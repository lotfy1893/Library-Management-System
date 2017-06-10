import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import java.awt.Window;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
	private JTextField txtFindABook;
	private JTable table_LibraryBooks;
	private JTable table_ViewUser;
	private JScrollPane scrollPane_LibraryBooks;
	private JScrollPane scrollPane_ViewUser;
	private JTable table_UserBooks;

	/**
	 * Create the panel.
	 */

	public AdminGUI() {

		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		// ----------------------- related to the LibraryBook tab

		String[][] books = { { "Math_book", "peter", "A" }, { "Science_book", "lotfy", "N" },
				{ "statistics_book", "bassem", "A" } };

		DefaultTableModel userTableModel = new DefaultTableModel(books, new String[] { "Book name", "Author name",
				"Availability", "Category", "Issue Date", "No. of Copies" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// -----------------------

		// ----------------------- related to the viewUser tab

		String[][] myUsers = { { "peter", "peter@jku.com" }, { "Lotfy", "Lotfy@jku.com" },
				{ "Bassem", "Bassem@jku.com" } };
		DefaultTableModel userTableModel_MyUsers = new DefaultTableModel(myUsers,
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

				// TODO when the user press the Remove Book Button
				// Remove the book from the Book database table

				System.out.println(table_LibraryBooks.getSelectedRow());
				// Return the no. of selected row

				if (table_LibraryBooks.getSelectedRow() != -1) {
					// remove selected row from the model
					userTableModel.removeRow(table_LibraryBooks.getSelectedRow());
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

				// TODO when the user press the Remove User Button
				// Remove the user from the User database table

				System.out.println(table_ViewUser.getSelectedRow());
				// Return the no. of selected row

				if (table_ViewUser.getSelectedRow() != -1) {
					// remove selected row from the model
					userTableModel_MyUsers.removeRow(table_ViewUser.getSelectedRow());
				}

			}
		});
		btnRemoveUser.setBounds(812, 299, 117, 35);
		btnRemoveUser.setEnabled(false);
		add(btnRemoveUser);

		// User's Book's Label
		JLabel lblUsersBookList = new JLabel("User's Book List");
		lblUsersBookList.setForeground(Color.GREEN);
		lblUsersBookList.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsersBookList.setBounds(64, 463, 168, 14);
		add(lblUsersBookList);

		// ---------------------------------View user books list

		table_UserBooks = new JTable();

		JScrollPane scrollPane_UserBooks = new JScrollPane();
		scrollPane_UserBooks.setBounds(64, 478, 738, 75);
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

		// Tab to switch between Library books view and Users View
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(64, 163, 738, 279);
		add(tabbedPane);

		// tab Listener
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					btnRemoveUser.setEnabled(false);
					lblUsersBookList.setVisible(false);
					scrollPane_UserBooks.setVisible(false);

				}
				if (tabbedPane.getSelectedIndex() == 1) {
					btnRemoveBook.setEnabled(false);
					lblUsersBookList.setVisible(true);
					scrollPane_UserBooks.setVisible(true);
				}
			}
		});

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

		// row selection Listener
		table_ViewUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// remove user button is Enabled
				btnRemoveUser.setEnabled(true);

				// refresh el user book list view

			}
		});
		scrollPane_ViewUser.setRowHeaderView(table_ViewUser);

		table_ViewUser.setModel(userTableModel_MyUsers);

		table_ViewUser.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane_ViewUser.setViewportView(table_ViewUser);

		txtFindABook = new JTextField();
		txtFindABook.setText("Find a book...");
		txtFindABook.setToolTipText("");
		txtFindABook.setBounds(64, 120, 513, 20);
		add(txtFindABook);
		txtFindABook.setColumns(10);

		JButton btn_Search = new JButton("Search");
		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String searchInput = txtFindABook.getText();
				txtFindABook.setText("");

				// TODO when the user press the search Button
				// Return all the Library books here
				// view the data in the Library Books view

			}
		});
		btn_Search.setBounds(614, 119, 89, 23);
		add(btn_Search);

		String x = "Admin Panel"; // I will get the Full name of the user here
		JLabel lblNewLabel_UserName = new JLabel(x);
		lblNewLabel_UserName.setForeground(Color.GREEN);
		lblNewLabel_UserName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_UserName.setBounds(64, 54, 356, 44);
		add(lblNewLabel_UserName);

		JLabel lblLogOut = new JLabel("Log Out");
		lblLogOut.setForeground(new Color(0, 255, 51));
		lblLogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogOut.setBounds(853, 74, 63, 24);
		add(lblLogOut);

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
