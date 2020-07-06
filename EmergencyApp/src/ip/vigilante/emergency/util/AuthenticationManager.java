package ip.vigilante.emergency.util;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import ip.vigilante.emergency.model.Login;
import ip.vigilante.emergency.model.User;
import ip.vigilante.emergency.services.LoginService;
import ip.vigilante.emergency.services.UserService;

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
		
		Login login = new Login(0, userId, new Date(), null);
		boolean logged = loginSvc.addLogin(login);
		
		User user = userSvc.getUserById(userId);
		user.setLogged(true);
		user.setLoginCount(user.getLoginCount() + 1);
		boolean updated = userSvc.updateUser(user);
		
		session.setAttribute("userId", userId);
		activeSessions.put(session.getId(), login);
		
		ret = logged && updated;
		return ret;
	}
	
	public static boolean logout(HttpSession session) {
		boolean ret = false;
		
		int userId = (int)session.getAttribute("userId");
		
		Login login = activeSessions.get(session.getId());
		login.setLogoutTime(new Date());
		boolean loginUpdated = loginSvc.updateLogin(login);
		
		User user = userSvc.getUserById(userId);
		user.setLogged(false);
		boolean userUpdated = userSvc.updateUser(user);
		
		activeSessions.remove(session.getId());
		session.invalidate();
		
		ret = loginUpdated && userUpdated;
		return ret;
	}
	
	public static boolean register(User user, HttpSession session) {
		return userSvc.addUser(user);
	}

}
