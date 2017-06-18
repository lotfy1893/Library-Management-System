import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 */

/**
 * @author Praktikant05
 * @author Peter
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BooksRepositoryTest {
	private BookRepository bookRepository;
	private MemberRepository memberRepository;
	private Member admin;
	private Member testMember;
	private Book b;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		memberRepository = new MemberRepository();
		bookRepository = new BookRepository();
		
		testMember = new Member("test@test.com", "1234", "Peter Bessada");
		Date issue = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2017-02-09").getTime());
		b = new Book("Test Book", "testy book", "Drama", "Peter Bessada", issue, 5, 2, 4);
		admin = memberRepository.getMemberByEmail("admin@admin.com");
	}

	/**
	 * tests the removing book property that is only meant for admins
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	@Test
	public void testRemoveBookFromDb() throws SQLException, ParseException {
		boolean inserted = bookRepository.registerNewBookInLibrary(b, admin);
		assertTrue(inserted);
		
		boolean removed = bookRepository.removeBookFromDB(b.getName(), b.getAuthor());
		assertTrue(removed);
	}
	
	@Test
	public void testThatNonAdminsCannotRegisterBookIntoDb() throws SQLException, ParseException {
		boolean inserted = bookRepository.registerNewBookInLibrary(b, testMember);
		assertFalse(inserted);
	}
	
	@Test
	public void testBorrowBookForAMember() throws SQLException, ParseException{
		boolean registerMember = memberRepository.registerNewMember(testMember);
		assertTrue(registerMember);
		
		testMember = memberRepository.getMemberByEmail(testMember.getEmail());
		
		boolean registerBook = bookRepository.registerNewBookInLibrary(b, admin);
		assertTrue(registerBook);
		
		b = bookRepository.getBookByAuthorAndName(b.getName(), b.getAuthor());
		
		int noCopies = b.getNumberOfCopies();
		boolean borrowed = bookRepository.borrowBook(testMember, b);
		assertTrue(borrowed);
		
		b = bookRepository.getBookByAuthorAndName(b.getName(), b.getAuthor());
		assertEquals(noCopies - 1, b.getNumberOfCopies());
	}

	@After
	public void cleanDB() throws SQLException{
		bookRepository.removeBorrowedBookTransaction(testMember.getId(), b.getId());
		bookRepository.removeBookFromDB(b.getName(), b.getAuthor());
		memberRepository.removeUserFromDB(testMember.getEmail());
	}
	
}
