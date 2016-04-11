package com.ga.domain.model;

import java.util.Date;

/**
 * The Class CommentDTO.
 *
 * @author Smit
 */
public class CommentDTO {

    /** The comment id. */
    private Integer commentId;

   
	/** The filepath. */
    private String filepath;

    /** The comments detail. */
    private String commentsDetail;

    /** The comment date. */
    private Date commentDate;
    
	private Integer agreeCount;
     
    private Integer notAgreeCount;
    
    private Integer commentsCount;
    
    private Boolean agreed;
    
    private Boolean notAgreed;

    /**
     * Gets the comment id.
     *
     * @return the comment id
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment id.
     *
     * @param commentId the new comment id
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets the filepath.
     *
     * @return the filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Sets the filepath.
     *
     * @param filepath the new filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the comments detail.
     *
     * @return the comments detail
     */
    public String getCommentsDetail() {
        return commentsDetail;
    }

    /**
     * Sets the comments detail.
     *
     * @param commentsDetail the new comments detail
     */
    public void setCommentsDetail(String commentsDetail) {
        this.commentsDetail = commentsDetail;
    }

    /**
     * Gets the comment date.
     *
     * @return the comment date
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Sets the comment date.
     *
     * @param commentDate the new comment date
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
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

}