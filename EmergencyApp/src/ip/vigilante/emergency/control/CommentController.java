package ip.vigilante.emergency.control;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ip.vigilante.emergency.util.FileManager;
import ip.vigilante.model.PostComment;
import ip.vigilante.model.User;
import ip.vigilante.service.PostCommentService;

@WebServlet("/comment")
@MultipartConfig
public class CommentController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PostCommentService commSvc;
	
	public CommentController() {
		commSvc = PostCommentService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("home.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PostComment comment = getCommentFromReq(req);
		
		boolean ret = commSvc.addComment(comment);
		
		if(ret) {
			resp.sendRedirect("home.jsp");
		} else {
			resp.sendRedirect("error.jsp");
		}
	}

	private PostComment getCommentFromReq(HttpServletRequest req) throws IOException, ServletException {
		User user = (User)req.getSession().getAttribute("user");
		
		Integer userId = user.getId();
		Integer postId = Integer.parseInt(req.getParameter("postId"));
		String content = req.getParameter("content");
		
		String imgUri = null;
		
		Part imgPart = req.getPart("image");
		if(imgPart.getSize() > 0) {
			imgUri = FileManager.saveFile(user.getUsername(), imgPart);
		}
		
		Date time = new Date();
		
		PostComment comm = new PostComment(postId, userId, content, imgUri, time, false);
		return comm;
	}

}
