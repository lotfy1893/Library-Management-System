import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	/**
	 * gets the user from the database by unique email maps it the object type
	 * Member
	 * 
	 * @param email
	 * @return Member with the email
	 * @throws SQLException
	 */
	public Member getMemberByEmail(String email) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member WHERE email='" + email + "'");

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

	/**
	 * gets member id by email
	 * 
	 * @param email
	 * @return id int
	 * @throws SQLException
	 */
	public int getIdMemberByEmail(String email) throws SQLException {
		Member m = getMemberByEmail(email);
		return m.getId();
	}

	/**
	 * this method takes member with all information and registers him/her in
	 * our db
	 * 
	 * @param member
	 * @return true if registered and false -> if the registration fails for any
	 *         reason
	 * @throws SQLException
	 */
	public boolean registerNewMember(Member member) throws SQLException {

		if (isMemberExists(member.getEmail())) {
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
			return false;
		}
	}

	/**
	 * given the email of the user we get all the books that he already has in
	 * the current time
	 * 
	 * @param email
	 * @return list of books
	 * @throws SQLException
	 * @throws ParseException
	 */
	public ArrayList<Book> getBorrowedBooks(String email) throws SQLException, ParseException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery(
				"select b.book_id,m.email,b.book_name,b.description,b.category,b.author,b.book_issue_date,b.entry_date,b.version,b.borrow_period,b.copies from book b,borrow r, member m where m.email='"
						+ email + "'and m.member_id= r.member_id and r.book_id=b.book_id and r.status='A'");

		ArrayList<Book> result = new ArrayList<>();

		while (stringQuery.next()) {
			String name = stringQuery.getString("book_name");
			String desc = stringQuery.getString("description");
			String category = stringQuery.getString("category");
			String author = stringQuery.getString("author");
			String bookIssue = stringQuery.getString("book_issue_date");
			String version = stringQuery.getString("version");
			String borrowPeriod = stringQuery.getString("borrow_period");
			String copies = stringQuery.getString("copies");

			Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(bookIssue).getTime());
			int borrow = Integer.parseInt(borrowPeriod);

			Book book = new Book(name, desc, category, author, issue, version, borrow, Integer.parseInt(copies));
			book.setId(Integer.parseInt(stringQuery.getString("book_id")));
			result.add(book);
		}

		return result;
	}

	/**
	 * takes email and check if this member already exists in our database
	 * 
	 * @param email
	 * @return true if the member exists and false otherwise
	 * @throws SQLException
	 */
	public boolean isMemberExists(String email) throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member where email = '" + email + "'");
		if (!stringQuery.next()) {
			return false;
		}
		return true;
	}

	/**
	 * this method takes email and password in the login process in gui and
	 * check if the password matches the email if the email exists in the first
	 * place ofcourse
	 * 
	 * @param email
	 * @param password
	 * @return Member with all his info if the login succeeds
	 * @throws SQLException
	 */
	public Member passwordMatchesForLogin(String email, String password) throws SQLException {
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
	 * @return list of all users
	 * @throws SQLException
	 */
	public ArrayList<Member> getAllMembers() throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet stringQuery = myStmt.executeQuery("SELECT * FROM member");
		ArrayList<Member> allMembers = new ArrayList<>();
		Member loggedInMember = null;
		while (stringQuery.next()) {
			String queryResultEmail = stringQuery.getString("email");
			if (queryResultEmail.equalsIgnoreCase("admin@admin.com")) {
				continue;
			}
			String queryResultID = stringQuery.getString("member_id");
			String queryResultFullName = stringQuery.getString("full_name");
			String queryResultType = stringQuery.getString("type");
			String queryResultPassword = stringQuery.getString("password");

			loggedInMember = new Member(queryResultEmail, queryResultPassword, queryResultFullName);
			loggedInMember.setId(Integer.parseInt(queryResultID));
			loggedInMember.setAdmin(queryResultType.equalsIgnoreCase("member") ? false : true);
			allMembers.add(loggedInMember);
		}
		
		return allMembers;
	}

	/**
	 * this method is used to remove a certain member from the db for the Admin
	 * and also we use it in the test class for cleanup
	 * 
	 * @param email
	 * @throws SQLException
	 *             return true/false
	 */
	public boolean removeUserFromDB(String email) {
		Statement myStmt;
		try {
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("DELETE FROM member WHERE email = '" + email + "'");
			return true;
		} catch (SQLException e) {
			return false;
		}

	}
}
