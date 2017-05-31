import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Praktikant05
 */
public class Member {

	private int id;
	private String email; // unique for each user
	private String password; // used to validate the user
	private int No_of_booksBorrowed; // maximum of 3 books
	private boolean isAdmin;
	private String fullName;
	private ArrayList<Book> books;

	public Member(String email, String password) {

		this.email = email;
		this.password = password;
		No_of_booksBorrowed = 0;
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

	public boolean isEmailFormatCorrect(String email) {
		Pattern p = Pattern.compile("\\b[a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,4}\\b");
		Matcher m = p.matcher(email);
		if (m.find())
			return true;
		else
			return false;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		if (books.size() == 3) {
			System.out.println("Allowed 3 books only for each member.");
			return;
		}
		books.add(book);
	}
	public void removeBook(int id){
		for(Book book: books){
			if(book.getId() == id)
				books.remove(book);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
