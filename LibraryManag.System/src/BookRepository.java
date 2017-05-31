import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookRepository {
	private Connection myConn;

	public BookRepository() {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_library?useSSL=false", "root",
					"123456789");
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public Book getBookById(int id) throws SQLException {
		Statement myStmt = myConn.createStatement();
		// ResultSet myRs = myStmt.executeQuery("");
		// TODO query to retrieve book by id

		return null;
	}

	public void registerNewBookInLibrary(Book book, Member member) throws SQLException {
		if (member.isAdmin()) {
			Statement myStmt = myConn.createStatement();
			// ResultSet myRs = myStmt.executeQuery("");
			// TODO query to insert new book
		}
	}

	public ArrayList<Book> findBooksByCategory(Book.Category category) throws SQLException {
		Statement myStmt = myConn.createStatement();
		// ResultSet myRs = myStmt.executeQuery("");
		// TODO query to retrieve all book(s) with the input category

		return null;

	}
	
	public ArrayList<Book> findBooksByAuthor(String author) throws SQLException {
		Statement myStmt = myConn.createStatement();
		// ResultSet myRs = myStmt.executeQuery("");
		// TODO query to retrieve all book(s) with the input author name all combinations
		// for example John R. Wagner or John Wagner or Wagner R. ignoring the cases of the letters

		return null;

	}
	
	public ArrayList<Book> findBooksByName(String name) throws SQLException {
		Statement myStmt = myConn.createStatement();
		// ResultSet myRs = myStmt.executeQuery("");
		// TODO query to retrieve all book(s) with the input name

		return null;

	}
	
	
}
