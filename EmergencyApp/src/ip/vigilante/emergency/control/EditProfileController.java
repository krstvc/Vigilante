package ip.vigilante.emergency.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ip.vigilante.emergency.util.FileManager;
import ip.vigilante.model.User;
import ip.vigilante.service.UserService;

@WebServlet("/profile")
@MultipartConfig
public class EditProfileController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserService userSvc;
	
	public EditProfileController() {
		userSvc = UserService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("profile.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = getUserFromRequest(req);
		
		boolean ret = userSvc.updateUser(user);
		req.getSession().setAttribute("user", user);
		
		if(ret) {
			resp.sendRedirect("home.jsp");
		} else {
			resp.sendRedirect("error.jsp");
		}
	}
	
	private User getUserFromRequest(HttpServletRequest req) throws IOException, ServletException {
		User userFromSession = (User)req.getSession().getAttribute("user");
		
		String username = req.getParameter("username");
		Part filePart = req.getPart("image");
		String imageURI = req.getParameter("countryFlag");
		
		if(filePart.getSize() > 0) {
			imageURI = FileManager.saveFile(username, filePart);
		}
		
		String idStr = req.getParameter("id");
		int id = idStr != null && idStr.length() > 0 ? Integer.parseInt(req.getParameter("id")) : 0;
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String email = req.getParameter("email");
		String country = req.getParameter("country");
		String region = req.getParameter("region");
		String city = req.getParameter("city");
		String password = userFromSession.getPasswordHash();
		boolean subscribedApp = Boolean.parseBoolean(req.getParameter("appSubscription"));
		boolean subscribedMail = Boolean.parseBoolean(req.getParameter("mailSubscription"));
		boolean isAdmin = userFromSession.isAdmin();
		boolean isApproved = userFromSession.isApproved();
		boolean isBlocked = userFromSession.isBlocked();
		boolean isDeleted = userFromSession.isDeleted();
		boolean isLogged = userFromSession.isLogged();
		int loginCount = userFromSession.getLoginCount();
		
		User user = new User(id, username, password, name, surname, email, imageURI, country, region, city,
				subscribedApp, subscribedMail, isAdmin, isApproved, isBlocked, isLogged, isDeleted, loginCount);
		return user;
	}

}
