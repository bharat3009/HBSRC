package com.ga.domain.model;

/**
 * The Class UserDTO.
 *
 * @author Smit
 */
public class UserDTO {

    /** The user id. */
    private int userId;

    /** The user name. */
    private String userName;
    
    public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	private int areaId;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}