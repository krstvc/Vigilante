package ip.vigilante.emergency.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ip.vigilante.emergency.model.EmergencyCategory;
import ip.vigilante.emergency.model.Post;
import ip.vigilante.emergency.model.PostEmergencyCategory;
import ip.vigilante.emergency.services.EmergencyCategoryService;
import ip.vigilante.emergency.services.PostService;

@WebServlet("/post")
@MultipartConfig
public class PostController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PostService postSvc;
	private EmergencyCategoryService catSvc;
	
	public PostController() {
		postSvc = PostService.getInstance();
		catSvc = EmergencyCategoryService.getInstance();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("home.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Post post = getPostFromRequest(req);
		boolean ret = postSvc.addPost(post);
		
		ArrayList<EmergencyCategory> categories = getCategoriesFromRequest(req);
		ArrayList<PostEmergencyCategory> postCategories = getPostCategories(categories, post.getId());

		ret = ret & postSvc.addEmergencyCategoriesForPost(postCategories);
		
		if(ret) {
			resp.sendRedirect("home.jsp");
		} else {
			resp.sendRedirect("error.jsp");
		}
	}

	private Post getPostFromRequest(HttpServletRequest req) {
		int userId = (Integer)req.getSession().getAttribute("userId");
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String link = req.getParameter("link");
		String videoUrl = req.getParameter("videoUrl");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		String location = latitude + ", " + longitude;
		Date time = new Date();
		boolean emergency = req.getParameter("alert") != null;
		
		Post post = new Post(userId, title, content, link, videoUrl, location, time, emergency, false);
		return post;
	}
	
	private ArrayList<EmergencyCategory> getCategoriesFromRequest(HttpServletRequest req) {
		ArrayList<EmergencyCategory> list = new ArrayList<>();
		ArrayList<EmergencyCategory> categories = catSvc.getCategories();
		
		for(EmergencyCategory cat : categories) {
			String name = cat.getCategory();
			String value = req.getParameter(name);
			if(value != null) {
				list.add(cat);
			}
		}
		
		return list;
	}
	
	private ArrayList<PostEmergencyCategory> getPostCategories(ArrayList<EmergencyCategory> categories, int postId) {
		ArrayList<PostEmergencyCategory> list = new ArrayList<>();
		
		for(EmergencyCategory cat : categories) {
			list.add(new PostEmergencyCategory(postId, cat.getId()));
		}
		
		return list;
	}

}
