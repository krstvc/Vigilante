package ip.vigilante.model;

public class CallCategory {

	private int id;
	
	private String category;
	
	private boolean isDeleted;


	public CallCategory(int id, String category, boolean isDeleted) {
		super();
		this.id = id;
		this.category = category;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
