package ip.vigilante.model;

public class PostEmergencyCategory {

	private int id;
	private int postId;
	private int emergencyCategoryId;
	

	public PostEmergencyCategory(int id, int postId, int emergencyCategoryId) {
		super();
		this.id = id;
		this.postId = postId;
		this.emergencyCategoryId = emergencyCategoryId;
	}

	public PostEmergencyCategory(int postId, int emergencyCategoryId) {
		this.postId = postId;
		this.emergencyCategoryId = emergencyCategoryId;
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

	public int getEmergencyCategoryId() {
		return emergencyCategoryId;
	}

	public void setEmergencyCategoryId(int emergencyCategoryId) {
		this.emergencyCategoryId = emergencyCategoryId;
	}
	
}
