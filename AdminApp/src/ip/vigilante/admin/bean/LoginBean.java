package ip.vigilante.admin.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ip.vigilante.admin.util.AuthenticationManager;
import ip.vigilante.model.User;
import ip.vigilante.service.UserService;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UserService userSvc;
	
	private static String HOME_REDIRECT = "/home.xhtml?faces-redirect=true";
	private static String LOGIN_REDIRECT = "/login.xhtml?faces-redirect=true";
	
	private boolean loggedIn;
	private String username;
	private String password;
	
	private User user;
	
	static {
		userSvc = UserService.getInstance();
	}
	
	public LoginBean() {
		user = new User();
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String login() throws IOException {
		String ret = null;
		
		user = userSvc.getUserByUsername(username);
		if(user != null) {
			String userPw = user.getPasswordHash();
			
			if(userPw.equals(password)) {
				if(user.isAdmin()) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					ExternalContext extContext = facesContext.getExternalContext();
					HttpSession session = (HttpSession)extContext.getSession(true);
					
					AuthenticationManager.login(user.getId(), session);
					
					loggedIn = true;
					
					ret = HOME_REDIRECT;
				} else {
					ret = "You need to be an admin to log in";
					FacesContext.getCurrentInstance().addMessage("loginForm:username", new FacesMessage(ret));
				}
			} else {
				ret = "Incorrect password";
				FacesContext.getCurrentInstance().addMessage("loginForm:password", new FacesMessage(ret));
			}
		} else {
			ret = "User does not exist";
			FacesContext.getCurrentInstance().addMessage("loginForm:username", new FacesMessage(ret));
		}
		
		return ret;
	}
	
	public String logout() {
		String ret = null;
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(true);
		
		Object userId = session != null ? session.getAttribute("userId") : null;
		
		if(userId != null) {
			AuthenticationManager.logout(session);
			loggedIn = false;
			
			ret = LOGIN_REDIRECT;
		}
		
		return ret;
	}

}
