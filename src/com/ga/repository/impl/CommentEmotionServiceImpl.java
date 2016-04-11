package com.ga.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ga.domain.model.CommentEmotionDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;
import com.ga.persistance.mapper.ICommentEmotionMapper;
import com.ga.repository.ICommentEmotionService;

@Service
@Transactional
public class CommentEmotionServiceImpl implements ICommentEmotionService{

	
	 /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);
    
    @Autowired
    ICommentEmotionMapper commentsMapper;
	@Override
	public List<CommentEmotionDTO> getCommentEmotionByUser(String userId)
			throws GAException {
		// TODO Auto-generated method stub
		 LOGGER.info("Get commemts list called!!");
	        List<CommentEmotion> commentEmotionList = commentsMapper.getCommentEmotionByUser(userId);
	        List<CommentEmotionDTO> commentsEmotionDtoList = new ArrayList<CommentEmotionDTO>();

	        // get data from database and store with list object.
	        if (commentEmotionList.isEmpty()) {
	            throw new GAException(ErrorCodes.GA_INTERNAL);
	        }

	        for (CommentEmotion commentEmotion : commentEmotionList) {
	        	commentsEmotionDtoList.add(convertEntityToDTO(commentEmotion));
	        }
	        // convert into dto and return to controller
	        if (commentsEmotionDtoList.isEmpty()) {
	            throw new GAException(ErrorCodes.GA_INTERNAL);
	        } else {
	            LOGGER.info("CommentsDtoList : " + commentsEmotionDtoList.toString());
	            return commentsEmotionDtoList;
	        }
	}
	
	
	 /**
     * Convert entity to dto.
     *
     * @param commentHistory the comment history
     * @return the comment dto
     */
    private CommentEmotionDTO convertEntityToDTO(CommentEmotion commentEmotion) {
        CommentEmotionDTO commentEmotionDto = new CommentEmotionDTO();
        
        commentEmotionDto.setCommentEmotionId(commentEmotion.getCommentEmotionId());
        commentEmotionDto.setCommentId(commentEmotion.getCommentid().getCommentId());
        commentEmotionDto.setUserId(commentEmotion.getUserId());
        commentEmotionDto.setNotAgreed(commentEmotion.getNotAgreeFlag());
        commentEmotionDto.setAgreed(commentEmotion.getAgreeFlag());
        
        return commentEmotionDto;
    }

}
