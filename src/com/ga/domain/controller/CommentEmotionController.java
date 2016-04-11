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

import com.ga.common.JsonUtility;
import com.ga.domain.model.CommentDTO;
import com.ga.domain.model.CommentEmotionDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.repository.ICommentEmotionService;
import com.ga.repository.ICommentsService;

@RestController
@RequestMapping("/commentemotion")
public class CommentEmotionController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);

	/** The comments service. */
	@Autowired
	ICommentEmotionService commentEmotionService;    
	
	
	/**
	 * Gets the all comments.
	 *
	 * @param userId the user id
	 * @return the all comments
	 */
	@RequestMapping(value = "getallcommentEmotionsbyuserid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getCommentEmotionByUser(@RequestParam("userId") String userId) {
		LOGGER.info("UserId : " + userId );
		try {
			if (userId.isEmpty() ) {
				throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
			}
			// This is call service to get comment for specific user.
			List<CommentEmotionDTO> commentsEmotionDtoList = commentEmotionService.getCommentEmotionByUser(userId);
			System.out.println("inthe controleler" + commentsEmotionDtoList);
			System.out.println(JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsEmotionDtoList));
			return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsEmotionDtoList);
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
