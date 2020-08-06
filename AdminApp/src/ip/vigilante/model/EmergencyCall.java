package ip.vigilante.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import ip.vigilante.service.CallCategoryService;

public class EmergencyCall {

	private int id;
	private int callCategoryId;
	
	private String title;
	private String description;
	private String imageURI;
	
	private Date time;
	private String location;
	private int reportCount;
	
	private boolean isDeleted;
	
	private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");


	public EmergencyCall(int id, int callCategoryId, String title, String description, String imageURI, Date time,
			String location, int reportCount, boolean isDeleted) {
		super();
		this.id = id;
		this.callCategoryId = callCategoryId;
		this.title = title;
		this.description = description;
		this.imageURI = imageURI;
		this.time = time;
		this.location = location;
		this.setReportCount(reportCount);
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
	
	public String getCallCategoryString() {
		CallCategoryService callSvc = CallCategoryService.getInstance();
		CallCategory cat = callSvc.getCategoryById(callCategoryId);
		return cat.getCategory();
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
	
	public String getTimeFormatted() {
		return df.format(time);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
