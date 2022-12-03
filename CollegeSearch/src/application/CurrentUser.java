package application;

public class CurrentUser {
	private int id;
	private String currentFirstName;
	private String currentLastName;
	private String currentUserName;
	private String currentEmail;
	private String currentPassword;
	private String currentSchool;
	private String favorites;
	CurrentUser(int id, String firstName, String lastName, String userName, String email, String passWord, String favorites) {
		this.id=id;
		this.currentFirstName=firstName;
		this.currentLastName=lastName;
		this.currentUserName=userName;
		this.currentEmail=email;
		this.currentPassword=passWord;
		this.currentSchool=null;
		this.favorites=favorites;
	}

	public String getFavorites() {
		return favorites;
	}

	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}

	public String getCurrentSchool() {
		return currentSchool;
	}

	public void setCurrentSchool(String currentSchool) {
		this.currentSchool = currentSchool;
	}

	public CurrentUser() {
		this.id=0;
		this.currentFirstName=null;
		this.currentLastName=null;
		this.currentUserName=null;
		this.currentEmail=null;
		this.currentPassword=null;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrentFirstName() {
		return currentFirstName;
	}

	public void setCurrentFirstName(String currentFirstName) {
		this.currentFirstName = currentFirstName;
	}

	public String getCurrentLastName() {
		return currentLastName;
	}

	public void setCurrentLastName(String currentLastName) {
		this.currentLastName = currentLastName;
	}

	public String getCurrentEmail() {
		return currentEmail;
	}

	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
}
