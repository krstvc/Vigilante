package ip.vigilante.emergency.model;

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

	/**
	 * @param id			Unique ID
	 * @param postId		Unique ID of a post
	 * @param userId		Unique ID of a user
	 * @param content		Textual content of the comment
	 * @param imageURI		Path to the image attached to the comment
	 * @param time			Time when comment was created
	 * @param isDeleted		Is comment deleted
	 */
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
