import java.awt.BorderLayout;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author Peter
 *
 */
public class AddBookGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField_BookName;
	private JTextField textField_AuthorName;
	private JTextField textField_Category;
	private JTextField textField_Availability;
	private JTextField textField_EntryDate;
	private JTextField textField_IssueDate;
	private JTextField textField_noOfCopies;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBookGUI frame = new AddBookGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddBookGUI() {

		setLocationRelativeTo(null);
		setTitle("Add New Book");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 547, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_BookName = new JTextField();
		textField_BookName.setBounds(10, 33, 222, 20);
		contentPane.add(textField_BookName);
		textField_BookName.setColumns(10);

		JLabel lblLabel_BookName = new JLabel("Book Name");
		lblLabel_BookName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLabel_BookName.setBounds(10, 11, 87, 14);
		contentPane.add(lblLabel_BookName);

		JLabel IbI_AuthorName = new JLabel("Author Name");
		IbI_AuthorName.setFont(new Font("Tahoma", Font.BOLD, 12));
		IbI_AuthorName.setBounds(10, 64, 87, 14);
		contentPane.add(IbI_AuthorName);

		textField_AuthorName = new JTextField();
		textField_AuthorName.setColumns(10);
		textField_AuthorName.setBounds(10, 85, 222, 20);
		contentPane.add(textField_AuthorName);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCategory.setBounds(10, 123, 87, 14);
		contentPane.add(lblCategory);

		textField_Category = new JTextField();
		textField_Category.setColumns(10);
		textField_Category.setBounds(10, 144, 222, 20);
		contentPane.add(textField_Category);

		JLabel lblAvailability = new JLabel("Availability");
		lblAvailability.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAvailability.setBounds(10, 186, 87, 14);
		contentPane.add(lblAvailability);

		textField_Availability = new JTextField();
		textField_Availability.setColumns(10);
		textField_Availability.setBounds(10, 208, 222, 20);
		contentPane.add(textField_Availability);

		JLabel lblEntryDate = new JLabel("Entry Date");
		lblEntryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEntryDate.setBounds(316, 123, 87, 14);
		contentPane.add(lblEntryDate);

		textField_EntryDate = new JTextField();
		textField_EntryDate.setColumns(10);
		textField_EntryDate.setBounds(316, 144, 205, 20);
		contentPane.add(textField_EntryDate);

		JLabel lblIssueDate = new JLabel("Issue Date");
		lblIssueDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIssueDate.setBounds(316, 11, 87, 14);
		contentPane.add(lblIssueDate);

		textField_IssueDate = new JTextField();
		textField_IssueDate.setColumns(10);
		textField_IssueDate.setBounds(316, 33, 205, 20);
		contentPane.add(textField_IssueDate);

		JLabel lblNumberOfCopies = new JLabel("Number of Copies");
		lblNumberOfCopies.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfCopies.setBounds(316, 64, 115, 14);
		contentPane.add(lblNumberOfCopies);

		textField_noOfCopies = new JTextField();
		textField_noOfCopies.setColumns(10);
		textField_noOfCopies.setBounds(316, 85, 205, 20);
		contentPane.add(textField_noOfCopies);

		JButton Button_EnterData = new JButton("Enter Data");
		Button_EnterData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Button_EnterData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bookName = textField_BookName.getText();
				String authorName = textField_AuthorName.getText();
				String category = textField_Category.getText();
				String availability = textField_Availability.getText();
				String entryDate = textField_EntryDate.getText();
				String issueDate = textField_IssueDate.getText();
				String noOfCopies = textField_noOfCopies.getText();

				// TODO fill the Database with the book information given
				// Add book entery fel Admin GUI as well

			}
		});
		Button_EnterData.setBounds(49, 263, 429, 31);
		contentPane.add(Button_EnterData);
	}
}
