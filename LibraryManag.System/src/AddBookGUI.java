import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/**
 * 
 */

/**
 * @author Peter Bessada
 *
 */
@SuppressWarnings("serial")
public class AddBookGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField_BookName;
	private JTextField textField_AuthorName;
	private JTextField textField_Category;
	private JTextField textField_IssueDate;
	private BookRepository bookRepository;
	private JTextField textField_Version;

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
	 * this is the constructor to Create the frame.
	 */
	public AddBookGUI() {

		bookRepository = new BookRepository();
		setLocationRelativeTo(null);
		setTitle("Add New Book");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 587, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_BookName = new JTextField();
		textField_BookName.setBounds(10, 33, 222, 20);
		contentPane.add(textField_BookName);
		textField_BookName.setColumns(10);

		JLabel lblLabel_BookName = new JLabel("Book Name *");
		lblLabel_BookName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLabel_BookName.setBounds(10, 11, 87, 14);
		contentPane.add(lblLabel_BookName);

		JLabel IbI_AuthorName = new JLabel("Author Name*");
		IbI_AuthorName.setFont(new Font("Tahoma", Font.BOLD, 12));
		IbI_AuthorName.setBounds(10, 64, 101, 14);
		contentPane.add(IbI_AuthorName);

		textField_AuthorName = new JTextField();
		textField_AuthorName.setColumns(10);
		textField_AuthorName.setBounds(10, 85, 222, 20);
		contentPane.add(textField_AuthorName);

		JLabel lblCategory = new JLabel("Category*");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCategory.setBounds(10, 123, 87, 14);
		contentPane.add(lblCategory);

		textField_Category = new JTextField();
		textField_Category.setColumns(10);
		textField_Category.setBounds(10, 144, 222, 20);
		contentPane.add(textField_Category);

		JLabel lblIssueDate = new JLabel("Issue Date*");
		lblIssueDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIssueDate.setBounds(316, 11, 87, 14);
		contentPane.add(lblIssueDate);

		textField_IssueDate = new JTextField();
		textField_IssueDate.setColumns(10);
		textField_IssueDate.setBounds(316, 33, 245, 20);
		contentPane.add(textField_IssueDate);

		JLabel lblNumberOfCopies = new JLabel("Number of Copies");
		lblNumberOfCopies.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfCopies.setBounds(316, 64, 115, 14);
		contentPane.add(lblNumberOfCopies);

		JLabel lblBorrowPeriod = new JLabel("Borrow Period");
		lblBorrowPeriod.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBorrowPeriod.setBounds(316, 124, 115, 14);
		contentPane.add(lblBorrowPeriod);

		SpinnerNumberModel model_BorrowPeriod = new SpinnerNumberModel(0, 0, 14, 1);
		JSpinner spinner_BorrowPeriod = new JSpinner(model_BorrowPeriod);
		spinner_BorrowPeriod.setBounds(316, 144, 60, 20);
		contentPane.add(spinner_BorrowPeriod);

		SpinnerNumberModel model_noOfCopies = new SpinnerNumberModel(0, 0, 50, 1);
		JSpinner spinner_noOfCopies = new JSpinner(model_noOfCopies);
		spinner_noOfCopies.setBounds(316, 85, 60, 20);
		contentPane.add(spinner_noOfCopies);

		JLabel lblBookNAmeRequired = new JLabel("Book Name required");
		lblBookNAmeRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookNAmeRequired.setForeground(new Color(165, 42, 42));
		lblBookNAmeRequired.setBounds(109, 11, 123, 14);
		lblBookNAmeRequired.setVisible(false);
		contentPane.add(lblBookNAmeRequired);

		JLabel lblAuthorNameRequired = new JLabel("Author Name required");
		lblAuthorNameRequired.setForeground(new Color(165, 42, 42));
		lblAuthorNameRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAuthorNameRequired.setBounds(109, 64, 146, 14);
		lblAuthorNameRequired.setVisible(false);
		contentPane.add(lblAuthorNameRequired);

		JLabel lblCategoryRequired = new JLabel("Category required");
		lblCategoryRequired.setForeground(new Color(165, 42, 42));
		lblCategoryRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCategoryRequired.setBounds(109, 124, 123, 14);
		lblCategoryRequired.setVisible(false);
		contentPane.add(lblCategoryRequired);

		JLabel lblIssueDateRequired = new JLabel("Issue Date invalid(YYYY-MM-DD)\r\n");
		lblIssueDateRequired.setForeground(new Color(165, 42, 42));
		lblIssueDateRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIssueDateRequired.setBounds(398, 12, 194, 14);
		lblIssueDateRequired.setVisible(false);
		contentPane.add(lblIssueDateRequired);

		JLabel lblBookAlreadyExists = new JLabel("This Book already exists in the Library!");
		lblBookAlreadyExists.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBookAlreadyExists.setForeground(new Color(165, 42, 42));
		lblBookAlreadyExists.setBounds(181, 293, 290, 14);
		lblBookAlreadyExists.setVisible(false);
		contentPane.add(lblBookAlreadyExists);

		JLabel lblBookRegisteredSuccessfully = new JLabel("Book registered Successfully");
		lblBookRegisteredSuccessfully.setForeground(new Color(34, 139, 34));
		lblBookRegisteredSuccessfully.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBookRegisteredSuccessfully.setBounds(202, 278, 240, 14);
		lblBookRegisteredSuccessfully.setVisible(false);
		contentPane.add(lblBookRegisteredSuccessfully);

		JLabel lblVersion = new JLabel("Version*");
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVersion.setBounds(316, 185, 115, 14);
		contentPane.add(lblVersion);

		textField_Version = new JTextField();
		textField_Version.setColumns(10);
		textField_Version.setBounds(316, 210, 245, 20);
		contentPane.add(textField_Version);

		JLabel lbl_VersionRequired = new JLabel("Version required");
		lbl_VersionRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_VersionRequired.setForeground(new Color(165, 42, 42));
		lbl_VersionRequired.setBounds(398, 185, 143, 14);
		lbl_VersionRequired.setVisible(false);
		contentPane.add(lbl_VersionRequired);

		JLabel lblDescription = new JLabel("Description*");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescription.setBounds(10, 185, 101, 14);
		contentPane.add(lblDescription);

		JLabel lblDescriptionRequired = new JLabel("Description required");
		lblDescriptionRequired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescriptionRequired.setForeground(new Color(165, 42, 42));
		lblDescriptionRequired.setBounds(109, 186, 123, 14);
		lblDescriptionRequired.setVisible(false);
		contentPane.add(lblDescriptionRequired);

		JTextPane textPane_Description = new JTextPane();
		textPane_Description.setBounds(10, 210, 222, 57);
		contentPane.add(textPane_Description);

		JButton Button_EnterData = new JButton("Enter Data");
		Button_EnterData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		/**
		 * this is the action listner of the button Enter Data we take all the
		 * inputs from the Admin and save them on the data base
		 */
		Button_EnterData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bookName = textField_BookName.getText();
				String authorName = textField_AuthorName.getText();
				String category = textField_Category.getText();
				String issueDate = textField_IssueDate.getText();
				int noOfCopies = model_noOfCopies.getNumber().intValue();
				int borrowPeriod = model_BorrowPeriod.getNumber().intValue();
				String description = textPane_Description.getText();
				String version = textField_Version.getText();

				boolean passedAllValidations = true;
				Date issueParsed = null;
				if (bookName.equals("")) {
					lblBookNAmeRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					lblBookNAmeRequired.setVisible(false);
				}
				if (authorName.equals("")) {
					lblAuthorNameRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					lblAuthorNameRequired.setVisible(false);
				}
				if (category.equals("")) {
					lblCategoryRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					lblCategoryRequired.setVisible(false);
				}
				if (description.equals("")) {
					lblDescriptionRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					lblDescriptionRequired.setVisible(false);
				}
				if (version.equals("")) {
					lbl_VersionRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					lbl_VersionRequired.setVisible(false);
				}
				if (issueDate.equals("") || !isValidDateFormatForParing(issueDate)) {
					lblIssueDateRequired.setVisible(true);
					lblBookRegisteredSuccessfully.setVisible(false);
					passedAllValidations = false;
				} else {
					try {
						issueParsed = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(issueDate).getTime());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					lblIssueDateRequired.setVisible(false);
				}
				Book newBook = new Book(bookName, description, category, authorName, issueParsed, version, borrowPeriod,
						noOfCopies);
				try {
					if (passedAllValidations) {
						boolean registered = bookRepository.registerNewBookInLibrary(newBook);
						if (!registered) {
							lblBookAlreadyExists.setVisible(true);
							lblBookRegisteredSuccessfully.setVisible(false);
						} else {
							textField_BookName.setText("");
							textField_AuthorName.setText("");
							textField_Category.setText("");
							textField_IssueDate.setText("");
							textPane_Description.setText("");
							textField_Version.setText("");
							lblBookAlreadyExists.setVisible(false);
							lblBookRegisteredSuccessfully.setVisible(true);

						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

		});
		Button_EnterData.setBounds(77, 318, 429, 31);
		contentPane.add(Button_EnterData);

	}

	/**
	 * this method is used to check the format of the entry date
	 * 
	 * @param date
	 *            as a string
	 * @return true or false based on the validity of the date format
	 */
	private static boolean isValidDateFormatForParing(String date) {
		if (date == null || !date.matches("\\d{4}-[01]\\d-[0-3]\\d")) {
			return false;
		} else {
			return true;
		}
	}
}
