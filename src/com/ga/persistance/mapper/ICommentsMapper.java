package com.ga.persistance.mapper;

import java.util.List;

import com.ga.domain.model.CommentDTO;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentEmotion;
import com.ga.persistance.entity.CommentHistory;

/**
 * The Interface ICommentsMapper.
 *
 * @author Smit
 */
public interface ICommentsMapper {

    /**
     * Upload file.
     *
     * @param filePath the file path
     * @param comments the comments
     * @param userID the user id
     * @return true, if successful
     */
  //  boolean uploadFile(String filePath, String comments, String userID);

    /**
     * Gets the comments list.
     *
     * @param userID the user id
     * @return the comments list
     * @throws GAException 
     */
    List<CommentHistory> getCommentsList(int userID) throws GAException;

    /**
     * Gets the comment by comment id.
     *
     * @param commentID the comment id
     * @return the comment by comment id
     * @throws GAException 
     */
    CommentHistory getCommentByCommentID(int commentID) throws GAException;

    List<CommentDTO> getAllMainCommentsByUser(int userId) throws GAException;
	
    List<CommentHistory> getAllComments() throws GAException;

	List<CommentDTO> getAllMainCommentsByArea(int areaId,int userId) throws GAException;

	boolean addComments(String filePath, String comments, int userID,
			int areaId, int mainCommentId, char showNameFlag);

	public boolean commentUnlike(int commentId, String action);
	
	public boolean commentLike(int commentId, String action);

	List<CommentDTO> getAllSubComments(int mainCommentId, int areaId)
			throws GAException;

	List<CommentDTO> getGlobalComments(int userId) throws GAException;

	/*boolean addSubComments(String filePath, String comments, int userId,
			int areaId, int mainCommentId);*/
	
}
