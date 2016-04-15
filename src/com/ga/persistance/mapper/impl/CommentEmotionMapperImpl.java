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
	public List<CommentEmotion> getCommentEmotionByUser(int userId) throws GAException { 
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

	@Override
	public boolean addEmotion(int userId, int commentId, char liked,
			char unliked) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		CommentHistory commentHistory = new CommentHistory(commentId);
		CommentEmotion commentEmotion = new CommentEmotion();
		commentEmotion.setCommentid(commentHistory);
		commentEmotion.setUserId(userId);
		commentEmotion.setAgreeFlag(liked);
		commentEmotion.setNotAgreeFlag(unliked);
		session.save(commentEmotion);
        session.getTransaction().commit();
        session.close();
        return true;
	}

	@Override
	public void removeEmotion(int userId, int commentId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		String hql = "delete from comment_emotion where comment_id = :commentId and user_id = :userId";
		Query query = session.createSQLQuery(hql);
		query.setParameter("commentId", commentId);
		query.setParameter("userId", userId);
		query.executeUpdate();
        session.getTransaction().commit();
        session.close();
		
	}
	
	   
}


