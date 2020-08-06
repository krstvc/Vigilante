package ip.vigilante.model;

public class User {

	private int id;

	private String username;
	private String passwordHash;

	private String name;
	private String surname;
	private String email;

	private String imageURI;

	private String countryCode;
	private String region;
	private String city;

	private boolean isSubscribedToAppNotifications;
	private boolean isSubscribedToMailNotifications;
	private boolean isAdmin;
	private boolean isApproved;
	private boolean isBlocked;
	private boolean isDeleted;
	private boolean isLogged;

	private int loginCount;

	public User(int id, String username, String passwordHash, String name, String surname, String email,
			String imageURI, String countryCode, String region, String city, boolean isSubscribedToAppNotifications,
			boolean isSubscribedToMailNotifications, boolean isAdmin, boolean isApproved, boolean isBlocked,
			boolean isLogged, boolean isDeleted, int loginCount) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.imageURI = imageURI;
		this.countryCode = countryCode;
		this.region = region;
		this.city = city;
		this.isSubscribedToAppNotifications = isSubscribedToAppNotifications;
		this.isSubscribedToMailNotifications = isSubscribedToMailNotifications;
		this.isAdmin = isAdmin;
		this.isApproved = isApproved;
		this.isBlocked = isBlocked;
		this.isDeleted = isDeleted;
		this.isLogged = isLogged;
		this.loginCount = loginCount;
	}

	public User(String username, String passwordHash, String name, String surname, String email) {
		this(0, username, passwordHash, name, surname, email, "", "", "", "", false, false, false, false, false, false,
				false, 0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isSubscribedToAppNotifications() {
		return isSubscribedToAppNotifications;
	}

	public void setSubscribedToAppNotifications(boolean isSubscribedToAppNotifications) {
		this.isSubscribedToAppNotifications = isSubscribedToAppNotifications;
	}

	public boolean isSubscribedToMailNotifications() {
		return isSubscribedToMailNotifications;
	}

	public void setSubscribedToMailNotifications(boolean isSubscribedToMailNotifications) {
		this.isSubscribedToMailNotifications = isSubscribedToMailNotifications;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public boolean hasImage() {
		return this.imageURI != null && !this.imageURI.equals("");
	}

	public String getFullName() {
		return name + " " + surname;
	}

}
