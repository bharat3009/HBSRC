package com.ga.repository.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ga.domain.model.CommentDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.mapper.ICommentsMapper;
import com.ga.repository.ICommentsService;

/**
 * The Class CommentsServiceImpl.
 *
 * @author Smit
 */
@Service
@Transactional
public class CommentsServiceImpl implements ICommentsService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);

    /** The comments mapper. */
    @Autowired
    ICommentsMapper commentsMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#addComments(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean addComments(String filePath, String comments, int userID, int areaId, int mainCommentId, char showNameFlag) throws GAException {
        LOGGER.info("Upload file called!!");
        boolean result = commentsMapper.addComments(filePath, comments, userID, areaId, mainCommentId, showNameFlag);

        if (result) {
            LOGGER.info("File uploaded successfully");
            return true;
        } else {
            LOGGER.info("File upload error");
            throw new GAException(ErrorCodes.GA_INTERNAL);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentDTO> getCommentsList(int userID, Integer userTime) throws GAException {
        LOGGER.info("Get commemts list called!!");
        List<CommentHistory> commentHistoryList = commentsMapper.getCommentsList(userID);
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();

        // get data from database and store with list object.
        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        }

        for (CommentHistory commentHistory : commentHistoryList) {
            commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }
        // convert into dto and return to controller
        if (commentsDtoList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentsDtoList;
        }
    }
    
    
    

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentByCommentID(java.lang.String)
     */
    @Override
    public CommentDTO getCommentByCommentID(String commentID, Integer userTime) throws GAException {
        LOGGER.info("Get commemt by comment id called!!");
        int commentId = Integer.parseInt(commentID);
        CommentHistory commentHistory = commentsMapper.getCommentByCommentID(commentId);

        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        CommentDTO commentDTO = convertEntityToDTO(commentHistory, userTime);
        if (commentDTO == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        return commentDTO;
    }

    /**
     * Convert entity to dto.
     *
     * @param commentHistory the comment history
     * @return the comment dto
     */
    private CommentDTO convertEntityToDTO(CommentHistory commentHistory, Integer userTime) {
        CommentDTO commentDto = new CommentDTO();
        Date d = commentHistory.getCommentDate();

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, userTime);

        commentDto.setCommentDate(calendar.getTime());
        commentDto.setCommentId(commentHistory.getCommentId());
        commentDto.setCommentsDetail(commentHistory.getCommentsDetail());
        commentDto.setFilepath(commentHistory.getFilepath());
        commentDto.setAgreeCount(commentHistory.getAgreeCount());
        commentDto.setNotAgreeCount(commentHistory.getNotAgreeCount());
        commentDto.setCommentsCount(commentHistory.getCommentsCount());

        return commentDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ga.repository.ICommentsService#uploadFile(org.springframework.web.multipart.commons.CommonsMultipartFile)
     */
    @Override
    public String uploadFile(CommonsMultipartFile file) throws GAException {
        LOGGER.info("Upload file called!!");
        String fileName;
        try {
            fileName = checkIsFile(file);
            if (fileName.isEmpty()) {
                LOGGER.info("File is empty!!");
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
            LOGGER.info(String.format("Upload file complete!! File path : %s", fileName));
            return fileName;
        } catch (IllegalStateException e) {
            LOGGER.info("Exception : " + e.getMessage());
            LOGGER.info("stack trace :  " + e);
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
    }

    /**
     * Check is file.
     *
     * @param file the file
     * @return the string
     * @throws GAException the GA exception
     */
    private String checkIsFile(CommonsMultipartFile file) throws GAException {
        LOGGER.info("Checkfile is called!!");
        try {
            if (!file.isEmpty()) {
                LOGGER.info("checkIsFile return true:");
                byte[] bytes = file.getBytes();
                Long f = new Date().getTime();

                String filename = file.getOriginalFilename();
                LOGGER.info("filename :" + filename);

                String[] sli = filename.split("\\.");
                /* print substrings */

                LOGGER.info("extension :" + sli[sli.length - 1]);
                File newFile = new File("C:/xampp/htdocs/Feedbacktool/images/" + f + "." + sli[sli.length - 1]);

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
                LOGGER.info("newFile :" + newFile);

                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
                LOGGER.info("return file :" + newFile.getName());
                return newFile.getName();

            } else {
                LOGGER.info("checkIsFile return false:");
                throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentDTO> getAllMainCommentsListByUserId(int userId, Integer userTime) throws GAException {
        LOGGER.info("Get commemts list called!!");
        List<CommentDTO> commentHistoryList = commentsMapper.getAllMainCommentsByUser(userId);
        //List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();

        // get data from database and store with list object.
        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        }

        /*for (CommentHistory commentHistory : commentHistoryList) {
            commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }*/
        // convert into dto and return to controller
        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentHistoryList.toString());
            return commentHistoryList;
        }
    }
    
    @Override
    public List<CommentDTO> getAllComments(Integer userTime) throws GAException {
    	LOGGER.info("Get all comments list");
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();
        List<CommentHistory> commentList = commentsMapper.getAllComments();
        if(commentList.isEmpty()){
        	throw new GAException(ErrorCodes.GA_INTERNAL);
        }
        
        for (CommentHistory commentHistory : commentList){
        	commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }

        if (commentsDtoList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentsDtoList;
        }
    }
    
    @Override
    public List<CommentDTO> getAllMainCommentsByArea(int areaId,int userId, Integer userTime) throws GAException {
    	LOGGER.info("Get all main comments list by area");
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();
        List<CommentDTO> commentList = commentsMapper.getAllMainCommentsByArea(areaId,userId);
        if(commentList.isEmpty()){
        	throw new GAException(ErrorCodes.GA_INTERNAL);
        }
        
       /* for (CommentHistory commentHistory : commentList){
        	commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }*/

        if (commentList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentList;
        }
    }
    
    @Override
    public List<CommentDTO> getAllSubComments(int mainCommentId,int userId, Integer userTime) throws GAException {
    	LOGGER.info("Get all main comments list by area");
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();
        List<CommentDTO> commentList = commentsMapper.getAllSubComments(mainCommentId,userId);
        if(commentList.isEmpty()){
        	throw new GAException(ErrorCodes.GA_INTERNAL);
        }
        
       /* for (CommentHistory commentHistory : commentList){
        	commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }*/

        if (commentList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentList;
        }
    }
    
    @Override
    public boolean commentLike(int commentId, String action){
    	
    	return commentsMapper.commentLike(commentId, action);
    }
    
    @Override
	public boolean commentUnlike(int commentId, String action){
    	return commentsMapper.commentUnlike(commentId, action);
    }

	@Override
	public List<CommentDTO> getGlobalCommentsList(int userId, Integer userTime) throws GAException {
		// TODO Auto-generated method stub
		LOGGER.info("Get all main comments list by area");
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();
        List<CommentDTO> commentList = commentsMapper.getGlobalComments(userId);
        if(commentList.isEmpty()){
        	throw new GAException(ErrorCodes.GA_INTERNAL);
        }
        
       /* for (CommentHistory commentHistory : commentList){
        	commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }*/

        if (commentList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentList;
        }
		
		//return null;
	}

	/*@Override
	public boolean addSubComments(String filePath, String comments, int userId,
			int areaId, int mainCommentId) throws GAException {
		// TODO Auto-generated method stub
		LOGGER.info("Upload file called!!");
        boolean result = commentsMapper.addSubComments(filePath, comments, userId, areaId, mainCommentId);

        
		return result;
	}*/
}