package com.ga.persistance.mapper.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.ICommentEmotionMapper;

@Repository
public class CommentEmotionMapperImpl implements ICommentEmotionMapper {
	
	 @Autowired
	    SessionFactory sessionFactory;

	@Override
	public List<CommentEmotion> getCommentEmotionByUser(String userId) throws GAException { 
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		UserDetail userDetail = new UserDetail(userId);
		String hql = "FROM CommentEmotion where userId = '" + userDetail.getUserId() + "'";
		Query query = session.createQuery(hql);
		List<CommentEmotion> commentEmotionList = query.list();
		 if (commentEmotionList.isEmpty()) {
	            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
	        }
	    session.getTransaction().commit();
	    session.close();
	    System.out.println(commentEmotionList);
	    return commentEmotionList;
	}
	
	   
}


