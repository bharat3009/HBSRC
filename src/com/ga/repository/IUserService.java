package com.ga.repository;

import java.sql.ResultSet;
import java.util.List;

import com.ga.domain.model.UserDTO;
import com.ga.exception.GAException;

/**
 * The Interface IUserService.
 *
 * @author Smit
 */
public interface IUserService {

    /**
     * User login.
     *
     * @param username the username
     * @param password the password
     * @return the user dto
     * @throws GAException the GA exception
     */
    UserDTO userLogin(String username, String password) throws GAException;

    UserDTO newUserLogin(String username, String password, int areaId, String showName) throws GAException;

	boolean userExists(String userName);

	List<Object> getUserNames();

}
