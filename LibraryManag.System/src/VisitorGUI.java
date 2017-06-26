import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * 
 */

/**
 * @author Peter Bessada
 *
 */
@SuppressWarnings("serial")
public class VisitorGUI extends JPanel {
	private JTextField textField_SearchBookName;
	private JTable table_LibraryBooks;
	private JScrollPane scrollPane_LibraryBooks;
	private JLabel label_LogOut;
	private Member loggedInMember;
	private BookRepository bookRepository;
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
	public Member getLoggedInMember() {
		return this.loggedInMember;
	}

	/**
	 * constructor of the visitor
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	public VisitorGUI() throws SQLException, ParseException {

		bookRepository = new BookRepository();
		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 380);
		add(tabbedPane);

		// -------------------------------------------- Library books Page
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

		// Listener to select between books
		table_LibraryBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
			}
		});
		scrollPane_LibraryBooks.setRowHeaderView(table_LibraryBooks);

		table_LibraryBooks.setModel(userTableModel);
		table_LibraryBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_LibraryBooks.getColumnModel().getColumn(1).setPreferredWidth(87);
		table_LibraryBooks.getColumnModel().getColumn(2).setPreferredWidth(65);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// -------------------------------------------------------------

		String x = "Visitor";
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
			/**
			 * this method is action listner for the search all
			 */
			public void actionPerformed(ActionEvent e) {

				String searchBookInput = textField_SearchBookName.getText();
				textField_SearchBookName.setText("");

				String searchAuthorInput = textField_SearchAuthorName.getText();
				textField_SearchAuthorName.setText("");

				String searchCategoryInput = textField_SearchCategory.getText();
				textField_SearchCategory.setText("");

				try {
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
			/**
			 * this method is used to return to the Login Page
			 */
			public void mouseClicked(MouseEvent e) {
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
