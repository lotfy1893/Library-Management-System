import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * 
 *
 */
public class OverDueDAO {

	private Connection myConn;

	public OverDueDAO() throws Exception {

		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);

	}

	/**
	 * 
	 * @return List of
	 * @throws Exception
	 */
	public List<OverDue> getAllOverDue() throws Exception {
		List<OverDue> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(
					"select (datediff(curdate(),r.return_date)) overdueDays,m.email email,b.book_name bookName,b.entry_date entryDate,r.borrow_date borrowDate,r.return_date returnDate from book b,borrow r, member m where datediff(r.return_date, r.borrow_date)>2 and  m.member_id= r.member_id and r.book_id=b.book_id");

			while (myRs.next()) {
				OverDue tempOverdue = convertRowToOverDue(myRs);
				list.add(tempOverdue);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	public List<OverDue> searchOverDue(String email) throws Exception {
		List<OverDue> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			// myStmt = myConn.prepareStatement(
			// "select (datediff(curdate(),r.return_date)) OverdueDays,m.email
			// email,b.book_name bookName,b.entry_date entryDate,r.borrow_date
			// borrowDate,r.return_date returnDate from book b,borrow r, member
			// m where datediff(r.return_date, r.borrow_date)>2 and m.member_id=
			// r.member_id and r.book_id=b.book_id and m.email='"
			// + email + "'");
			myStmt = myConn.createStatement();

			// myStmt.setString(1, email);

			myRs = myStmt.executeQuery(
					"select (datediff(curdate(),r.return_date)) OverdueDays,m.email email,b.book_name bookName,b.entry_date entryDate,r.borrow_date borrowDate,r.return_date returnDate from book b,borrow r, member m where datediff(r.return_date, r.borrow_date)>2 and  m.member_id= r.member_id and r.book_id=b.book_id and m.email='"
							+ email + "'");

			while (myRs.next()) {
				OverDue tempOverdue = convertRowToOverDue(myRs);
				list.add(tempOverdue);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	private OverDue convertRowToOverDue(ResultSet myRs) throws SQLException {

		int overdueDays = myRs.getInt("overdueDays");
		String email = myRs.getString("email");
		String bookName = myRs.getString("bookName");
		String entryDate = myRs.getString("entryDate");
		String BorrowDate = myRs.getString("BorrowDate");
		String returnDate = myRs.getString("returnDate");

		OverDue tempOverdue = new OverDue(overdueDays, email, bookName, entryDate, BorrowDate, returnDate);

		return tempOverdue;
	}
	// (overdueDays, email, bookName, entryDate, BorrowDate,returnDate)

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {

		}

		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}

	public static void main(String[] args) throws Exception {

		OverDueDAO dao = new OverDueDAO();
		System.out.println(dao.searchOverDue(""));

		System.out.println(dao.getAllOverDue());
	}
}
