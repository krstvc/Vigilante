package ip.vigilante.emergency.model;

public class Image {
	
	private int id;
	private int postId;
	
	private String imageURI;
	
	private boolean isDeleted;

	/**
	 * @param id			Unique ID
	 * @param postId		Unique ID of a post
	 * @param imageURI		Path to the image content
	 * @param isDeleted		Is image deleted
	 */
	public Image(int id, int postId, String imageURI, boolean isDeleted) {
		super();
		this.id = id;
		this.postId = postId;
		this.imageURI = imageURI;
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

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
