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

		while(stringQuery.next()) {
			String desc = stringQuery.getString("description");
			String category = stringQuery.getString("category");
			String bookIssue = stringQuery.getString("book_issue_date");
			String entryDate = stringQuery.getString("entry_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = (Date) new SimpleDateFormat("yyyy/dd/MM").parse(bookIssue);
			//Timestamp tsEntry = Timestamp.valueOf(entryDate);
			int vers = Integer.parseInt(version);
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, vers, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_name")));

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

	@SuppressWarnings("deprecation")
	public Date calculateReturnDate(Book book, Date borrowDate) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("select return_date from borrow");
		// query to get the period borrow of the book
		if (stringQuery != null) {
			int numberofDays = Integer.parseInt(stringQuery + "");
			Date copy = borrowDate;
			copy.setDate(copy.getDate() + numberofDays);
			return copy;
		}
		return null;
	}

	// insert boorrow date
	// insert return date
	public void inserBorrowAndReturnDateForABorrowedBook(Date borrow, Date returnDate, Book borrowBook)
			throws SQLException {
		Statement myStmt = myConn.createStatement();
		// myStmt.executeUpdate("INSERT INTO
		// member(full_name,email,type,password) VALUES('" +
		// member.getFullName() + "','"
		// + member.getEmail() + "','" + member.isAdmin() + "','" +
		// member.getFullName() + "')");
		myStmt.executeUpdate("");

		// insert those dates given the combination of authoer and book title

	}

	// increment copies
	public void incrementCopiesOfBook(Book book) throws SQLException {
		int newCopuies = book.getNumberOfCopies() + 1;
		Statement myStmt = myConn.createStatement();
		myStmt.executeQuery("");
	}

	// decrement copies
	public void decrementCopiesOfBook(Book book) throws SQLException {
		int newCopuies = book.getNumberOfCopies() - 1;
		Statement myStmt = myConn.createStatement();
		myStmt.executeQuery("");
	}

	public boolean borrowBook(Member borrower, Book borrowedBook) {
		try {
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate("");
			// insert into borrow date the date of today ONLY DATE NOT
			// TIME!!!!!!!!!
			// query to insert a borrowed book for this member
			// status of borrow entry is active nowN
			this.decrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
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

	public ArrayList<Book> getAllBooksInLibrary() throws SQLException, ParseException{
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * from book");
		ArrayList<Book> results = new ArrayList<>();
		
		while(stringQuery.next()){
		String name = stringQuery.getString("book_name");	
		String author = stringQuery.getString("author");
		String desc = stringQuery.getString("description");
		String category = stringQuery.getString("category");
		String bookIssue = stringQuery.getString("book_issue_date");
		String entryDate = stringQuery.getString("entry_date");
		String version = stringQuery.getString("version");
		String borrowPeriod = stringQuery.getString("borrow_period");
		String copies = stringQuery.getString("copies");

		Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
//		Timestamp tsEntry = Timestamp.valueOf(entryDate);
		int vers = Integer.parseInt(version);
		int borrow = Integer.parseInt(borrowPeriod);

		Book book = new Book(name, desc, category, author, issue,vers, borrow, Integer.parseInt(copies));
		book.setId(Integer.parseInt(stringQuery.getString("book_id")));
		results.add(book);
		}
		return results;
	}
	
	public Date getBookReturnDate(int memberId, int bookId) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("");
		// query to the borrow table to get the return date given member id and
		// book id to get a unique borrow entry and status is active
		return null;
	}
}
