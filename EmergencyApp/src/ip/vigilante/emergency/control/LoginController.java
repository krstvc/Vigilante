package ip.vigilante.emergency.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ip.vigilante.emergency.util.AuthenticationManager;
import ip.vigilante.model.User;
import ip.vigilante.service.UserService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserService userSvc;
	
	public LoginController() {
		userSvc = UserService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = getUserFromRequest(req);
		
		String errorMessage = userSvc.getUserLoginMessage(user);
		
		if(errorMessage != null) {
			fillUserParameters(user, req);
			req.setAttribute("message", errorMessage);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		} else {
			user = userSvc.getUserByUsername(user.getUsername());
			AuthenticationManager.login(user.getId(), req.getSession());
			resp.sendRedirect("home.jsp");
		}
	}
	
	private User getUserFromRequest(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = new User(username, password, null, null, null);
		return user;
	}
	
	private void fillUserParameters(User user, HttpServletRequest req) {
		req.setAttribute("username", user.getUsername());
	}
	
}
