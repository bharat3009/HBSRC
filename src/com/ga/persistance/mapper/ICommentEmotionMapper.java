package com.ga.persistance.mapper;

import java.util.List;

import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;

public interface ICommentEmotionMapper {

	List<CommentEmotion> getCommentEmotionByUser(int userId) throws GAException ;
	
	boolean addEmotion(int userId, int commentId, char liked, char unliked);

	void removeEmotion(int userId, int commentId);
	
}
