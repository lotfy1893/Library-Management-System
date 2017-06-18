import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public Book getBookByAuthorAndName(String name, String author) throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery(
				"select * from book where book_name Like '%" + name + "%' And author Like '%" + author + "%';");

		while (stringQuery.next()) {
			String desc = stringQuery.getString("description");
			String category = stringQuery.getString("category");
			String bookIssue = stringQuery.getString("book_issue_date");
			// String entryDate = stringQuery.getString("entry_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			// Timestamp tsEntry = Timestamp.valueOf(entryDate);
			int vers = Integer.parseInt(version);
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, vers, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));

			return book;
		}

		return null;
	}

	public boolean registerNewBookInLibrary(Book book, Member member) throws SQLException, ParseException {
		if (getBookByAuthorAndName(book.getName(), book.getAuthor()) != null) {
			return false;
		}
		if (member.isAdmin()) {
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate(
					"INSERT INTO `book` (book_name,description,category,author,book_issue_date,version,copies,borrow_period) VALUES ('"
							+ book.getName() + "','" + book.getDescription() + "','" + book.getCategory() + "','"
							+ book.getAuthor() + "','" + book.getBookIssueDate() + "','" + book.getVersion() + "','"
							+ book.getNumberOfCopies() + "','" + book.getBorrowPeriod() + "')");
			return true;
		}
		return false;
	}

	public ArrayList<Book> findBooksByCategory(String category) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet queryString = myStmt.executeQuery("select * from book where category='" + category + "'");
		// TODO query to retrieve all book(s) with the input category

		return null;

	}

	public ArrayList<Book> findBooksByAuthor(String author) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet queryString = myStmt.executeQuery("select * from book where author='" + author + "'");
		// TODO query to retrieve all book(s) with the input author name all
		// combinations
		// for example John R. Wagner or John Wagner or Wagner R. ignoring the
		// cases of the letters

		return null;

	}

	public ArrayList<Book> findBooksByName(String name) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet queryString = myStmt.executeQuery("select * from book where book_name like '" + name + "'");
		// TODO query to retrieve all book(s) with the input name

		return null;

	}

	// increment copies
	public void incrementCopiesOfBook(Book book) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("UPDATE book SET copies = copies + 1 where book_id=" + book.getId());
	}

	// decrement copies
	public void decrementCopiesOfBook(Book book) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("UPDATE book SET copies = copies - 1 where book_id=" + book.getId());
	}

	public boolean borrowBook(Member borrower, Book borrowedBook) throws SQLException, ParseException {
		int copies = getBookByAuthorAndName(borrowedBook.getName(), borrowedBook.getAuthor()).getNumberOfCopies();
		if (copies <= 0)
			return false;
		try {
			int borrowPeriod = borrowedBook.getBorrowPeriod();
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate(
					"INSERT INTO `borrow` (return_date,borrow_date,member_id,book_id,status) VALUES (CURDATE()+"
							+ borrowPeriod + ",CURDATE()," + borrower.getId() + "," + borrowedBook.getId() + ",'A')");
			decrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean returnBook(Member borrower, Book borrowedBook) {
		try {
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate("");
			// query to update only the entry in borrow where id of book and id
			// of member and status is Active set the status to inactive
			this.incrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}

	public ArrayList<Book> getAllBooksInLibrary() throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * from book");
		ArrayList<Book> results = new ArrayList<>();

		while (stringQuery.next()) {
			String name = stringQuery.getString("book_name");
			String author = stringQuery.getString("author");
			String desc = stringQuery.getString("description");
			String category = stringQuery.getString("category");
			String bookIssue = stringQuery.getString("book_issue_date");
			// String entryDate = stringQuery.getString("entry_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			// Timestamp tsEntry = Timestamp.valueOf(entryDate);
			int vers = Integer.parseInt(version);
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, vers, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));
			results.add(book);
		}
		return results;
	}

	@SuppressWarnings("deprecation")
	public Date getBookReturnDate(int memberId, int bookId) throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet queryString = myStmt.executeQuery("select * from borrow where status = 'A' and member_id='" + memberId
				+ "' and book_id='" + bookId + "'");
		Date borrow = null;
		while (queryString.next()) {
			String borrowDate = queryString.getString("return_date");

			borrow = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(borrowDate).getTime());
			borrow.setDate(borrow.getDate() + 1);
		}
		return borrow;
	}

	public void removeBorrowedBookTransaction(int memberId, int bookId) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate(
				"delete from borrow where member_id='" + memberId + "' and book_id='" + bookId + "' and status='A'");
	}

	public boolean removeBookFromDB(String title, String author) {
		Statement myStmt;
		try {
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("DELETE FROM book WHERE book_name = '" + title + "' AND author = '" + author + "'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
