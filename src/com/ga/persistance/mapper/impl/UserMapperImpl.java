package com.ga.persistance.mapper.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.IUserMapper;

/**
 * The Class UserMapperImpl.
 *
 * @author Smit
 */
@Repository
public class UserMapperImpl implements IUserMapper {

    /** The session factory. */
    @Autowired
    SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.IUserMapper#userLogin(java.lang.String)
     */
    @Override
    public UserDetail userLogin(String userName) throws GAException {
        System.out.println("Login :" + userName);
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from UserDetail u where u.userName=:userName");
        query.setParameter("userName", userName);

        List list = query.list();
        if (list == null || list.size() == 0) {
            throw new GAException(ErrorCodes.GA_AUTH_FAILED);
        } else {
            UserDetail userDetail = (UserDetail) list.get(0);
            System.out.println(userDetail);
            session.getTransaction().commit();
            session.close();
            return userDetail;
        }
    }

	@Override
	public UserDetail newUserLogin(String userName, String password, int areaId) throws GAException{
		// TODO Auto-generated method stub
		 System.out.println("Login :" + userName);
	        Session session = sessionFactory.openSession();
	        session.getTransaction().begin();
	        UserDetail userDetail = new UserDetail();
	        userDetail.setUserName(userName);
	        userDetail.setPassword(password);
	        userDetail.setAreaId(areaId);
	        session.save(userDetail);
	        
	        session.getTransaction().commit();
            session.close();
            
            return userDetail;
		
		
	}

	@Override
	public boolean userExists(String userName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
	    Query query = session.createQuery("from UserDetail u where u.userName=:userName");
	    query.setParameter("userName", userName);		
	    
	    List list = query.list();
	    session.getTransaction().commit();
        session.close();
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
	}
}