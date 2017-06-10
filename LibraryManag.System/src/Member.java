/**
 * @author Praktikant05
 */
public class Member {

	private int id;
	private String email; // unique for each user
	private String password; // used to validate the user
	private boolean isAdmin;
	private String fullName;

	public Member(String email, String password,String fullName) {

		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}
	

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
