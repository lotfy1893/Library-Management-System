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
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member WHERE email='" + email + "'");
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
	
	public int getIdMemberByEmail(String email) throws SQLException{
		Member m = getMemberByEmail(email);
		return m.getId();
	}

	public boolean registerNewMember(Member member) throws SQLException {

		if (isMemberExists(member.getEmail())) {
			System.out.println("i am here");
			return false;
		}

		Statement myStmt = null;
		try {
			myStmt = myConn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			String type = member.isAdmin() ? "Admin" : "Member";
			myStmt.executeUpdate("INSERT INTO member(full_name,email,type,password) VALUES('" + member.getFullName()
					+ "','" + member.getEmail() + "','" + type + "','" + member.getPassword() + "')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("exception catched from the ssql");
			return false;
		}
	}

	public ArrayList<Book> getBorrowedBooks(String email) throws SQLException, ParseException {
		// TODO query to retrive all the member books
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery(
				"select m.email,b.book_name,b.description,b.category,b.author,b.book_issue_date,b.entry_date,b.version,b.borrow_period,b.copies from book b,borrow r, member m where m.email='"
						+ email + "' and m.member_id= r.member_id and r.book_id=b.book_id);"); // add where also the status is Active 
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
		//	Timestamp tsEntry = Timestamp.valueOf(entryDate);
			int vers = Integer.parseInt(version);
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, vers, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_name")));
			result.add(book);
		}

		return result;
	}

	public boolean isMemberExists(String email) throws SQLException {
		// TODO query to see whether the member is in our db or not
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member where email = '" + email + "'");
		if (!stringQuery.next()) {
			return false;
		}
		return true;
	}

	public Member passwordMatchesForLogin(String email, String password) throws SQLException {
		// TODO query to check matching email and password invoked with login
		if (!isMemberExists(email))
			return null;
		Statement myStmt = myConn.createStatement();
		ResultSet stringQueryPassword = myStmt
				.executeQuery("select email,password from member where email=+'" + email + "'");
		while (stringQueryPassword.next()) {
			String resultingPassword = stringQueryPassword.getString("password");

			if (password.equals(resultingPassword)) {
				Member result = getMemberByEmail(email);
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * this method returns all members in db
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Member> getAllMembers() throws SQLException {
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
