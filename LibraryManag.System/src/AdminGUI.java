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
import javax.swing.UIManager;
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

public class AdminGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_SearchByCategory;
	private JTable table_LibraryBooks;
	private JTable table_ViewUser;
	private JScrollPane scrollPane_LibraryBooks;
	private JScrollPane scrollPane_ViewUser;
	private JTable table_UserBooks;
	private BookRepository bookRepository;
	private MemberRepository memberRepository;
	private String clickedEmail = "";
	private JTextField textField_SearchByAuthor;
	private JTextField textField_SearchByBook;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void setClickedEmail(String email) {
		this.clickedEmail = email;
	}

	public AdminGUI() throws SQLException, ParseException {
		bookRepository = new BookRepository();
		memberRepository = new MemberRepository();
		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		// ----------------------- related to the LibraryBook tab

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

		@SuppressWarnings("serial")
		DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable, new String[] { "Book name",
				"Author name", "Availability", "Category", "Issue Date", "No. of Copies" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// -----------------------

		// ----------------------- related to the viewUser tab

		ArrayList<Member> allUsers = memberRepository.getAllMembers();
		String[][] allUsersForTable = new String[allUsers.size()][2];

		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).getFullName() == null || allUsers.get(i).getFullName().equalsIgnoreCase("null")) {
				allUsersForTable[i][0] = "Unknown Name";
			} else {
				allUsersForTable[i][0] = allUsers.get(i).getFullName();
			}

			allUsersForTable[i][1] = allUsers.get(i).getEmail();
		}

		@SuppressWarnings("serial")
		DefaultTableModel userTableModel_MyUsers = new DefaultTableModel(allUsersForTable,
				new String[] { "User Full Name", "email Address" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// -----------------------

		// Remove Book Button
		JButton btnRemoveBook = new JButton("Remove Book");
		// Remove Book Listener
		btnRemoveBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				if ((row = table_LibraryBooks.getSelectedRow()) != -1) {
					try {
						final String title = (String) table_LibraryBooks.getValueAt(row, 0);
						final String author = (String) table_LibraryBooks.getValueAt(row, 1);
						bookRepository.removeBookFromDB(title, author);
						// userTableModel.removeRow(table_LibraryBooks.getSelectedRow());

						ArrayList<Book> allBooks = null;
						try {
							allBooks = bookRepository.getAllBooksInLibrary();
						} catch (SQLException e1) {
							e1.printStackTrace();
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

						@SuppressWarnings("serial")
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
		btnRemoveBook.setBounds(812, 242, 117, 35);
		btnRemoveBook.setEnabled(false);
		add(btnRemoveBook);

		// Add Book Button
		JButton btnAddBook = new JButton("Add Book");

		// Add Book Listener
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBookGUI newBook = new AddBookGUI();
				newBook.setVisible(true);
			}
		});
		btnAddBook.setBounds(812, 184, 117, 35);
		add(btnAddBook);

		// Remove User Button
		JButton btnRemoveUser = new JButton("Remove User");

		// Remove User Listener
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				if ((row = table_ViewUser.getSelectedRow()) != -1) {

					try {
						memberRepository.removeUserFromDB(clickedEmail);
						userTableModel_MyUsers.removeRow(table_ViewUser.getSelectedRow());
						// System.out.println(row);
						final String value = (String) table_ViewUser.getValueAt(row, 1);
						clickedEmail = value;
					} catch (Exception ex) {
						System.out.println("Cannot perform this remove!");
					}
				}

			}
		});
		btnRemoveUser.setBounds(812, 299, 117, 35);
		btnRemoveUser.setEnabled(false);
		add(btnRemoveUser);

		// Tab to switch between Library books view and Users View
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 279);
		add(tabbedPane);

		// ---------------------------------------------- Library books Page
		scrollPane_LibraryBooks = new JScrollPane();
		tabbedPane.addTab("Library Books", null, scrollPane_LibraryBooks, null);

		table_LibraryBooks = new JTable();
		table_LibraryBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_LibraryBooks.setFocusable(false);
		table_LibraryBooks.setRowSelectionAllowed(true);

		// row selection Listener
		table_LibraryBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				if (true) {
					btnRemoveBook.setEnabled(true);
				}

				// System.out.println(table.getValueAt(table.getSelectedRow(),
				// 2));
			}
		});
		scrollPane_LibraryBooks.setRowHeaderView(table_LibraryBooks);

		table_LibraryBooks.setModel(userTableModel);
		table_LibraryBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_LibraryBooks.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_LibraryBooks.getColumnModel().getColumn(2).setPreferredWidth(50);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// ----------------------------------------------- View Users Page

		scrollPane_ViewUser = new JScrollPane();
		tabbedPane.addTab("View Users", null, scrollPane_ViewUser, null);

		table_ViewUser = new JTable();

		table_ViewUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_ViewUser.setFocusable(false);
		table_ViewUser.setRowSelectionAllowed(true);

		table_ViewUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnRemoveUser.setEnabled(true);
				if (table_ViewUser.getSelectedRow() != -1) {
					if (!event.getValueIsAdjusting()) {
						int row = table_ViewUser.getSelectedRow();
						final String value = (String) table_ViewUser.getValueAt(row, 1);
						setClickedEmail(value); // -----------------

						ArrayList<Book> myBooks = new ArrayList<>();
						try {
							myBooks = memberRepository.getBorrowedBooks(clickedEmail);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Member clicked = null;
						try {
							clicked = memberRepository.getMemberByEmail(clickedEmail);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String[][] mybooksTest = new String[myBooks.size()][4];
						for (int i = 0; i < myBooks.size(); i++) {
							int bookId = myBooks.get(i).getId();
							int memberId = clicked.getId();
							Date returnDate = null;
							try {
								returnDate = bookRepository.getBookReturnDate(memberId, bookId);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mybooksTest[i][0] = myBooks.get(i).getName();
							mybooksTest[i][1] = myBooks.get(i).getAuthor();
							mybooksTest[i][2] = myBooks.get(i).getCategory();
							mybooksTest[i][3] = returnDate + "";
						}
						@SuppressWarnings("serial")
						DefaultTableModel userTableModel_UserBooks = new DefaultTableModel(mybooksTest,
								new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
							@Override
							public boolean isCellEditable(int row, int column) {
								return false;
							}
						};

						table_UserBooks.setModel(userTableModel_UserBooks);
						// -----------------
					}
				}
			}
		});
		scrollPane_ViewUser.setRowHeaderView(table_ViewUser);

		table_ViewUser.setModel(userTableModel_MyUsers);

		table_ViewUser.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane_ViewUser.setViewportView(table_ViewUser);

		///////////////////////////////////////////////////////////////////////

		// User's Book's Label
		JLabel lblUsersBookList = new JLabel("User's Book List");
		lblUsersBookList.setForeground(Color.GREEN);
		lblUsersBookList.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsersBookList.setBounds(64, 463, 168, 14);
		lblUsersBookList.setVisible(false);
		add(lblUsersBookList);

		// ---------------------------------View user books list

		table_UserBooks = new JTable();

		JScrollPane scrollPane_UserBooks = new JScrollPane();
		scrollPane_UserBooks.setBounds(64, 478, 738, 75);
		add(scrollPane_UserBooks);
		scrollPane_UserBooks.setVisible(false);

		scrollPane_UserBooks.setViewportView(table_UserBooks);

		table_UserBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_UserBooks.setFocusable(false);
		table_UserBooks.setRowSelectionAllowed(true);

		String[][] mybooksTest = new String[4][4];

		@SuppressWarnings("serial")
		DefaultTableModel userTableModel_UserBooks = new DefaultTableModel(mybooksTest,
				new String[] { "Book Name", "Author Name", "Category", "Return Date" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_UserBooks.setModel(userTableModel_UserBooks);
		table_UserBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_UserBooks.getColumnModel().getColumn(2).setPreferredWidth(83);
		table_UserBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		table_UserBooks.setBackground(UIManager.getColor("Button.background"));

		// --------------------------------------------------------------

		// tab Listener
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					btnRemoveUser.setEnabled(false);
					lblUsersBookList.setVisible(false);
					scrollPane_UserBooks.setVisible(false);
					table_ViewUser.clearSelection();

					ArrayList<Book> allBooks = null;
					try {
						allBooks = bookRepository.getAllBooksInLibrary();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
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

					@SuppressWarnings("serial")
					DefaultTableModel userTableModel = new DefaultTableModel(allBooksArrayForTable, new String[] {
							"Book name", "Author name", "Availability", "Category", "Issue Date", "No. of Copies" }) {
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					table_LibraryBooks.setModel(userTableModel);
				}
				if (tabbedPane.getSelectedIndex() == 1) {
					btnRemoveBook.setEnabled(false);
					lblUsersBookList.setVisible(true);
					scrollPane_UserBooks.setVisible(true);
					table_LibraryBooks.clearSelection();
				}
			}
		});

		textField_SearchByAuthor = new JTextField();
		textField_SearchByAuthor.setToolTipText("");
		textField_SearchByAuthor.setColumns(10);
		textField_SearchByAuthor.setBounds(205, 94, 446, 20);
		add(textField_SearchByAuthor);

		textField_SearchByBook = new JTextField();
		textField_SearchByBook.setToolTipText("");
		textField_SearchByBook.setColumns(10);
		textField_SearchByBook.setBounds(205, 63, 446, 20);
		add(textField_SearchByBook);

		textField_SearchByCategory = new JTextField();
		textField_SearchByCategory.setToolTipText("");
		textField_SearchByCategory.setBounds(205, 125, 446, 20);
		add(textField_SearchByCategory);
		textField_SearchByCategory.setColumns(10);

		JButton btn_SearchAll = new JButton("Search All");
		btn_SearchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bookName = textField_SearchByBook.getText();
				textField_SearchByBook.setText("");

				String AuthorName = textField_SearchByAuthor.getText();
				textField_SearchByAuthor.setText("");

				String category = textField_SearchByCategory.getText();
				textField_SearchByCategory.setText("");
				try {
					ArrayList<Book> searchResults = bookRepository.searchForABook(bookName, AuthorName, category);
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

					@SuppressWarnings("serial")
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
		btn_SearchAll.setBounds(677, 124, 125, 23);
		add(btn_SearchAll);

		String x = "Admin Panel"; // I will get the Full name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 11, 356, 44);
		add(lblNewLabel_UserName);

		JLabel lblLogOut = new JLabel("Log Out");
		lblLogOut.setForeground(new Color(0, 255, 51));
		lblLogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogOut.setBounds(851, 25, 63, 24);
		add(lblLogOut);

		JLabel lbl_SearchbyCategory = new JLabel("Search by Category");
		lbl_SearchbyCategory.setForeground(new Color(0, 255, 0));
		lbl_SearchbyCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_SearchbyCategory.setBounds(64, 128, 139, 14);
		add(lbl_SearchbyCategory);

		JLabel lblSearchByAuther = new JLabel("Search by Author ");
		lblSearchByAuther.setForeground(Color.GREEN);
		lblSearchByAuther.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchByAuther.setBounds(64, 97, 139, 14);
		add(lblSearchByAuther);

		JLabel lblSearchByBook = new JLabel("Search by Book");
		lblSearchByBook.setForeground(Color.GREEN);
		lblSearchByBook.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchByBook.setBounds(64, 66, 139, 14);
		add(lblSearchByBook);

		lblLogOut.addMouseListener(new MouseAdapter() {
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
