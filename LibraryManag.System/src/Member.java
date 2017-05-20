import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Bessada
 *
 */
public class Member {

	private String email; // unique for each user
	private String password; // used to validate the user
	private int No_of_booksBorrowed; // maximum of 3 books
	private ArrayList<String> bookss; // this is a temprary till the books class is designed

	private ArrayList<Books> books; // list of books for each member

	public Member(String email, String password) { //when I create the memeber I will not add him till am sure he is ok

		this.email = email;
		this.password = password;
		No_of_booksBorrowed = 0;
		bookss = new ArrayList<String>();
		books = new ArrayList<Books>(); // Also I will use that later
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

	public ArrayList<String> getBookss() {
		return bookss;
	}

	public ArrayList<Books> getBooks() {
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

	public boolean isMemberExists(String email, String password) {  ///// lesa 3aiz to add the member here to the database
		String qureyEmailBack = "";

		// send qurey to the database
		// get the qurey back in the queryEmailBack Strin
	// mesh me7tag to implement this if ... el database will return found or not		 
		if (qureyEmailBack.equals(email))
			//
			return true;
		else
			// Add member lel database using (email + password)
			return false;
	}
	
	public boolean isPasswordCorrect(String email, String password){
		String qureyPasswordBack = "";
		// send qurey to the database
	    // get the qurey back in the queryPasswordBack String
		if (qureyPasswordBack.equals(password))
			return true;
		else
			return false;
	}
	
	public boolean isAdmin(String email, String password){// save it fel database ... this is the admin email
		  if(email.equals("admin@jku.com") && password.equals("123456"))  
		    	return true;
		  else 
			  return false;
		  }

	public void addBooks(Books b) {
        this.books.add(b);
	}
	
	public void removeBooks(Books b) {
        this.books.remove(b);
	}
	
	public String isPrintBooksList(){
		String s = "";
		
		for(int i =0; i<books.size(); i++){
			s =  s + books.get(i).getName() + "\n";
		}
		return s;
	}

}
