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
 * @author Peter Bessada
 *
 */
@SuppressWarnings("serial")
public class BooksGUI extends JPanel {
	private JTextField textField_SearchBookName;
	private JTable table_LibraryBooks;
	private JTable table_MyBooks;
	private JScrollPane scrollPane_LibraryBooks;
	private JScrollPane scrollPane_MyBooks;
	private JLabel label_LogOut;
	private Member loggedInMember;
	private MemberRepository memberRepository;
	private BookRepository bookRepository;
	private JLabel lbl_NoMorethan3Books;
	private JTextField textField_SearchAuthorName;
	private JTextField textField_SearchCategory;
	private JLabel label_SearchByCategory;
	private JLabel lblSearchByAuthor;
	private JLabel lblSearchByBook;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 */

	/**
	 * 
	 * @return the logged in member
	 */
	public Member getLoggedInMember() {
		return this.loggedInMember;
	}

	// this is the constructor to build in the Book GUI page
	public BooksGUI(Member loggedInMember) throws SQLException, ParseException {

		lbl_NoMorethan3Books = new JLabel("Can not borrow more than 3 books or you already have the book!");
		lbl_NoMorethan3Books.setForeground(new Color(255, 0, 0));
		lbl_NoMorethan3Books.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NoMorethan3Books.setBounds(445, 159, 484, 14);
		lbl_NoMorethan3Books.setVisible(false);
		add(lbl_NoMorethan3Books);

		this.loggedInMember = loggedInMember;
		bookRepository = new BookRepository();
		memberRepository = new MemberRepository();
		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		// ---------- related to the Library books view page... This page view
		// the books in the library

		ArrayList<Book> myBooks = memberRepository.getBorrowedBooks(this.loggedInMember.getEmail());
		String[][] mybooksTest = new String[myBooks.size()][4];
		for (int i = 0; i < myBooks.size(); i++) {
			int bookId = myBooks.get(i).getId();
			int memberId = this.loggedInMember.getId();
			Date returnDate = bookRepository.getBookReturnDate(memberId, bookId);
			mybooksTest[i][0] = myBooks.get(i).getName();
			mybooksTest[i][1] = myBooks.get(i).getAuthor();
			mybooksTest[i][2] = myBooks.get(i).getCategory();
			mybooksTest[i][3] = returnDate + "";
		}
		DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooksTest,
				new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// ------------------------------------

		// Borrow Button to borrow books from library
		JButton btnBorrowBook = new JButton("Borrow Book");
		btnBorrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				if ((row = table_LibraryBooks.getSelectedRow()) != -1) {
					try {
						final String title = (String) table_LibraryBooks.getValueAt(row, 0);
						final String author = (String) table_LibraryBooks.getValueAt(row, 1);
						Book b = bookRepository.getBookByAuthorAndName(title, author);
						boolean borrowedSuccessfully = bookRepository.borrowBook(loggedInMember, b);
						final Member loggedIn = getLoggedInMember();
						if (borrowedSuccessfully) { // if the user borrowed the
													// book successfully
							// the next lines is used to repaint the My books
							// tab view
							ArrayList<Book> myBooks = memberRepository.getBorrowedBooks(loggedIn.getEmail());
							String[][] mybooksTest = new String[myBooks.size()][4];
							for (int i = 0; i < myBooks.size(); i++) {
								int bookId = myBooks.get(i).getId();
								int memberId = loggedIn.getId();
								Date returnDate = bookRepository.getBookReturnDate(memberId, bookId);
								mybooksTest[i][0] = myBooks.get(i).getName();
								mybooksTest[i][1] = myBooks.get(i).getAuthor();
								mybooksTest[i][2] = myBooks.get(i).getCategory();
								mybooksTest[i][3] = returnDate + "";
							}
							DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooksTest,
									new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
								@Override
								public boolean isCellEditable(int row, int column) {
									return false;
								}
							};
							table_MyBooks.setModel(userTableModel_Mybooks);
						} else {
							lbl_NoMorethan3Books.setVisible(true);
						}
						// the next lines is used to repaint the Library books
						// view
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

						DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable,
								new String[] { "Book name", "Author name", "Availability", "Category", "Issue Date",
										"No. of Copies" }) {
							@Override
							public boolean isCellEditable(int row, int column) {
								return false;
							}
						};
						table_LibraryBooks.setModel(userTableModel);
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Cannot perform this remove!");
					}
				}
			}
		});
		btnBorrowBook.setBounds(812, 242, 117, 35);
		btnBorrowBook.setEnabled(false);
		add(btnBorrowBook);

		// Return Book Button to return my books back to the library
		JButton btnReturnBook = new JButton("Return Book");

		// Return Book Listener
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				final Member loggedMember = getLoggedInMember();
				boolean happned = false;
				if ((row = table_MyBooks.getSelectedRow()) != -1) {

					final String title = (String) table_MyBooks.getValueAt(row, 0);
					final String author = (String) table_MyBooks.getValueAt(row, 1);
					Book b = null;
					try {
						b = bookRepository.getBookByAuthorAndName(title, author);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					try {
						happned = bookRepository.returnBook(loggedMember, b);
						if (happned) {
							// if the book returned then repaint my book view
							// tab
							ArrayList<Book> myBooks = null;
							try {
								myBooks = memberRepository.getBorrowedBooks(loggedMember.getEmail());
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							String[][] mybooksTest = new String[myBooks.size()][4];
							for (int i = 0; i < myBooks.size(); i++) {
								int bookId = myBooks.get(i).getId();
								int memberId = loggedMember.getId();
								Date returnDate = null;
								try {
									returnDate = bookRepository.getBookReturnDate(memberId, bookId);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								mybooksTest[i][0] = myBooks.get(i).getName();
								mybooksTest[i][1] = myBooks.get(i).getAuthor();
								mybooksTest[i][2] = myBooks.get(i).getCategory();
								mybooksTest[i][3] = returnDate + "";
							}
							DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooksTest,
									new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
								@Override
								public boolean isCellEditable(int row, int column) {
									return false;
								}
							};
							table_MyBooks.setModel(userTableModel_Mybooks);
							// also repaint the library view by increasing the
							// number of copies for the returned book
							ArrayList<Book> allBooks = null;
							try {
								allBooks = bookRepository.getAllBooksInLibrary();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
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

							DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable,
									new String[] { "Book name", "Author name", "Availability", "Category", "Issue Date",
											"No. of Copies" }) {
								@Override
								public boolean isCellEditable(int row, int column) {
									return false;
								}
							};
							table_LibraryBooks.setModel(userTableModel);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btnReturnBook.setBounds(812, 184, 117, 35);
		btnReturnBook.setEnabled(false);
		add(btnReturnBook);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 380);
		add(tabbedPane);

		// -------------------------------------------- Library books tab Page
		scrollPane_LibraryBooks = new JScrollPane();
		tabbedPane.addTab("Library Books", null, scrollPane_LibraryBooks, null);

		table_LibraryBooks = new JTable();
		table_LibraryBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_LibraryBooks.setFocusable(false);
		table_LibraryBooks.setRowSelectionAllowed(true);

		ArrayList<Book> searchResults = bookRepository.getAllBooksInLibrary();
		String[][] allBooksArrayForTable = new String[searchResults.size()][6];
		for (int i = 0; i < searchResults.size(); i++) {
			String avaliablity = searchResults.get(i).getNumberOfCopies() > 0 ? "A" : "N";
			allBooksArrayForTable[i][0] = searchResults.get(i).getName();
			allBooksArrayForTable[i][1] = searchResults.get(i).getAuthor();
			allBooksArrayForTable[i][2] = avaliablity;
			allBooksArrayForTable[i][3] = searchResults.get(i).getCategory();
			allBooksArrayForTable[i][4] = searchResults.get(i).getBookIssueDate() + "";
			allBooksArrayForTable[i][5] = searchResults.get(i).getNumberOfCopies() + "";
		}

		DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable, new String[] { "Book name",
				"Author name", "Availability", "Category", "Issue Date", "No. of Copies" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Listener to select between books in the library page
		table_LibraryBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// checks if the books is available or not
				if (table_LibraryBooks.getSelectedRow() != -1) {
					if (table_LibraryBooks.getValueAt(table_LibraryBooks.getSelectedRow(), 2).equals("A")) {
						btnBorrowBook.setEnabled(true);
					} else {
						btnBorrowBook.setEnabled(false);
					}
				}
			}
		});
		scrollPane_LibraryBooks.setRowHeaderView(table_LibraryBooks);
		table_LibraryBooks.setModel(userTableModel);
		table_LibraryBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_LibraryBooks.getColumnModel().getColumn(1).setPreferredWidth(87);
		table_LibraryBooks.getColumnModel().getColumn(2).setPreferredWidth(65);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// ----------------------------------------- My Books Page
		scrollPane_MyBooks = new JScrollPane();
		tabbedPane.addTab("My books", null, scrollPane_MyBooks, null);

		table_MyBooks = new JTable();

		table_MyBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_MyBooks.setFocusable(false);
		table_MyBooks.setRowSelectionAllowed(true);

		// listener to select between my books
		table_MyBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnReturnBook.setEnabled(true);
			}
		});
		scrollPane_MyBooks.setRowHeaderView(table_MyBooks);
		table_MyBooks.setModel(userTableModel_Mybooks);
		table_MyBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_MyBooks.getColumnModel().getColumn(1).setPreferredWidth(83);
		table_MyBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		scrollPane_MyBooks.setViewportView(table_MyBooks);

		// -------------------------------------------------------------

		// tab Listner to listen when changing between tabs (Library books or My
		// books)
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0)
					btnReturnBook.setEnabled(false);
				table_MyBooks.clearSelection();
				if (tabbedPane.getSelectedIndex() == 1)
					btnBorrowBook.setEnabled(false);
				lbl_NoMorethan3Books.setVisible(false);
				table_LibraryBooks.clearSelection();

			}
		});

		String x = this.loggedInMember.getFullName(); // I will get the Full
														// name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 11, 356, 44);

		add(lblNewLabel_UserName);

		label_LogOut = new JLabel("Log Out");
		label_LogOut.setForeground(new Color(0, 255, 0));
		label_LogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_LogOut.setBounds(866, 25, 63, 24);
		add(label_LogOut);

		textField_SearchBookName = new JTextField();
		textField_SearchBookName.setBounds(213, 57, 455, 20);
		add(textField_SearchBookName);
		textField_SearchBookName.setColumns(10);

		textField_SearchAuthorName = new JTextField();
		textField_SearchAuthorName.setColumns(10);
		textField_SearchAuthorName.setBounds(213, 89, 455, 20);
		add(textField_SearchAuthorName);

		textField_SearchCategory = new JTextField();
		textField_SearchCategory.setColumns(10);
		textField_SearchCategory.setBounds(213, 121, 455, 20);
		add(textField_SearchCategory);

		JButton btnSearchAll = new JButton("Search All");
		btnSearchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// search the library books based on the fields decided by the
				// user
				String searchBookInput = textField_SearchBookName.getText();
				textField_SearchBookName.setText("");

				String searchAuthorInput = textField_SearchAuthorName.getText();
				textField_SearchAuthorName.setText("");

				String searchCategoryInput = textField_SearchCategory.getText();
				textField_SearchCategory.setText("");

				try {
					// repaint the library books view
					ArrayList<Book> searchResults = bookRepository.searchForABook(searchBookInput, searchAuthorInput,
							searchCategoryInput);
					String[][] allBooksArrayForTableA = new String[searchResults.size()][6];
					for (int i = 0; i < searchResults.size(); i++) {
						String avaliablity = searchResults.get(i).getNumberOfCopies() > 0 ? "A" : "N";
						allBooksArrayForTableA[i][0] = searchResults.get(i).getName();
						allBooksArrayForTableA[i][1] = searchResults.get(i).getAuthor();
						allBooksArrayForTableA[i][2] = avaliablity;
						allBooksArrayForTableA[i][3] = searchResults.get(i).getCategory();
						allBooksArrayForTableA[i][4] = searchResults.get(i).getBookIssueDate() + "";
						allBooksArrayForTableA[i][5] = searchResults.get(i).getNumberOfCopies() + "";
					}
					DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTableA, new String[] {
							"Book name", "Author name", "Availability", "Category", "Issue Date", "No. of Copies" }) {
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					table_LibraryBooks.setModel(userTableModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnSearchAll.setBounds(678, 118, 123, 24);
		add(btnSearchAll);

		label_SearchByCategory = new JLabel("Search by Category");
		label_SearchByCategory.setForeground(Color.GREEN);
		label_SearchByCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_SearchByCategory.setBounds(64, 124, 139, 14);
		add(label_SearchByCategory);

		lblSearchByAuthor = new JLabel("Search by Author");
		lblSearchByAuthor.setForeground(Color.GREEN);
		lblSearchByAuthor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchByAuthor.setBounds(64, 89, 139, 14);
		add(lblSearchByAuthor);

		lblSearchByBook = new JLabel("Search by Book");
		lblSearchByBook.setForeground(Color.GREEN);
		lblSearchByBook.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchByBook.setBounds(64, 57, 139, 14);
		add(lblSearchByBook);

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
