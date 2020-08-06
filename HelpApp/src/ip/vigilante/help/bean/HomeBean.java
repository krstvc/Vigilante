package ip.vigilante.help.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ip.vigilante.model.CallCategory;
import ip.vigilante.model.EmergencyCall;
import ip.vigilante.service.CallCategoryService;
import ip.vigilante.service.EmergencyCallService;

@ManagedBean(name="home")
@RequestScoped
public class HomeBean {

	private String title;
	private String description;
	private String imageURI;
	private String location;
	private int categoryId;
	
	private ArrayList<CallCategory> categories;
	
	private static EmergencyCallService callSvc;
	private static CallCategoryService catSvc;
	
	private static String HOME_REDIRECT = "home.xhtml?faces-redirect=true";
	
	public HomeBean() {
		callSvc = EmergencyCallService.getInstance();
		catSvc = CallCategoryService.getInstance();
		
		categories = catSvc.getCategories();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public ArrayList<CallCategory> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CallCategory> categories) {
		this.categories = categories;
	}
	
	public String addCall() {
		String ret = null;
		
		Date time = new Date();
		
		EmergencyCall call = new EmergencyCall(categoryId, title, description, imageURI, time, location, 0, false);
		boolean added = callSvc.addCall(call);
		if(!added) {
			ret = "An error occurred";
			FacesContext.getCurrentInstance().addMessage("add-call-form:message", new FacesMessage(ret));
		} else {
			ret = HOME_REDIRECT;
		}
		
		return ret;
	}
	
}
