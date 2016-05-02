package com.ga.persistance.mapper;

import java.sql.ResultSet;
import java.util.List;

import com.ga.exception.GAException;
import com.ga.persistance.entity.UserDetail;

/**
 * The Interface IUserMapper.
 *
 * @author Smit
 */
public interface IUserMapper {

    /**
     * User login.
     *
     * @param userName the user name
     * @return the user detail
     * @throws GAException the GA exception
     */
    UserDetail userLogin(String userName) throws GAException;
    
    
    UserDetail newUserLogin(String userName, String password, int areaId, String showName) throws GAException;


	boolean userExists(String userName);


	List<Object> getUserNames();
}
