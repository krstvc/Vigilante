package ip.vigilante.service;

import java.util.ArrayList;
import java.util.Collection;

import ip.vigilante.db.dao.PostDAO;
import ip.vigilante.model.EmergencyCategory;
import ip.vigilante.model.Post;
import ip.vigilante.model.PostEmergencyCategory;

public class PostService {
	
	private static PostService svc;
	
	private PostService() {}
	
	public static PostService getInstance() {
		if(svc == null) {
			svc = new PostService();
		}
		return svc;
	}
	
	public ArrayList<Post> getAllPosts() {
		return PostDAO.selectAllPosts();
	}
	
	public ArrayList<Post> getAllEmergencyAlertPosts() {
		return PostDAO.selectAllEmergencyAlertPosts();
	}
	
	public Post getPostById(int id) {
		return PostDAO.selectPostById(id);
	}
	
	public boolean addPost(Post post) {
		return PostDAO.insertPost(post);
	}
	
	public boolean updatePost(Post post) {
		return PostDAO.updatePost(post);
	}
	
	public boolean deletePost(int id) {
		return PostDAO.deletePost(id);
	}
	
	public ArrayList<EmergencyCategory> getAllEmergencyCategoriesForPost(int postId) {
		return PostDAO.selectAllEmergencyCategories(postId);
	}
	
	public boolean addEmergencyCategoryForPost(PostEmergencyCategory pec) {
		return PostDAO.insertEmergencyCategory(pec);
	}
	
	public boolean addEmergencyCategoriesForPost(Collection<PostEmergencyCategory> col) {
		boolean ret = true;
		for(PostEmergencyCategory pec : col) {
			ret = ret & addEmergencyCategoryForPost(pec);
		}
		return ret;
	}

}
