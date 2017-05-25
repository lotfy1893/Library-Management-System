import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author Praktikant05
 */
public class Member {

	private String email; // unique for each user
	private String password; // used to validate the user
	private int No_of_booksBorrowed; // maximum of 3 books
	private boolean isAdmin;

	public Member(String email, String password) { // when I create the memeber
													// I will not add him till
													// am sure he is ok

		this.email = email;
		this.password = password;
		No_of_booksBorrowed = 0;
		books = new ArrayList<Book>(); // Also I will use that later
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getNo_of_booksBorrowed() {
		return No_of_booksBorrowed;
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

	public void setNo_of_booksBorrowed(int no_of_booksBorrowed) {
		No_of_booksBorrowed = no_of_booksBorrowed;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	private ArrayList<Book> books; // list of books for each member


	public ArrayList<Book> getBooks() {
		return books;
	}

	public boolean isEmailFormatCorrect(String email) {
		Pattern p = Pattern.compile("\\b[a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,4}\\b");
		Matcher m = p.matcher(email);
		if (m.find())
			return true;
		else
			return false;
	}

	public void addBooks(Book b) {
		this.books.add(b);
	}

	public void removeBooks(Book b) {
		this.books.remove(b);
	}
}
