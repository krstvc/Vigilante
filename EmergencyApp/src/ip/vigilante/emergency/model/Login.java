package ip.vigilante.emergency.model;

import java.util.Date;

public class Login {
	
	private int id;
	private int userId;
	
	private Date loginTime;
	private Date logoutTime;
	

	public Login(int id, int userId, Date loginTime, Date logoutTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public Date getLogoutTime() {
		return logoutTime;
	}
	
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
