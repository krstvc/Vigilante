package ip.vigilante.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
	
	private int id;
	private int userId;
	
	private String title;
	private String content;
	private String link;
	private String videoURI;
	private String location;
	
	private Date time;
	
	private boolean isEmergencyAlert;
	private boolean isDeleted;
	
	private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
	

	public Post(int id, int userId, String title, String content, String link, String videoURI, String location,
			Date time, boolean isEmergencyAlert, boolean isDeleted) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.link = link;
		this.videoURI = videoURI;
		this.location = location;
		this.time = time;
		this.isEmergencyAlert = isEmergencyAlert;
		this.isDeleted = isDeleted;
	}
	
	public Post(int userId, String title, String content, String link, String videoURI, String location,
			Date time, boolean isEmergencyAlert, boolean isDeleted) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.link = link;
		this.videoURI = videoURI;
		this.location = location;
		this.time = time;
		this.isEmergencyAlert = isEmergencyAlert;
		this.isDeleted = isDeleted;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVideoURI() {
		return videoURI;
	}

	public void setVideoURI(String videoURI) {
		this.videoURI = videoURI;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getTimeFormatted() {
		return time != null ? df.format(time) : "";
	}
	
	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isEmergencyAlert() {
		return isEmergencyAlert;
	}

	public void setEmergencyAlert(boolean isEmergencyAlert) {
		this.isEmergencyAlert = isEmergencyAlert;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
