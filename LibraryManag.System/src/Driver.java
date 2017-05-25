import java.sql.*;
/**
 * 
 */

/**
 * @author apple2
 *
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
try{
	//get the connection to database
	Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_library?useSSL=false","root","123456789");

//Create a statement 
	Statement myStmt = myConn.createStatement();
	
	// execute Sql query
	ResultSet myRs = myStmt.executeQuery("select * from book");
	
	while (myRs.next()) {
		System.out.println(myRs.getString("book_id") + "," + myRs.getString("book_name"));
	}

}
catch (Exception exc) {
	exc.printStackTrace();
}
}
	}