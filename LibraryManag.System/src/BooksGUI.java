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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

	public BooksGUI() {

		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		// ---------- related to the book view page
		String[][] mybooks = { { "Math_book", "peter" }, { "Science_book", "lotfy" }, { "statistics_book", "bassem" } };
		DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooks,
				new String[] { "Book Name", "Author Name", "Category", "Borrow Date", "Return Date" }) {
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

		String[][] books = { { "Math_book", "peter", "A" }, { "Science_book", "lotfy", "N" },
				{ "statistics_book", "bassem", "A" } };

		DefaultTableModel userTableModel = new DefaultTableModel(books, new String[] { "Book name", "Author name",
				"Availability", "Category", "Issue Date", "No. of Copies" }) {
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
		table_MyBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		table_MyBooks.getColumnModel().getColumn(4).setPreferredWidth(83);
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

		String x = "Peter Bessada"; // I will get the Full name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 54, 356, 44);
		add(lblNewLabel_UserName);

	}
}
