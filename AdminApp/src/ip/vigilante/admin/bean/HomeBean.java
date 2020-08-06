package ip.vigilante.admin.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ip.vigilante.admin.util.CredentialsManager;
import ip.vigilante.model.CallCategory;
import ip.vigilante.model.EmergencyCall;
import ip.vigilante.model.User;
import ip.vigilante.service.CallCategoryService;
import ip.vigilante.service.EmergencyCallService;
import ip.vigilante.service.UserService;

@ManagedBean(name="home")
@RequestScoped
public class HomeBean {
	
	private int userCount;
	private int onlineUserCount;
	private String categoryName;
	
	private List<User> users;
	private List<User> nonApprovedUsers;
	
	private List<EmergencyCall> emergencyCalls;
	private List<CallCategory> callCategories;
	
	private UserService userSvc;
	private CallCategoryService catSvc;
	private EmergencyCallService callSvc;
	
	public HomeBean() {
		userSvc = UserService.getInstance();
		catSvc = CallCategoryService.getInstance();
		callSvc = EmergencyCallService.getInstance();
		
		users = userSvc.getAllUsers();
		nonApprovedUsers = users.stream()
				.filter(u -> !u.isApproved() && !u.isBlocked())
				.collect(Collectors.toList());
		
		emergencyCalls = callSvc.getCalls();
		setCallCategories(catSvc.getCategories());
		
		userCount = userSvc.getUserCount();
		onlineUserCount = userSvc.getOnlineUserCount();
	}
	
	public void init() {
		
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getOnlineUserCount() {
		return onlineUserCount;
	}

	public void setOnlineUserCount(int onlineUserCount) {
		this.onlineUserCount = onlineUserCount;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getNonApprovedUsers() {
		return nonApprovedUsers;
	}

	public void setNonApprovedUsers(List<User> nonApprovedUsers) {
		this.nonApprovedUsers = nonApprovedUsers;
	}

	public List<EmergencyCall> getEmergencyCalls() {
		return emergencyCalls;
	}

	public void setEmergencyCalls(List<EmergencyCall> emergencyCalls) {
		this.emergencyCalls = emergencyCalls;
	}
	
	public List<CallCategory> getCallCategories() {
		return callCategories;
	}

	public void setCallCategories(List<CallCategory> callCategories) {
		this.callCategories = callCategories;
	}
	
	public String blockUser() {
		String ret = null;
		
		Map<String,String> reqParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(reqParams.containsKey("id")) {
			int id = Integer.parseInt(reqParams.get("id"));
			User user = userSvc.getUserById(id);
			
			user.setBlocked(true);
			userSvc.updateUser(user);
			
			users.forEach(u -> {
				if(u.getId() == id) {
					u.setBlocked(true);
				}
			});
		}
		
		return ret;
	}
	
	public String approveUser() {
		String ret = null;
		
		Map<String,String> reqParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(reqParams.containsKey("id")) {
			int id = Integer.parseInt(reqParams.get("id"));
			User user = userSvc.getUserById(id);
			
			user.setApproved(true);
			userSvc.updateUser(user);
			
			users.forEach(u -> {
				if(u.getId() == id) {
					u.setApproved(true);
				}
			});
		}
		
		return ret;
	}
	
	public String passwordReset() {
		String ret = null;
		
		Map<String,String> reqParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(reqParams.containsKey("id")) {
			int id = Integer.parseInt(reqParams.get("id"));
			User user = userSvc.getUserById(id);
			
			String newPassword = getRandomPassword();
			String hashed = CredentialsManager.getHashedPassword(newPassword);
			
			user.setPasswordHash(hashed);
			userSvc.updateUser(user);
			
			users.forEach(u -> {
				if(u.getId() == id) {
					u.setPasswordHash(hashed);
				}
			});
		}
		
		return ret;
	}

	public String addCategory() {
		String ret = null;
		
		ArrayList<CallCategory> categories = catSvc.getCategories();
		boolean exists = false;
		
		for(CallCategory cc : categories) {
			if(cc.getCategory().equals(categoryName)) {
				exists = true;
				break;
			}
		}
		
		if(!exists) {
			CallCategory cat = new CallCategory(categoryName, false);
			catSvc.addCategory(cat);
			categoryName = null;
		} else {
			ret = "Category already exists";
			FacesContext.getCurrentInstance().addMessage("add-category-form:category-name-input", new FacesMessage(ret));
		}
		
		return ret;
	}
	
	public String deleteCategory() {
		String ret = null;
		
		Map<String,String> reqParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(reqParams.containsKey("id")) {
			int id = Integer.parseInt(reqParams.get("id"));

			catSvc.deleteCategory(id);
			callCategories.removeIf(c -> c.getId() == id);
		}
		
		return ret;
	}
	
	public String deleteEmergencyCall() {
		String ret = null;
		
		Map<String,String> reqParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(reqParams.containsKey("id")) {
			int id = Integer.parseInt(reqParams.get("id"));

			callSvc.deleteCall(id);
			emergencyCalls.removeIf(c -> c.getId() == id);
		}
		
		return ret;
	}
	
	private String getRandomPassword() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "").substring(0, 10);
	}

}
