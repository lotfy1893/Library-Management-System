import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
		b = new Book("Test Book", "testy book", "Drama", "Peter Bessada", issue, "V.1", 2, 4);
	}

	/**
	 * tests the removing book property that is only meant for admins
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 */
	@Test
	public void testRemoveBookFromDb() throws SQLException, ParseException {
		boolean inserted = bookRepository.registerNewBookInLibrary(b);
		assertTrue(inserted);

		boolean removed = bookRepository.removeBookFromDB(b.getName(), b.getAuthor());
		assertTrue(removed);
	}

	/**
	 * tests if the borrow process for a given book and member is working fine
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	@Test
	public void testBorrowBookForAMember() throws SQLException, ParseException {
		boolean registerMember = memberRepository.registerNewMember(testMember);
		assertTrue(registerMember);

		testMember = memberRepository.getMemberByEmail(testMember.getEmail());

		boolean registerBook = bookRepository.registerNewBookInLibrary(b);
		assertTrue(registerBook);

		b = bookRepository.getBookByAuthorAndName(b.getName(), b.getAuthor());

		int noCopies = b.getNumberOfCopies();
		boolean borrowed = bookRepository.borrowBook(testMember, b);
		assertTrue(borrowed);

		b = bookRepository.getBookByAuthorAndName(b.getName(), b.getAuthor());
		assertEquals(noCopies - 1, b.getNumberOfCopies());
	}

	/**
	 * test search for drama books
	 */
	@Test
	public void testSearchForDramaBooks() {
		try {
			ArrayList<Book> result = bookRepository.searchForABook("", "", "drama");
			assertNotNull(result);
			assertEquals(3, result.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * test search for a book by name of the author
	 */
	@Test
	public void testSearchForBooksByAuthor() {
		try {
			ArrayList<Book> result = bookRepository.searchForABook("", "margaret", "");
			assertNotNull(result);
			assertEquals(4, result.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * test search for a book by initials of the book name
	 */
	@Test
	public void testSearchForBooksByFirstLetterOfTitle() {
		try {
			ArrayList<Book> result = bookRepository.searchForABook("b", "", "");
			assertNotNull(result);
			assertEquals(8, result.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void checkThatBorrowFailsIFTheUserHasAlreadyAcopy() throws SQLException, ParseException {

		testMember = memberRepository.getMemberByEmail(testMember.getEmail());

		boolean borrow = bookRepository.borrowBook(testMember, b);
		assertTrue(borrow);

		boolean borrowAgain = bookRepository.borrowBook(testMember, b);
		assertFalse(borrowAgain);

	}

	/**
	 * cleans the db from the created instances in the test not to overwhelm the
	 * db
	 * 
	 * @throws SQLException
	 */

	@After
	public void cleanDB() throws SQLException {
		bookRepository.removeBorrowedBookTransaction(testMember.getId(), b.getId());
		bookRepository.removeBookFromDB(b.getName(), b.getAuthor());
		memberRepository.removeUserFromDB(testMember.getEmail());
	}

}
