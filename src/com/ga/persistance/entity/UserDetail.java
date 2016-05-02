package com.ga.persistance.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Smit
 */
@Entity
@Table(name = "user_detail")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "UserDetail.findAll", query = "SELECT u FROM UserDetail u"),
        @NamedQuery(name = "UserDetail.findByUserId", query = "SELECT u FROM UserDetail u WHERE u.userId = :userId"),
        @NamedQuery(name = "UserDetail.findByUserName", query = "SELECT u FROM UserDetail u WHERE u.userName = :userName"),
        @NamedQuery(name = "UserDetail.findByPassword", query = "SELECT u FROM UserDetail u WHERE u.password = :password") })
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private Integer userId;
    @Column(name = "user_name")
    @Expose
    private String userName;
    @Column(name = "password")
    private String password;
	@Column(name = "area_id")
    private int areaId;
    @Column(name = "show_name")
    private String showName;
    
    
    public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
    public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	//@OneToOne
	//private Collection<Area> areaCollection;

	/*public Collection<Area> getAreaCollection() {
		return areaCollection;
	}

	public void setAreaCollection(Collection<Area> areaCollection) {
		this.areaCollection = areaCollection;
	}*/

	//@OneToMany(mappedBy = "userId")
   // private Collection<CommentHistory> commentHistoryCollection;

    public UserDetail() {
    }

    public UserDetail(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  /*  @XmlTransient
    public Collection<CommentHistory> getCommentHistoryCollection() {
        return commentHistoryCollection;
    }

    public void setCommentHistoryCollection(Collection<CommentHistory> commentHistoryCollection) {
        this.commentHistoryCollection = commentHistoryCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDetail)) {
            return false;
        }
        UserDetail other = (UserDetail) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserDetail [userId=" + userId + ", userName=" + userName + "]";
    }
}