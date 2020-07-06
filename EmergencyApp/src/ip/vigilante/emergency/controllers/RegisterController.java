package ip.vigilante.emergency.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ip.vigilante.emergency.model.User;
import ip.vigilante.emergency.services.UserService;
import ip.vigilante.emergency.util.AuthenticationManager;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService userSvc;
	
	public RegisterController() {
		userSvc = UserService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("register.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = getUserFromRequest(req);
		
		String errorMessage = userSvc.getUserRegistrationMessage(user);
		
		if(errorMessage != null) {
			fillUserParameters(user, req);
			req.setAttribute("message", errorMessage);
			req.getRequestDispatcher("register.jsp").forward(req, resp);
		} else {
			AuthenticationManager.register(user, req.getSession());
			resp.sendRedirect("profile.jsp");
		}
	}
	
	private User getUserFromRequest(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		
		User user = new User(username, password, name, surname, email);
		return user;
	}
	
	private void fillUserParameters(User user, HttpServletRequest req) {
		req.setAttribute("username", user.getUsername());
		req.setAttribute("email", user.getEmail());
		req.setAttribute("name", user.getName());
		req.setAttribute("surname", user.getSurname());
	}

}
