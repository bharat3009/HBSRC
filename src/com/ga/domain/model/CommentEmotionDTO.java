package com.ga.domain.model;

public class CommentEmotionDTO {
	
	private Integer commentEmotionId;
	private int userId;
	private Integer commentId;
	private char agreed;
	private char notAgreed;
	
	public Integer getCommentEmotionId() {
		return commentEmotionId;
	}
	public void setCommentEmotionId(Integer commentEmotionId) {
		this.commentEmotionId = commentEmotionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public char getAgreed() {
		return agreed;
	}
	public void setAgreed(char agreed) {
		this.agreed = agreed;
	}
	public char getNotAgreed() {
		return notAgreed;
	}
	public void setNotAgreed(char notAgreed) {
		this.notAgreed = notAgreed;
	}
	
	

}
