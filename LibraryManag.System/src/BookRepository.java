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
	private MemberRepository memberRepository;

	public BookRepository() {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_library?useSSL=false", "root",
					"123456789");
			memberRepository = new MemberRepository();
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
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, version, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));

			return book;
		}

		return null;
	}

	public boolean registerNewBookInLibrary(Book book) throws SQLException, ParseException {
		if (getBookByAuthorAndName(book.getName(), book.getAuthor()) != null) {
			return false;
		}
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate(
				"INSERT INTO `book` (book_name,description,category,author,book_issue_date,version,copies,borrow_period) VALUES ('"
						+ book.getName() + "','" + book.getDescription() + "','" + book.getCategory() + "','"
						+ book.getAuthor() + "','" + book.getBookIssueDate() + "','" + book.getVersion() + "','"
						+ book.getNumberOfCopies() + "','" + book.getBorrowPeriod() + "')");
		return true;
	}

	public ArrayList<Book> searchForABook(String title, String author, String category)
			throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("select * from book where book_name like '" + title
				+ "%' and author like '" + author + "%' and category like '" + category + "%'");
		ArrayList<Book> result = new ArrayList<>();
		while (stringQuery.next()) {
			String name = stringQuery.getString("book_name");
			String authorStr = stringQuery.getString("author");
			String categoryStr = stringQuery.getString("category");
			String desc = stringQuery.getString("description");
			String bookIssue = stringQuery.getString("book_issue_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, categoryStr, authorStr, issue, version, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));
			result.add(book);
		}
		return result;
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

		if (checkIfAlreadyHasACopy(borrower, borrowedBook)) {
			return false;
		}

		ArrayList<Book> borrowedForMember = memberRepository.getBorrowedBooks(borrower.getEmail());
		if (borrowedForMember.size() == 3) {
			return false;
		}
		int copies = getBookByAuthorAndName(borrowedBook.getName(), borrowedBook.getAuthor()).getNumberOfCopies();
		if (copies <= 0) {
			return false;
		}

		try {
			int borrowPeriod = borrowedBook.getBorrowPeriod();
			Statement myStmt = myConn.createStatement();
			// myStmt.executeUpdate(
			// "INSERT INTO `borrow`
			// (return_date,borrow_date,member_id,book_id,status) VALUES
			// (CURDATE()+"
			// + borrowPeriod + ",CURDATE()," + borrower.getId() + "," +
			// borrowedBook.getId() + ",'A')");
			myStmt.executeUpdate("INSERT INTO `borrow` (return_date,borrow_date,member_id,book_id,status) VALUES"
					+ "(DATE_ADD(CURDATE() , interval " + borrowPeriod + " day),CURDATE()," + borrower.getId() + ","
					+ borrowedBook.getId() + "," + "'A')");
			decrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean checkIfAlreadyHasACopy(Member borrower, Book borrowedBook) throws SQLException {
		Statement stm = myConn.createStatement();
		ResultSet stringQuery = stm.executeQuery("select * From borrow where member_id= '" + borrower.getId()
				+ "' and book_id= '" + borrowedBook.getId() + "' and status = 'A'");
		if (stringQuery.next()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean returnBook(Member borrower, Book borrowedBook) throws SQLException {
		try {
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate("update borrow set status= 'N' where member_id= '" + borrower.getId()
					+ "' and book_id= '" + borrowedBook.getId() + "' and status = 'A'");
			this.incrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
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
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, version, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));
			results.add(book);
		}
		return results;
	}

	public Date getBookReturnDate(int memberId, int bookId) throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet queryString = myStmt.executeQuery("select * from borrow where status = 'A' and member_id='" + memberId
				+ "' and book_id='" + bookId + "'");
		Date borrow = null;
		while (queryString.next()) {
			String borrowDate = queryString.getString("return_date");

			borrow = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(borrowDate).getTime());
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
