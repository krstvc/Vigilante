package ip.vigilante.emergency.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ip.vigilante.emergency.util.FileManager;
import ip.vigilante.model.EmergencyCategory;
import ip.vigilante.model.Image;
import ip.vigilante.model.Post;
import ip.vigilante.model.PostEmergencyCategory;
import ip.vigilante.model.User;
import ip.vigilante.service.EmergencyCategoryService;
import ip.vigilante.service.ImageService;
import ip.vigilante.service.PostService;

@WebServlet("/post")
@MultipartConfig
public class PostController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PostService postSvc;
	private EmergencyCategoryService catSvc;
	private ImageService imgSvc;
	
	public PostController() {
		postSvc = PostService.getInstance();
		catSvc = EmergencyCategoryService.getInstance();
		imgSvc = ImageService.getInstance();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("home.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		
		Post post = getPostFromRequest(req);
		List<Part> imgParts = req.getParts()
				.stream()
				.filter(p -> p.getName().equals("image") && p.getSize() > 0)
				.collect(Collectors.toList());
		
		boolean ret = postSvc.addPost(post);
		for(Part imgPart : imgParts) {
			String path = FileManager.saveFile(user.getUsername(), imgPart);
			Image img = new Image(post.getId(), path, false);
			ret = ret & imgSvc.addImage(img);
		}
		
		ArrayList<EmergencyCategory> categories = getCategoriesFromRequest(req);
		ArrayList<PostEmergencyCategory> postCategories = getPostCategories(categories, post.getId());

		ret = ret & postSvc.addEmergencyCategoriesForPost(postCategories);
		
		if(ret) {
			resp.sendRedirect("home.jsp");
		} else {
			resp.sendRedirect("error.jsp");
		}
	}

	private Post getPostFromRequest(HttpServletRequest req) throws IOException, ServletException {
		User user = (User)req.getSession().getAttribute("user");
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String link = req.getParameter("link");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		String location = latitude != null && longitude != null ? latitude + ", " + longitude : "";
		Date time = new Date();
		boolean emergency = req.getParameter("alert") != null;
		
		String videoUrl = null;
		String videoType = req.getParameter("videoType");
		
		if(videoType != null && videoType.equals("url")) {
			videoUrl = req.getParameter("videoUrl");
		} else if(videoType != null && videoType.equals("upload")) {
			Part vidPart = req.getPart("video");
			if(vidPart.getSize() > 0) {
				videoUrl = FileManager.saveFile(user.getUsername(), vidPart);
			}
		}
		
		Post post = new Post(user.getId(), title, content, link, videoUrl, location, time, emergency, false);
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
