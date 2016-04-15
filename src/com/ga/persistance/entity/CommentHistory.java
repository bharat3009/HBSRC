package com.ga.persistance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Smit
 */
@Entity
@Table(name = "comment_history")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CommentHistory.findAll", query = "SELECT c FROM CommentHistory c"),
        @NamedQuery(name = "CommentHistory.findByCommentId", query = "SELECT c FROM CommentHistory c WHERE c.commentId = :commentId"),
        @NamedQuery(name = "CommentHistory.findByFilepath", query = "SELECT c FROM CommentHistory c WHERE c.filepath = :filepath"),
        @NamedQuery(name = "CommentHistory.findByCommentsDetail", query = "SELECT c FROM CommentHistory c WHERE c.commentsDetail = :commentsDetail"),
        @NamedQuery(name = "CommentHistory.findByCommentDate", query = "SELECT c FROM CommentHistory c WHERE c.commentDate = :commentDate"),
        @NamedQuery(name = "CommentHistory.findAllMainComments", query = "SELECT c from CommentHistory c WHERE c.masterCommentId != null") })
public class CommentHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "comment_id" )
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private Integer commentId;
    @Column(name = "filepath")
    @Expose
    private String filepath;
    @Column(name = "comments_detail")
    @Expose
    private String commentsDetail;
    @Column(name = "comment_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date commentDate;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne  
    private UserDetail userId;
    @JoinColumn(name = "area_id", referencedColumnName = "areaId")
    @ManyToOne
    private Area areaId;
    
    @Column(name = "master_commentid")
    @Expose
    private Integer masterCommentId;
    
    @Column(name = "agree_count")
    @Expose
    private Integer agreeCount;
    
    @Column(name = "notagree_count")
    @Expose
    private Integer notAgreeCount;
    
    @Column(name = "comments_count")
    @Expose
    private Integer commentsCount;
/*
    private Boolean agreed;
    private Boolean notAgreed;
    
    public Boolean getAgreed() {
		return agreed;
	}

	public void setAgreed(Boolean agreed) {
		this.agreed = agreed;
	}

	public Boolean getNotAgreed() {
		return notAgreed;
	}

	public void setNotAgreed(Boolean notAgreed) {
		this.notAgreed = notAgreed;
	}
*/
    public Integer getMasterCommentId() {
		return masterCommentId;
	}

	public void setMasterCommentId(Integer masterCommentId) {
		this.masterCommentId = masterCommentId;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getNotAgreeCount() {
		return notAgreeCount;
	}

	public void setNotAgreeCount(Integer notAgreeCount) {
		this.notAgreeCount = notAgreeCount;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public CommentHistory() {
    }

    public Area getAreaId() {
		return areaId;
	}

	public void setAreaId(Area areaId) {
		this.areaId = areaId;
	}

	public CommentHistory(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getCommentsDetail() {
        return commentsDetail;
    }

    public void setCommentsDetail(String commentsDetail) {
        this.commentsDetail = commentsDetail;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public UserDetail getUserId() {
        return userId;
    }

    public void setUserId(UserDetail userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentHistory)) {
            return false;
        }
        CommentHistory other = (CommentHistory) object;
        if ((this.commentId == null && other.commentId != null)
                || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommentHistory [commentId=" + commentId + ", filepath=" + filepath + ", commentDate=" + commentDate
                + "]";
    }
}