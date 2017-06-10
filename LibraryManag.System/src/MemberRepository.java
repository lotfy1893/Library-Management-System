import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MemberRepository {

	private Connection myConn;

	public MemberRepository() {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_library?useSSL=false", "root",
					"123456789");
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public Member getMemberByEmail(String email) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member WHERE email=" + email + ");");
		// TODO query to retrieve member all info by id

		Member loggedInMember = null;
		while (stringQuery.next()) {
			String queryResultID = stringQuery.getString("member_id");
			String queryResultFullName = stringQuery.getString("full_name");
			String queryResultEmail = stringQuery.getString("email");
			String queryResultType = stringQuery.getString("type");
			String queryResultPassword = stringQuery.getString("password");

			loggedInMember = new Member(queryResultEmail, queryResultPassword, queryResultFullName);
			loggedInMember.setId(Integer.parseInt(queryResultID));
			loggedInMember.setAdmin(queryResultType.equalsIgnoreCase("member") ? false : true);
		}
		return loggedInMember;
	}

	public boolean registerNewMember(Member member) throws SQLException {

		if (isMemberExists(member.getEmail()))
			return false;

		Statement myStmt = null;
		try {
			myStmt = myConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			myStmt.executeQuery("INSERT INTO member(full_name,email,type,password) VALUES(" + member.getFullName() + ","
					+ member.getEmail() + "," + member.isAdmin() + "," + member.getFullName() + ");");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		// TODO query to insert the new member in the db

	}

	public ArrayList<Book> getBorrowedBooks(int id) throws SQLException, ParseException {
		// TODO query to retrive all the member books
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("");
		ArrayList<Book> result = new ArrayList<>();

		while (stringQuery.next()) {
			String name = stringQuery.getString("book_name");
			String desc = stringQuery.getString("description");
			String category = stringQuery.getString("category");
			String author = stringQuery.getString("author");
			String bookIssue = stringQuery.getString("book_issue_date");
			String entryDate = stringQuery.getString("entry_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = (Date) new SimpleDateFormat("yyyy/dd/MM").parse(bookIssue);
			Timestamp tsEntry = Timestamp.valueOf(entryDate);
			int vers = Integer.parseInt(version);
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, tsEntry, vers, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_name")));
			result.add(book);
		}

		return result;
	}

	public boolean isMemberExists(String email) throws SQLException {
		// TODO query to see whether the member is in our db or not
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member where email = '" + email + "'");
		if (stringQuery == null || stringQuery.equals("")) {
			return false;
		}
		return true;
	}

	public Member passwordMatchesForLogin(String email, String password) throws SQLException {
		// TODO query to check matching email and password invoked with login
		if (!isMemberExists(email))
			return null;
		Statement myStmt = myConn.createStatement();
		ResultSet stringQueryPassword = myStmt.executeQuery("SELECT * FROM member where email = '" + email + "'");
		while (stringQueryPassword.next()) {
			String resultingPassword = stringQueryPassword.getString("password");

			if (password.equals(resultingPassword)) {
				return getMemberByEmail(email);
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * this method returns all members in db
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Member> getAllMembers() throws SQLException{
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member");
		// TODO query to retrieve member all info by id
		ArrayList<Member> allMembers = new ArrayList<>();
		Member loggedInMember = null;
		while (stringQuery.next()) {
				String queryResultID = stringQuery.getString("member_id");
				String queryResultFullName = stringQuery.getString("full_name");
				String queryResultEmail = stringQuery.getString("email");
				String queryResultType = stringQuery.getString("type");
				String queryResultPassword = stringQuery.getString("password");

				loggedInMember = new Member(queryResultEmail, queryResultPassword, queryResultFullName);
				loggedInMember.setId(Integer.parseInt(queryResultID));
				loggedInMember.setAdmin(queryResultType.equalsIgnoreCase("member") ? false : true);
				allMembers.add(loggedInMember);
		}
		return allMembers;
	}

	public boolean exceededReturnDue(int id) {
		// TODO query to check that the user doesn't have a book that excedds
		// due
		// ResultSet myRs = myStmt.executeQuery("");
		return true;
	}
}
