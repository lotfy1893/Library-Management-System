import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MemberRepository {

	private Connection myConn;

	public MemberRepository() {
		try {
			// get the connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_library?useSSL=false", "root",
					"123456789");
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public Member getMemberById(int id) throws SQLException {
		Statement myStmt = myConn.createStatement();
		//ResultSet myRs = myStmt.executeQuery("");
		//TODO query to retrieve member all info by id

		return null;
	}

	public ArrayList<Book> getBorrowedBooks(int id) {
		//TODO query to retrive all the member books
		//ResultSet myRs = myStmt.executeQuery("");
		return null;
	}
	
	public boolean isMemberExists(String email){
		//TODO query to see whether the member is in our db or not
		//ResultSet myRs = myStmt.executeQuery("");
		return true;
	}
	
	public boolean passwordMatchesForLogin(String email, String password){
		//TODO query to check matching email and password invoked with login
		//ResultSet myRs = myStmt.executeQuery("");
		return true;
	}
	
	public boolean exceededNumberOfAllowedBooks(int id){
		//TODO query to sum no of borrowed books check if it is > 3
		//ResultSet myRs = myStmt.executeQuery("");
		return true;
	}
	
	public boolean exceededReturnDue(int id){
		//TODO query to check that the user doesn't have a book that excedds due
		//ResultSet myRs = myStmt.executeQuery("");
		return true;
	}
}
