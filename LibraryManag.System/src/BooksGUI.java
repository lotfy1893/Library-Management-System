import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * 
 */

/**
 * @author Peter
 *
 */
public class BooksGUI extends JPanel {
	private JTextField textField_Search;
	private JTable table_LibraryBooks;
	private JTable table_MyBooks;
	private JScrollPane scrollPane_LibraryBooks;
	private JScrollPane scrollPane_MyBooks;
	private JLabel label_LogOut;
	private Member loggedInMember;
	private MemberRepository memberRepository;
	private BookRepository bookRepository;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 */

	@SuppressWarnings("serial")
	public BooksGUI(Member loggedInMember) throws SQLException, ParseException {
		this.loggedInMember = loggedInMember;
		bookRepository = new BookRepository();
		memberRepository = new MemberRepository();
		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		// ---------- related to the book view page
		String[][] mybooks = { { "Math_book", "peter" }, { "Science_book", "lotfy" }, { "statistics_book", "bassem" } };
		
//		ArrayList<Book> myBooks = memberRepository.getBorrowedBooks(this.loggedInMember.getEmail());
//		String[][] mybooksTest = new String[myBooks.size()][4];
//		for (int i = 0; i < myBooks.size(); i++) {
//			int bookId = myBooks.get(i).getId();
//			int memberId = this.loggedInMember.getId();
//			Date returnDate = bookRepository.getBookReturnDate(memberId, bookId);
//			mybooksTest[i][0] = myBooks.get(i).getName();
//			mybooksTest[i][1] = myBooks.get(i).getAuthor();
//			mybooksTest[i][2] = myBooks.get(i).getCategory();
//			mybooksTest[i][3] = returnDate + "";
//		}
		DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooks,
				new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// ------------------------------------

		// Borrow Button
		JButton btnBorrowBook = new JButton("Borrow Book");
		btnBorrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO when the user press the Borrow Button
				// make the unavailable in library book table in case no other
				// copies in the Library
				// as well as to the GUI
				// add the book to the user's book list or table
				// as well as to the GUI

			}
		});
		btnBorrowBook.setBounds(812, 242, 117, 35);
		btnBorrowBook.setEnabled(false);
		add(btnBorrowBook);

		// Return Button
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO when the user press the Return Button
				// Remove the book from the User database table
				// Remove it from the GUI also
				// make it available in the Library books list if not
				// make the book available in the GUI also if applicable

				System.out.println(table_MyBooks.getSelectedRow());
				// Return the no. of selected row

				if (table_MyBooks.getSelectedRow() != -1) {
					// remove selected row from the model
					userTableModel_Mybooks.removeRow(table_MyBooks.getSelectedRow());
				}

				// System.out.println(userTableModel_Mybooks.getDataVector().elementAt(table_MyBooks.getSelectedRow()));
				// Return the data, in case we need it
			}
		});
		btnReturnBook.setBounds(812, 184, 117, 35);
		btnReturnBook.setEnabled(false);
		add(btnReturnBook);

		// Tabbed pane to switch between the library books view and my books
		// view
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 380);
		add(tabbedPane);

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0)
					btnReturnBook.setEnabled(false);
				if (tabbedPane.getSelectedIndex() == 1)
					btnBorrowBook.setEnabled(false);

			}
		});

		// -------------------------------------------- Library books Page
		scrollPane_LibraryBooks = new JScrollPane();
		tabbedPane.addTab("Library Books", null, scrollPane_LibraryBooks, null);

		table_LibraryBooks = new JTable();
		table_LibraryBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_LibraryBooks.setFocusable(false);
		table_LibraryBooks.setRowSelectionAllowed(true);

		// String[][] books = { { "Math_book", "peter", "A" }, { "Science_book",
		// "lotfy", "N" },
		// { "statistics_book", "bassem", "A" } };

		ArrayList<Book> allBooks = bookRepository.getAllBooksInLibrary();
		String[][] allBooksArrayForTable = new String[allBooks.size()][6];
		for (int i = 0; i < allBooks.size(); i++) {
			String avaliablity = allBooks.get(i).getNumberOfCopies() > 0 ? "A" : "N";
			allBooksArrayForTable[i][0] = allBooks.get(i).getName();
			allBooksArrayForTable[i][1] = allBooks.get(i).getAuthor();
			allBooksArrayForTable[i][2] = avaliablity;
			allBooksArrayForTable[i][3] = allBooks.get(i).getCategory();
			allBooksArrayForTable[i][4] = allBooks.get(i).getBookIssueDate() + "";
			allBooksArrayForTable[i][5] = allBooks.get(i).getNumberOfCopies() + "";
		}

		DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable, new String[] { "Book name",
				"Author name", "Availability", "Category", "Issue Date", "No. of Copies" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Listener to select between books
		table_LibraryBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// checks if the books is available or not

				if (table_LibraryBooks.getValueAt(table_LibraryBooks.getSelectedRow(), 2).equals("A")) { // 2
																											// means
																											// third
																											// column
					btnBorrowBook.setEnabled(true);
				} else {
					btnBorrowBook.setEnabled(false);
				}
				// System.out.println(table.getValueAt(table.getSelectedRow(),
				// 2));
			}
		});
		scrollPane_LibraryBooks.setRowHeaderView(table_LibraryBooks);

		table_LibraryBooks.setModel(userTableModel);
		table_LibraryBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_LibraryBooks.getColumnModel().getColumn(1).setPreferredWidth(87);
		table_LibraryBooks.getColumnModel().getColumn(2).setPreferredWidth(65);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// ---------------------------------------------------------------- My
		// Books Page

		scrollPane_MyBooks = new JScrollPane();
		tabbedPane.addTab("My books", null, scrollPane_MyBooks, null);

		table_MyBooks = new JTable();

		table_MyBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_MyBooks.setFocusable(false);
		table_MyBooks.setRowSelectionAllowed(true);

		// listener to select between my books
		table_MyBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// set return book true
				btnReturnBook.setEnabled(true);

				// System.out.println(table_MyBooks.getSelectedRow());
			}
		});
		scrollPane_MyBooks.setRowHeaderView(table_MyBooks);

		table_MyBooks.setModel(userTableModel_Mybooks);
		table_MyBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_MyBooks.getColumnModel().getColumn(1).setPreferredWidth(83);
		table_MyBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		scrollPane_MyBooks.setViewportView(table_MyBooks);

		// -------------------------------------------------------------

		textField_Search = new JTextField();
		textField_Search.setBounds(64, 120, 513, 20);
		add(textField_Search);
		textField_Search.setColumns(10);

		JButton btn_Search = new JButton("Search");
		btn_Search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String searchInput = textField_Search.getText();
				textField_Search.setText("");

				// TODO when the user press the search Button
				// Return all the Library books here
				// view the data in the Library Books view

			}
		});
		btn_Search.setBounds(614, 119, 89, 23);
		add(btn_Search);

		String x = this.loggedInMember.getFullName(); // I will get the Full
														// name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 54, 356, 44);

		add(lblNewLabel_UserName);

		label_LogOut = new JLabel("Log Out");
		label_LogOut.setForeground(new Color(0, 255, 0));
		label_LogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_LogOut.setBounds(853, 68, 63, 24);
		add(label_LogOut);

		label_LogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// return to the Login Page
				System.gc();
				java.awt.Window win[] = java.awt.Window.getWindows();
				for (int i = 0; i < win.length; i++) {
					win[i].dispose();
					win[i] = null;
				}
				LibraryGUI.main(null);

			}
		});

	}
}
