package com.ga.persistance.mapper;

import java.util.List;

import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;

public interface ICommentEmotionMapper {

	List<CommentEmotion> getCommentEmotionByUser(String userId) throws GAException ;
	
}
