package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.LoginDAO;
import ip.vigilante.model.Login;

public class LoginService {
	
	private static LoginService svc;
	
	private LoginService() {}
	
	public static LoginService getInstance() {
		if(svc == null) {
			svc = new LoginService();
		}
		return svc;
	}
	
	public ArrayList<Login> getLoginsForUser(int userId){
		return LoginDAO.selectAllLoginsForUser(userId);
	}
	
	public Login getLoginById(int id) {
		return LoginDAO.selectLoginById(id);
	}
	
	public boolean addLogin(Login login) {
		return LoginDAO.insertLogin(login);
	}
	
	public boolean updateLogin(Login login) {
		return LoginDAO.updateLogin(login);
	}

}
