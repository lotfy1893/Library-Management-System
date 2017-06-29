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

	/**
	 * returns back a unique book by the combination of the book's title and
	 * author which uniquely identify the book in our database
	 * 
	 * @param name
	 * @param author
	 * @return unique object of type Book
	 * @throws SQLException
	 * @throws ParseException
	 */
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

	/**
	 * takes object Book with all its info and inserts it into our database
	 * 
	 * @param book
	 * @return true if registration succeeds and false otherwise
	 * @throws SQLException
	 * @throws ParseException
	 */
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

	/**
	 * this method search for a book in the db by using one or more of those
	 * title author or category and if all are empty values it returns all the
	 * book in the db
	 * 
	 * @param title
	 * @param author
	 * @param category
	 * @return list of books matches the search variables
	 * @throws SQLException
	 * @throws ParseException
	 */
	public ArrayList<Book> searchForABook(String title, String author, String category)
			throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = null;

		if (title.length() > 1 || author.length() > 1 || category.length() > 1) {
			stringQuery = myStmt.executeQuery("select * from book where book_name like '%" + title
					+ "%' and author like '%" + author + "%' and category like '%" + category + "%'");
		} else {
			stringQuery = myStmt.executeQuery("select * from book where book_name like '" + title
					+ "%' and author like '" + author + "%' and category like '" + category + "%'");
		}

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

	/**
	 * this method takes a certain book and update its entry by increasing its
	 * number of copies after a member returns a book
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void incrementCopiesOfBook(Book book) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("UPDATE book SET copies = copies + 1 where book_id=" + book.getId());
	}

	/**
	 * this method takes a certain book and update its entry by increasing its
	 * number of copies after a member borrows a book
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void decrementCopiesOfBook(Book book) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("UPDATE book SET copies = copies - 1 where book_id=" + book.getId());
	}

	/**
	 * this method takes a book and a member inserts entry in the
	 * transactions/borrows table in the database to commit it
	 * 
	 * @param borrower
	 * @param borrowedBook
	 * @return true if the borrow succeeds and false otherwise
	 * @throws SQLException
	 * @throws ParseException
	 */
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

			myStmt.executeUpdate("INSERT INTO `borrow` (return_date,borrow_date,member_id,book_id,status) VALUES"
					+ "(DATE_ADD(CURDATE() , interval " + borrowPeriod + " day),CURDATE()," + borrower.getId() + ","
					+ borrowedBook.getId() + "," + "'A');");
			decrementCopiesOfBook(borrowedBook);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * this method checks if the member/borrower has already a copy of a given
	 * book to make sure that he doesn't take more than one copy (library
	 * policy)
	 * 
	 * @param borrower
	 * @param borrowedBook
	 * @return true if he has a copy and false otherwise
	 * @throws SQLException
	 */
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

	/**
	 * this method completes the process of a member returning a book to db by
	 * deactivating the entry in the table
	 * 
	 * @param borrower
	 * @param borrowedBook
	 * @return true if the return succeeds and false otherwise
	 * @throws SQLException
	 */
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

	/**
	 * 
	 * @return all the books in the library from the db
	 * @throws SQLException
	 * @throws ParseException
	 */
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

	/**
	 * given member id and book id we browse the transactions/borrows table to
	 * get a certain entry to check when this member should return this book
	 * 
	 * @param memberId
	 * @param bookId
	 * @return return date for the book and null otherwise
	 * @throws SQLException
	 * @throws ParseException
	 */
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

	/**
	 * this method removes an entry of borrowing a book for given member and
	 * book from the db entirely we use it for test issues
	 * 
	 * @param memberId
	 * @param bookId
	 * @throws SQLException
	 */
	public void removeBorrowedBookTransaction(int memberId, int bookId) throws SQLException {
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate(
				"delete from borrow where member_id='" + memberId + "' and book_id='" + bookId + "' and status='A'");
	}

	/**
	 * this method gets a unique book and it removes it from the db of the
	 * library
	 * 
	 * @param title
	 * @param author
	 * @return true if removal succeeds and false otherwise
	 */
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
