package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.UserDAO;
import ip.vigilante.emergency.util.CredentialsManager;
import ip.vigilante.model.User;

public class UserService {

	private static UserService svc;
	
	private UserService() {}
	
	public static UserService getInstance() {
		if(svc == null) {
			svc = new UserService();
		}
		return svc;
	}
	
	
	public ArrayList<User> getAllUsers() {
		return UserDAO.selectAllUsers();
	}
	
	public User getUserById(int id) {
		return UserDAO.selectUserById(id);
	}
	
	public User getUserByUsername(String username) {
		return UserDAO.selectUserByUsername(username);
	}
	
	public User getUserByEmail(String email) {
		return UserDAO.selectUserByEmail(email);
	}
	
	public boolean addUser(User user) {
		String password = user.getPasswordHash();
		user.setPasswordHash(CredentialsManager.getHashedPassword(password));
		return UserDAO.insertUser(user);
	}
	
	public boolean updateUser(User user) {
		return UserDAO.updateUser(user);
	}
	
	public boolean deleteUser(int id) {
		return UserDAO.deleteUser(id);
	}
	
	// Helper methods
	
	public boolean usernameExists(String username) {
		User user = getUserByUsername(username);
		return user != null;
	}
	
	public boolean emailExists(String email) {
		User user = getUserByEmail(email);
		return user != null;
	}
	
	public boolean isPasswordCorrect(String username, String password) {
		User user = getUserByUsername(username);
		String hash = CredentialsManager.getHashedPassword(password);
		return user.getPasswordHash().equals(hash);
	}
	
	public boolean isUserApproved(String username) {
		User user = getUserByUsername(username);
		return user.isApproved();
	}
	
	public String getUserRegistrationMessage(User user) {
		String message = null;
		
		if(usernameExists(user.getUsername())) {
			message = "Username taken";
		} else if(emailExists(user.getEmail())) {
			message = "User with this email already exists";
		}
		
		return message;
	}
	
	public String getUserLoginMessage(User user) {
		String message = null;
		
		if(!usernameExists(user.getUsername())) {
			message = "User does not exist";
		} else if(!isPasswordCorrect(user.getUsername(), user.getPasswordHash())) {
			message = "Incorrect password";
		} else if(!isUserApproved(user.getUsername())) {
			message = "Account has not been approved";
		}
		
		return message;
	}
	
}
