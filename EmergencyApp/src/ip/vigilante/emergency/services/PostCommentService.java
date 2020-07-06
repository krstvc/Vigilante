package ip.vigilante.emergency.services;

import java.util.ArrayList;

import ip.vigilante.emergency.database.dao.PostCommentDAO;
import ip.vigilante.emergency.model.PostComment;

public class PostCommentService {
	
	private static PostCommentService svc;
	
	private PostCommentService() {}
	
	public static PostCommentService getInstance() {
		if(svc == null) {
			svc = new PostCommentService();
		}
		return svc;
	}
	
	public ArrayList<PostComment> getCommentsForPost(int postId) {
		return PostCommentDAO.selectAllCommentsForPost(postId);
	}
	
	public PostComment getCommentById(int id) {
		return PostCommentDAO.selectCommentById(id);
	}
	
	public boolean addComment(PostComment comment) {
		return PostCommentDAO.insertComment(comment);
	}
	
	public boolean deleteComment(int id) {
		return PostCommentDAO.deleteComment(id);
	}

}
