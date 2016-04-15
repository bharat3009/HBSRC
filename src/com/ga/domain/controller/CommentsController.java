package com.ga.domain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ga.common.JsonUtility;
import com.ga.domain.model.CommentDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.repository.ICommentEmotionService;
import com.ga.repository.ICommentsService;

/**
 * The Class CommentsController.
 *
 * @author Smit
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);

	/** The comments service. */
	@Autowired
	ICommentsService commentsService;    
	
	@Autowired
	ICommentEmotionService commentEmotionService;

	/**
	 * Adds the comments.
	 *
	 * @param filePath the file path
	 * @param comments the comments
	 * @param userId the user id
	 * @return the string
	 */
	@RequestMapping(value = "/addcomments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addComments(@RequestParam("filePath") String filePath, @RequestParam("comments") String comments,
			@RequestParam("userId") int userId, @RequestParam("areaId") int areaId) {

		LOGGER.info(String.format("comments : %s,comments : %s, userName : %s, areaId : %s ", filePath, comments, userId, areaId));
		try {
			if (filePath.isEmpty() || comments.isEmpty()) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}

			boolean resultSaveComment = commentsService.addComments(filePath, comments, userId, areaId);
			if (!resultSaveComment) {
				LOGGER.info("File upload error");
				throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
			}
			LOGGER.info("File upload successfull");
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, null);

		} catch (GAException e) {
			e.printStackTrace();
			if (e.getCode() == ErrorCodes.GA_FILE_UPLOAD.getErrorCode()) {
				LOGGER.info("File upload error");
				return JsonUtility.getJson(ErrorCodes.GA_FILE_UPLOAD, null);
			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				LOGGER.info("Parameter not set");
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);
			} else {
				LOGGER.info("Internal error. Obj is null");
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return the string
	 */
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadFile(@RequestParam CommonsMultipartFile file) {
		LOGGER.info("File size : " + file.getSize());
		CommentDTO commentDto = new CommentDTO();

		try {
			if (file.isEmpty()) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}

			String resultFilePath = commentsService.uploadFile(file);

			if (resultFilePath.isEmpty()) {
				throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
			}
			commentDto.setFilepath("comments/" + resultFilePath);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentDto);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_FILE_UPLOAD.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_FILE_UPLOAD, null);

			} else if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}

	/**
	 * Gets the all comments.
	 *
	 * @param userId the user id
	 * @return the all comments
	 */
	@RequestMapping(value = "getallcommentsbyuserid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllCommentsByUser(@RequestParam("userId") int userId, @RequestParam("userTime") Integer userTime) {
		LOGGER.info("UserId : " + userId + " User Time " + userTime);
		try {
			if (userId == 0 && userTime == null) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}
			// This is call service to get comment for specific user.
			List<CommentDTO> commentsDtoList = commentsService.getCommentsList(userId,userTime);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}

	/**
	 * Gets the comment by id.
	 *
	 * @param commentId the comment id
	 * @return the comment by id
	 */
	@RequestMapping(value = "getcommentbyid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCommentById(@RequestParam("commentId") String commentId,@RequestParam("userTime") Integer userTime) {
		LOGGER.info("CommentId : " + commentId + " User Time " + userTime);
		try {
			if (commentId.isEmpty() && userTime == null) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}
			// This is call service to get comment by comment id.
			CommentDTO commentsDto = commentsService.getCommentByCommentID(commentId,userTime);
			if (commentsDto == null) {
				throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
			}
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDto);
		} catch (GAException e) {
			e.printStackTrace();
			return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);
		}
	}



	/**
	 * Gets the all main comments by user.
	 *
	 * @param userId the user id
	 * @return the all comments
	 */
	@RequestMapping(value = "getallmaincommentsbyuserid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllMainCommentsByUserId(@RequestParam("userId") int userId, @RequestParam("userTime") Integer userTime) {
		LOGGER.info("UserId : " + userId + " User Time " + userTime);
		try {
			if (userId == 0 && userTime == null) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}
			// This is call service to get comment for specific user.
			List<CommentDTO> commentsDtoList = commentsService.getAllMainCommentsListByUserId(userId,userTime);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}


	/**
	 * Gets the all comments.
	 *
	 * @param userId the user id
	 * @return the all comments
	 */
	@RequestMapping(value = "getallcomments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllComments(@RequestParam("userTime") Integer userTime) {
		LOGGER.info(" User Time " + userTime);
		try {

			List<CommentDTO> commentsDtoList = commentsService.getAllComments(userTime);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}
	
	
	/**
	 * Gets the all main comments by area.
	 *
	 * @param areaId the area id
	 * @return the all comments
	 */
	@RequestMapping(value = "getallmaincommentsbyarea", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllMainCommentsByAreaId(@RequestParam("areaId") int areaId,  @RequestParam("userId") Integer userId,@RequestParam("userTime") Integer userTime) {
		LOGGER.info(" User Time " + userTime);
		try {

			List<CommentDTO> commentsDtoList = commentsService.getAllMainCommentsByArea(areaId,userId,userTime);
			System.out.println( "comments " + commentsDtoList);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}
	
	
	/*@RequestMapping(value="gethotbyarea", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getHotCommentsByArea(@RequestParam("areaId") int areaId, @RequestParam("userTime") Integer userTime) {
		LOGGER.info(" User Time " + userTime);
		try {

			List<CommentDTO> commentsDtoList = commentsService.getAllMainCommentsByArea(areaId,userTime);
			System.out.println( "comments " + commentsDtoList);
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}*/
	
	
	@RequestMapping(value = "getemotion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String userReacted(@RequestParam("action") String action,@RequestParam("presentAction") String presentAction, @RequestParam("liked") char liked, @RequestParam("unliked") char unliked,
			@RequestParam("commentId") int commentId, @RequestParam("userId") int userId) {
		LOGGER.info("action : " + action + " liked " + liked);
		try {
			if (action.isEmpty()) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}
			if(action.equals("liked")){
				System.out.println("liked");
				if(presentAction.equals("noaction")) {
					System.out.println("liked");
					commentEmotionService.addEmotion(userId, commentId, 'Y', 'N');
					commentsService.commentLike(commentId, "add");
				
				} else if(presentAction.equals("unliked")) {
					commentEmotionService.removeEmotion(userId, commentId);
					commentEmotionService.addEmotion(userId, commentId, 'Y', 'N');
					commentsService.commentUnlike(commentId, "sub");
					commentsService.commentLike(commentId, "add");
				}else if(presentAction.equals("liked")) {
					commentEmotionService.removeEmotion(userId, commentId);
					commentsService.commentLike(commentId, "sub");
				}
				
			} else if(action.equals("unliked")) {
				if(presentAction.equals("noaction")) {
					commentEmotionService.addEmotion(userId, commentId, 'N', 'Y');
					commentsService.commentUnlike(commentId, "add");
					
				} else if(presentAction.equals("liked")) {
					commentEmotionService.removeEmotion(userId, commentId);
					commentEmotionService.addEmotion(userId, commentId, 'N', 'Y');
					commentsService.commentLike(commentId, "sub");
					commentsService.commentUnlike(commentId, "add");
				}else if(presentAction.equals("unliked")) {
					commentEmotionService.removeEmotion(userId, commentId);
					commentsService.commentUnlike(commentId, "sub");
				}
			}
			// This is call service to get comment for specific user.
			
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, null);
		} catch (GAException e) {
			if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

			} else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
				return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);

			} else {
				return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
			}
		}
	}
	

}

