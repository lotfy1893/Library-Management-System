import static org.junit.Assert.*;

import java.sql.SQLException;

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

	@After
	public void cleanUpDB() {
		memberRepository.removeUserFromDB("test@test.com");
	}

}
