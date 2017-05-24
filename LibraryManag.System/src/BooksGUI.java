import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.BorderLayout;
import javax.swing.ListSelectionModel;
import java.awt.ScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JTable;
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

	/**
	 * Create the panel.
	 */
	// private JList<String> countryList = new JList();

	public BooksGUI() {

		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		JButton btnBorrowBook = new JButton("Borrow Book");
		btnBorrowBook.setBounds(812, 242, 117, 35);
		btnBorrowBook.setEnabled(false);
		add(btnBorrowBook);

		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.setBounds(812, 184, 117, 35);
		btnReturnBook.setEnabled(false);
		add(btnReturnBook);

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

		String[][] books = { { "Math_book", "peter", "A" }, { "Science_book", "lotfy", "N" },
				{ "statistics_book", "bassem", "A" } };
		DefaultTableModel userTableModel = new DefaultTableModel(books,
				new String[] { "book name", "author name", "Availability" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

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
		table_LibraryBooks.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// ---------------------------------------------------------------- My Books Page

		scrollPane_MyBooks = new JScrollPane();
		tabbedPane.addTab("My books", null, scrollPane_MyBooks, null);

		table_MyBooks = new JTable();

		table_MyBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_MyBooks.setFocusable(false);
		table_MyBooks.setRowSelectionAllowed(true);

		String[][] mybooks = { { "Math_book", "peter" }, { "Science_book", "lotfy" }, { "statistics_book", "bassem" } };
		DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooks,
				new String[] { "Book Name", "Author" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_MyBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// set return book true
				btnReturnBook.setEnabled(true);

			}
		});
		scrollPane_MyBooks.setRowHeaderView(table_MyBooks);

		table_MyBooks.setModel(userTableModel_Mybooks);

		table_MyBooks.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane_MyBooks.setViewportView(table_MyBooks);

		// -------------------------------------------------------------

		textField_Search = new JTextField();
		textField_Search.setBounds(64, 120, 513, 20);
		add(textField_Search);
		textField_Search.setColumns(10);

		JButton btn_Search = new JButton("Search");
		btn_Search.setBounds(614, 119, 89, 23);
		add(btn_Search);

		String x = "Peter Bessada"; // I will get the Full name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 54, 356, 44);
		add(lblNewLabel_UserName);

	}
}
