package ip.vigilante.emergency.util;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import ip.vigilante.model.Login;
import ip.vigilante.model.User;
import ip.vigilante.service.LoginService;
import ip.vigilante.service.UserService;

public class AuthenticationManager {
	
	private static UserService userSvc;
	private static LoginService loginSvc;
	
	private static HashMap<String, Login> activeSessions;
	
	static {
		userSvc = UserService.getInstance();
		loginSvc = LoginService.getInstance();
		
		activeSessions = new HashMap<>();
	}
	
	public static boolean login(int userId, HttpSession session) {
		boolean ret = false;
		
		User user = userSvc.getUserById(userId);
		if(user.isApproved()) {
			Login login = new Login(0, userId, new Date(), null);
			boolean logged = loginSvc.addLogin(login);
			
			user.setLogged(true);
			user.setLoginCount(user.getLoginCount() + 1);
			boolean updated = userSvc.updateUser(user);
			
			activeSessions.put(session.getId(), login);
			
			ret = logged && updated;
		}
		
		session.setAttribute("userId", userId);
		
		return ret;
	}
	
	public static boolean logout(HttpSession session) {
		boolean ret = false;
		
		int userId = (int)session.getAttribute("userId");
		
		Login login = activeSessions.get(session.getId());
		boolean loginUpdated = true;
		
		if(login != null) {
			login.setLogoutTime(new Date());
			loginUpdated = loginSvc.updateLogin(login);
		}
		
		User user = userSvc.getUserById(userId);
		user.setLogged(false);
		boolean userUpdated = userSvc.updateUser(user);
		
		activeSessions.remove(session.getId());
		session.invalidate();
		
		ret = loginUpdated && userUpdated;
		return ret;
	}
	
	public static boolean register(User user, HttpSession session) {
		boolean ret = userSvc.addUser(user);
		session.setAttribute("userId", user.getId());
		return ret;
	}

}
