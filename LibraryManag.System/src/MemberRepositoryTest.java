import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

/**
 * @author Peter
 * @author Praktikant05
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberRepositoryTest {

	private Member testMember = new Member("test@test.com", "1234", "Peter Bessada");
	private MemberRepository memberRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		memberRepository = new MemberRepository();
	}

	/**
	 * tests if the member is registered into the db or not
	 * 
	 * @throws SQLException
	 * 
	 */
	@Test
	public void testRegisterNewMemberIntoTheDB() throws SQLException {
		boolean registered = memberRepository.registerNewMember(testMember);
		assertTrue(registered);
	}

	/**
	 * checks that the method returns a full equibed member with all of his
	 * details
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetUniqueMemberByEmail() throws SQLException {
		boolean register = memberRepository.registerNewMember(testMember);
		Member m = null;
		if (register) {
			m = memberRepository.getMemberByEmail("test@test.com");
		}
		assertNotEquals(m.getId(), null);
		assertNotEquals(m, null);
		assertEquals(m.getEmail(), "test@test.com");
		assertEquals(m.getFullName(), "Peter Bessada");
	}

	/**
	 * tests if the method getsId of the member by email mapping is working
	 * properly
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetMemberIdbyEmail() throws SQLException {
		boolean register = memberRepository.registerNewMember(testMember);
		int m = 0;
		if (register) {
			m = memberRepository.getIdMemberByEmail("test@test.com");
		}
		assertNotEquals(0, m);
	}

	/**
	 * testing the GET/borrowedBooks for a given member is working
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	@Test
	public void testGetBorrowedBooks() throws SQLException, ParseException {
		// we will test the borrowed books for an already existing member with
		// mail "joe@gmail.com"
		// sofar this guy has two borrowed books
		ArrayList<Book> books = memberRepository.getBorrowedBooks("joe@gmail.com");
		assertEquals(2, books.size());
	}

	/**
	 * test if the wrong password is detected or not
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testFailedLoginEmailAndPassword() throws SQLException {
		String email = "joe@gmail.com";
		String password = "joejoe";

		Member logged = memberRepository.passwordMatchesForLogin(email, password);
		assertEquals(null, logged);
	}

	/**
	 * test successful login credentials
	 * 
	 * @throws SQLException
	 */

	@Test
	public void testSuccessfulLoginEmailAndPassword() throws SQLException {
		String email = "joe@gmail.com";
		String password = "1234";

		Member logged = memberRepository.passwordMatchesForLogin(email, password);
		assertNotEquals(null, logged);
	}

	/**
	 * cleaning the db from the instances created in the test
	 */
	@After
	public void cleanUpDB() {
		memberRepository.removeUserFromDB("test@test.com");
	}

}
