package com.ga.persistance.mapper.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.domain.model.CommentDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.Area;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.ICommentsMapper;

/**
 * The Class CommentsMapperImpl.
 *
 * @author Smit
 */
@Repository
public class CommentsMapperImpl implements ICommentsMapper {
    /** The session factory. */
    @Autowired
    SessionFactory sessionFactory;
    
    private static final String userMainComments = "select ch.*," + 
															"case when (select agree_flag from comment_emotion where comment_id = ch.comment_id  and user_id = :userId) = 'Y' then 1 else 0 end as agreed," +
															"case when (select notagree_flag from comment_emotion where comment_id = ch.comment_id  and user_id = :userId) = 'Y' then 1 else 0 end as notagreed " +
															" from comment_history ch where ch.user_id = :userId and ch.master_commentid is null ORDER BY ch.comment_date DESC";
    
    private static final String areaMainComments = "select ch.*," + 
			"case when (select agree_flag from comment_emotion where comment_id = ch.comment_id and  user_id = ch.user_id and user_id = :userId) = 'Y' then 1 else 0 end as agreed," +
			"case when (select notagree_flag from comment_emotion where comment_id = ch.comment_id and  user_id = ch.user_id and user_id = :userId) = 'Y' then 1 else 0 end as notagreed " +
			" from comment_history ch where ch.area_id = :areaId and ch.master_commentid is null ORDER BY ch.comment_date DESC";

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#uploadFile(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean addComments(String filePath, String comments, int userID, int areaId) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        CommentHistory commentsHistory = new CommentHistory();
        commentsHistory.setCommentsDetail(comments);
        commentsHistory.setFilepath(filePath);
        commentsHistory.setUserId(new UserDetail(userID));
        commentsHistory.setAreaId(new Area(areaId));

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);

        commentsHistory.setCommentDate(calendar.getTime());
        commentsHistory.setAgreeCount(0);
        commentsHistory.setNotAgreeCount(0);
        commentsHistory.setCommentsCount(0);

        session.save(commentsHistory);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentHistory> getCommentsList(int userID) throws GAException {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        UserDetail userDetail = new UserDetail(userID);

        String hql = "FROM CommentHistory where userId =" + userDetail.getUserId() + "ORDER BY commentDate DESC";
        Query query = session.createQuery(hql);
        List<CommentHistory> communityResuleList = query.list();
        if (communityResuleList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
        session.getTransaction().commit();
        session.close();
        return communityResuleList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#getCommentByCommentID(int)
     */
    @Override
    public CommentHistory getCommentByCommentID(int commentID) throws GAException {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        CommentHistory commentHistory = (CommentHistory) session.get(CommentHistory.class, commentID);
        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
        session.getTransaction().commit();
        session.close();
        return commentHistory;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentDTO> getAllMainCommentsByUser(int userId) throws GAException {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        UserDetail userDetail = new UserDetail(userId);
       
        //String hql = "FROM CommentHistory where userId =" + userDetail.getUserId() +  " and masterCommentId = null ORDER BY commentDate DESC";
        Query query = session.createSQLQuery(userMainComments).setParameter("userId", userDetail.getUserId());
        
        List<Object> resultList = query.list();
        if (resultList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
        
        session.getTransaction().commit();
        session.close();
        System.out.println(resultList);
        return convertQueryResultTODTO(resultList);
    }
    
    
    @Override
    public List<CommentHistory> getAllComments() throws GAException{
    	Session session = sessionFactory.openSession();
    	session.getTransaction().begin();
    	String hql = "FROM CommentHistory ORDER BY commentDate DESC";
    	Query query = session.createQuery(hql);
    	List<CommentHistory> commentList = query.list();
    	if(commentList.isEmpty()){
    		throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
    	}
    	session.getTransaction().commit();
    	session.close();
    	return commentList;
    }
    
    @Override
	public List<CommentDTO> getAllMainCommentsByArea(int areaId,int userId) throws GAException{
    	Session session = sessionFactory.openSession();
    	session.getTransaction().begin();
        UserDetail userDetail = new UserDetail(areaId);

        String hql = "FROM CommentHistory where areaId =" + areaId +  " and masterCommentId = null ORDER BY commentDate DESC";
        Query query = session.createSQLQuery(areaMainComments);
        query.setParameter("areaId", areaId);
        query.setParameter("userId", userId);
        List<Object> resultList = query.list();
        if (resultList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
        session.getTransaction().commit();
        session.close();
        return convertQueryResultTODTO(resultList);
	}
    
    
    @Override
    public boolean commentLike(int commentId, String action){
    	Session session = sessionFactory.openSession();
    	session.getTransaction().begin();
    	String hql = "";
    	if(action.equals("add")){
    		hql = "update comment_history set agree_count = agree_count + 1 where comment_id = :commentId";
    	}else if(action.equals("sub")){
    		hql = "update comment_history set agree_count = agree_count - 1 where comment_id = :commentId";
    	}
    	Query query = session.createSQLQuery(hql).setParameter("commentId", commentId);
    	query.executeUpdate();
    	 session.getTransaction().commit();
         session.close();
    	return true;
    }
    
    @Override
    public boolean commentUnlike(int commentId, String action){
    	Session session = sessionFactory.openSession();
    	session.getTransaction().begin();
    	String hql = "";
    	if(action.equals("add")){
    		hql = "update comment_history set notagree_count = notagree_count + 1 where comment_id = :commentId";
    	}else if(action.equals("sub")){
    		hql = "update comment_history set notagree_count = notagree_count - 1 where comment_id = :commentId";
    	}
    	Query query = session.createSQLQuery(hql).setParameter("commentId", commentId);
    	query.executeUpdate();
    	 session.getTransaction().commit();
         session.close();
    	return true;
    }

    
    private List<CommentDTO> convertQueryResultTODTO(List<Object> resultList){
    	List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        for(Object object : resultList){
        	Object[] result = (Object[]) object;
        	CommentDTO commentDTO =  new CommentDTO();
        	commentDTO.setCommentId((Integer) result[0]);
        	commentDTO.setFilepath((String)result[2]);
        	commentDTO.setCommentsDetail((String)result[1]);
        	commentDTO.setCommentDate((Date)result[3]);
        	commentDTO.setAgreeCount((Integer) result[7]);
        	commentDTO.setNotAgreeCount((Integer) result[8]);
        	commentDTO.setCommentsCount((Integer) result[9]);
        	Integer agreeValue = ((BigInteger) result[10]).intValue();
        	commentDTO.setAgreed((Boolean) ((agreeValue == 1) ? true : false));
        	Integer notAgreeValue = ((BigInteger) result[11]).intValue();
        	commentDTO.setNotAgreed((Boolean) ((notAgreeValue == 1) ? true : false));

        	commentDTOList.add(commentDTO);
        }
        System.out.println(commentDTOList);
        return commentDTOList;
    }

}
