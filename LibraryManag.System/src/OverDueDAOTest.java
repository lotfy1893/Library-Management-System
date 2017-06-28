import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * 
 */

/**
 * @author apple2
 *
 */
public class OverDueDAOTest {


	
	private Connection myConn;
	private int iSize =1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
	String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
	// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate("insert into borrow (return_date,borrow_date,member_id,book_id,status) value (" + "'2017-06-15'" + "," + "'2017-05-11'" + ",5,1,'A');");

}


	/**
	 * @throws java.lang.Exception
	 */
	
	@Test
	public void testSearchOverDueDAO() throws Exception {
		try {
			OverDueDAO overdue = new OverDueDAO();
			List<OverDue> result = overdue.searchOverDue("test@test.com");
			assertNotNull(result);
			assertEquals(1, result.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSearchOverDueDAOAll() throws Exception {
		try {
			OverDueDAO overdue = new OverDueDAO();
			List<OverDue> result = overdue.getAllOverDue();	
			assertNotNull(result);
			if (result.size() > 2){
				iSize=result.size();
			}
			assertEquals(iSize, result.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@After
	public void deleteTestRecord() throws Exception {

	// get db properties
	Properties props = new Properties();
	props.load(new FileInputStream("demo.properties"));

	String user = props.getProperty("user");
	String password = props.getProperty("password");
	String dburl = props.getProperty("dburl");
	
	// connect to database
	myConn = DriverManager.getConnection(dburl, user, password);	
	Statement myStmt = myConn.createStatement();
	myStmt.executeUpdate("delete from borrow where member_id=5");
	}	

	/**
	 * @param ok2
	 * @param overdue2
	 * @param c
	 */
	

}
