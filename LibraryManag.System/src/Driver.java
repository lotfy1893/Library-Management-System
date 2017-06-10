import java.sql.*;
import java.util.Calendar;
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
	ResultSet myRs = myStmt.executeQuery("select * from member where email = '"+"admin@admin.com'");

	
	while (myRs.next()) {
		System.out.println(myRs.getString("email") + "," + myRs.getString("password"));
	}

	

	Calendar calendar = Calendar.getInstance();
	Date d = new Date(calendar.getTime().getTime());
	
	System.out.println(d);
	d.setDate(d.getDate() + 1);
	System.out.println(d);

}
catch (Exception exc) {
	exc.printStackTrace();
}
}
	}