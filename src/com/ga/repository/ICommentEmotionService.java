package com.ga.repository;

import java.util.List;

import com.ga.domain.model.CommentEmotionDTO;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;

public interface ICommentEmotionService {
	
	List<CommentEmotionDTO> getCommentEmotionByUser(String userId) throws GAException;
}
