package com.ga.repository;

import java.util.List;

import com.ga.domain.model.CommentEmotionDTO;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;

public interface ICommentEmotionService {
	
	List<CommentEmotionDTO> getCommentEmotionByUser(int userId) throws GAException;
	public boolean addEmotion(int userId, int commentId, char liked, char unliked);
	void removeEmotion(int userId, int commentId);
	
}
