package com.ga.persistance.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "comment_emotion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommentEmotion.findAll", query = "SELECT c FROM CommentEmotion c")})
public class CommentEmotion implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "comment_emotionid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer commentEmotionId;
    


	@Column(name = "user_id")
    @Expose  
    private String userId;
    
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    @ManyToOne
    private CommentHistory commentId;
    
    @Column(name = "agree_flag")
    @Expose
    private char agreeFlag;
    
    @Column(name = "notagree_flag")
    @Expose
    private char notAgreeFlag;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public CommentHistory getCommentid() {
		return commentId;
	}

	public void setCommentid(CommentHistory commentId) {
		this.commentId = commentId;
	}

	public char getAgreeFlag() {
		return agreeFlag;
	}

	public void setAgreeFlag(char agreeFlag) {
		this.agreeFlag = agreeFlag;
	}

	public char getNotAgreeFlag() {
		return notAgreeFlag;
	}

	public void setNotAgreeFlag(char notAgreeFlag) {
		this.notAgreeFlag = notAgreeFlag;
	}
	
	public Integer getCommentEmotionId() {
		return commentEmotionId;
	}

	public void setCommentEmotionId(Integer commentEmotionId) {
		this.commentEmotionId = commentEmotionId;
	}
    
    
}
