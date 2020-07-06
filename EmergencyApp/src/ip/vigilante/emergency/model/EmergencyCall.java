package ip.vigilante.emergency.model;

import java.util.Date;

public class EmergencyCall {

	private int id;
	private int callCategoryId;
	
	private String title;
	private String description;
	private String imageURI;
	
	private Date time;
	private String location;
	
	private boolean isDeleted;

	/**
	 * @param id				Unique ID
	 * @param callCategoryId	Unique ID of a emergency call category
	 * @param title				Title of the emergency call
	 * @param description		Description of the emergency call
	 * @param imageURI			Path to the image attached to the emergency call
	 * @param time				Time when emergency call was created
	 * @param location			Google Maps location where emergency occurred
	 * @param isDeleted			Is emergency call deleted
	 */
	public EmergencyCall(int id, int callCategoryId, String title, String description, String imageURI, Date time,
			String location, boolean isDeleted) {
		super();
		this.id = id;
		this.callCategoryId = callCategoryId;
		this.title = title;
		this.description = description;
		this.imageURI = imageURI;
		this.time = time;
		this.location = location;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCallCategoryId() {
		return callCategoryId;
	}

	public void setCallCategoryId(int callCategoryId) {
		this.callCategoryId = callCategoryId;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
