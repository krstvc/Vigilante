package ip.vigilante.emergency.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ip.vigilante.emergency.util.AuthenticationManager;
import ip.vigilante.model.User;
import ip.vigilante.service.UserService;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService userSvc;
	
	public LogoutController() {
		userSvc = UserService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		int userId = (int)session.getAttribute("userId");
		User user = userSvc.getUserById(userId);
		
		if(user != null) {
			AuthenticationManager.logout(session);
		}
		
		resp.sendRedirect("home");
	}

}
