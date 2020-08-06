package ip.vigilante.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostComment {
	
	private int id;
	private int postId;
	private int userId;
	
	private String content;
	private String imageURI;
	private Date time;
	
	private boolean isDeleted;
	
	private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");


	public PostComment(int id, int postId, int userId, String content, String imageURI, Date time, boolean isDeleted) {
		super();
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.imageURI = imageURI;
		this.time = time;
		this.isDeleted = isDeleted;
	}
	
	public PostComment(int postId, int userId, String content, String imageURI, Date time, boolean isDeleted) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.imageURI = imageURI;
		this.time = time;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	public String getTimeFormatted() {
		return time != null ? df.format(time) : "";
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
