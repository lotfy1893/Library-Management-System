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
import javax.swing.UIManager;

/**
 * 
 */

/**
 * @author Peter
 *
 */
public class AdminGUI extends JPanel {
	private JTextField textField_Search;
	private JTable table_LibraryBooks;
	private JTable table_ViewUser;
	private JScrollPane scrollPane_LibraryBooks;
	private JScrollPane scrollPane_ViewUser;
	private JTable table_UserBooks;

	/**
	 * Create the panel.
	 */
	// private JList<String> countryList = new JList();

	public AdminGUI() {

		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		JButton btnRemoveBook = new JButton("Remove Book");
		btnRemoveBook.setBounds(812, 242, 117, 35);
		btnRemoveBook.setEnabled(false);
		add(btnRemoveBook);

		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBookGUI newBook = new AddBookGUI(); ////////////////////
				newBook.setVisible(true);
			}
		});
		btnAddBook.setBounds(812, 184, 117, 35);
		// btnAddBook.setEnabled(false);
		add(btnAddBook);

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddUser.setBounds(812, 296, 117, 35);
		add(btnAddUser);

		JButton btnRemoveUser = new JButton("Remove User");
		btnRemoveUser.setBounds(812, 350, 117, 35);
		btnRemoveUser.setEnabled(false);
		add(btnRemoveUser);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 279);
		add(tabbedPane);

		// -------------------------------------------------------------------------
		// Library books Page
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

		table_LibraryBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// checks if the books is available or not

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
		table_LibraryBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		table_LibraryBooks.getColumnModel().getColumn(4).setPreferredWidth(83);
		scrollPane_LibraryBooks.setViewportView(table_LibraryBooks);

		// ----------------------------------------------- View Users Page

		scrollPane_ViewUser = new JScrollPane();
		tabbedPane.addTab("View Users", null, scrollPane_ViewUser, null);

		table_ViewUser = new JTable();

		table_ViewUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_ViewUser.setFocusable(false);
		table_ViewUser.setRowSelectionAllowed(true);

		String[][] mybooks = { { "peter", "peter@jku.com" }, { "Lotfy", "Lotfy@jku.com" },
				{ "Bassem", "Bassem@jku.com" } };
		DefaultTableModel userTableModel_Mybooks = new DefaultTableModel(mybooks,
				new String[] { "User Full Name", "email Address" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_ViewUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// remove user is Enabled
				btnRemoveUser.setEnabled(true);

			}
		});
		scrollPane_ViewUser.setRowHeaderView(table_ViewUser);

		table_ViewUser.setModel(userTableModel_Mybooks);

		table_ViewUser.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane_ViewUser.setViewportView(table_ViewUser);

		// ---------------------------------View user books list

		table_UserBooks = new JTable();

		JScrollPane scrollPane_UserBooks = new JScrollPane();
		scrollPane_UserBooks.setBounds(64, 458, 738, 79);
		add(scrollPane_UserBooks);
		scrollPane_UserBooks.setViewportView(table_UserBooks);

		table_UserBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_UserBooks.setFocusable(false);
		table_UserBooks.setRowSelectionAllowed(true);

		String[][] userBooks = { { "Math_book", "peter", "A" }, { "Science_book", "lotfy", "N" },
				{ "statistics_book", "bassem", "A" } };

		DefaultTableModel userTableModel_UserBooks = new DefaultTableModel(userBooks,
				new String[] { "Book Name", "Author Name", "Category", "Borrow Date", "Return Date" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_UserBooks.setModel(userTableModel_UserBooks);
		table_UserBooks.getColumnModel().getColumn(0).setPreferredWidth(236);
		table_UserBooks.getColumnModel().getColumn(3).setPreferredWidth(83);
		table_UserBooks.getColumnModel().getColumn(4).setPreferredWidth(83);
		table_UserBooks.setBackground(UIManager.getColor("Button.background"));

		// --------------------------------------------------------------

		textField_Search = new JTextField();
		textField_Search.setBounds(64, 120, 513, 20);
		add(textField_Search);
		textField_Search.setColumns(10);

		JButton btn_Search = new JButton("Search");
		btn_Search.setBounds(614, 119, 89, 23);
		add(btn_Search);

		String x = "Admin Panel"; // I will get the Full name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 54, 356, 44);
		add(lblNewLabel_UserName);

	}
}
