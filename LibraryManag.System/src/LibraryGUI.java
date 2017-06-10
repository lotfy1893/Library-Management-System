import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Peter Bessada
 *
 */
@SuppressWarnings("serial")
public class LibraryGUI extends JFrame {

	private JFrame frmLibraryManagementSystem;
	private JTextField textField_Email;
	private JPasswordField passwordField;
	private JPanel panel = new JPanel();
	private JPanel panel11 = new JPanel();
	private JLabel lblLabel_Invalid = new JLabel("Invalid Email or Password, Please try again.");
	static LibraryGUI window = new LibraryGUI();

	private MemberRepository memberRepository;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryGUI window = new LibraryGUI();
					window.frmLibraryManagementSystem.setLocationRelativeTo(null);
					window.frmLibraryManagementSystem.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryGUI() {
		initialize();
		memberRepository = new MemberRepository();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frmLibraryManagementSystem = new JFrame();
		frmLibraryManagementSystem.setTitle("Library Management System");
		frmLibraryManagementSystem.setResizable(false);
		frmLibraryManagementSystem.getContentPane().setBackground(new Color(51, 102, 102));
		frmLibraryManagementSystem.getContentPane().setLayout(null);

		panel11.setBackground(new Color(51, 102, 102));
		panel11.setBounds(0, 0, 950, 575);
		panel11.setLayout(null);
		panel11.add(panel);

		JLabel lblLabel_Title = new JLabel("Library Management System");
		lblLabel_Title.setForeground(new Color(51, 204, 0));
		lblLabel_Title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 34));
		lblLabel_Title.setBounds(218, 43, 492, 57);
		panel11.add(lblLabel_Title);

		frmLibraryManagementSystem.setBounds(100, 100, 956, 604);
		frmLibraryManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setBackground(new Color(51, 153, 153));
		panel.setBounds(153, 137, 651, 339);
		frmLibraryManagementSystem.getContentPane().add(panel11);
		panel.setLayout(null);

		JLabel lblLabel_LOGIN = new JLabel("LOGIN");
		lblLabel_LOGIN.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 26));
		lblLabel_LOGIN.setBounds(32, 44, 198, 62);
		panel.add(lblLabel_LOGIN);

		lblLabel_Invalid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLabel_Invalid.setForeground(new Color(204, 0, 0));
		lblLabel_Invalid.setBounds(106, 100, 421, 35);
		panel.add(lblLabel_Invalid);
		lblLabel_Invalid.setVisible(false);

		JLabel lblLabel_Email = new JLabel("Email");
		lblLabel_Email.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLabel_Email.setBounds(32, 146, 147, 20);
		panel.add(lblLabel_Email);

		textField_Email = new JTextField();
		textField_Email.setBounds(106, 146, 465, 29);
		panel.add(textField_Email);
		textField_Email.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(32, 189, 147, 20);
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(106, 186, 465, 29);
		panel.add(passwordField);
		// passwordField.getText();

		JButton btn_Login = new JButton("Login");
		btn_Login.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inputListenerForLoginButton();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// call
												// inputListenerForLoginButton
												// method when "Login" button is
												// pressed
			}
		});
		btn_Login.setBounds(106, 250, 465, 29);
		panel.add(btn_Login);

		JLabel lblLable_Visitor = new JLabel("Login as Vistor");
		lblLable_Visitor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLable_Visitor.setBounds(464, 294, 147, 20);
		panel.add(lblLable_Visitor);

		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSignUp.setBounds(396, 297, 46, 14);
		panel.add(lblSignUp);

		// if I pressed "Login as Vistor" Label--> then I will open the books
		// TODO switch to the Vistor View
		lblLable_Visitor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// open the vistor view frame
				BooksGUI p = new BooksGUI();
				frmLibraryManagementSystem.remove(panel11);
				frmLibraryManagementSystem.getContentPane().add(p);
				frmLibraryManagementSystem.revalidate();
				frmLibraryManagementSystem.repaint();

			}
		});

		lblSignUp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// open the Sign up page
				RegisterGUI R = new RegisterGUI();
				frmLibraryManagementSystem.remove(panel11);
				frmLibraryManagementSystem.getContentPane().add(R);
				frmLibraryManagementSystem.revalidate();
				frmLibraryManagementSystem.repaint();

			}
		});
	}

	@SuppressWarnings("unused")
	public void inputListenerForLoginButton() throws SQLException { // do what
																	// inside
																	// inputListener
		// when Login is pressed

		String email = textField_Email.getText().toLowerCase(); // put the input
																// email String
																// to a lower
																// case
		textField_Email.setText("");

		@SuppressWarnings("deprecation")
		String password = passwordField.getText();
		passwordField.setText("");

		Member m = memberRepository.passwordMatchesForLogin(email, password);

		if (!m.isEmailFormatCorrect(email) || password.equals("")) {
			lblLabel_Invalid.setVisible(true);
			panel.revalidate();
			panel.repaint();
			return;
		}

		if (m != null) {
			if (m.isAdmin()) {
				// view el admin Panel
				AdminGUI admin = new AdminGUI();
				frmLibraryManagementSystem.remove(panel11);
				frmLibraryManagementSystem.getContentPane().add(admin);
				frmLibraryManagementSystem.revalidate();
				frmLibraryManagementSystem.repaint();

			} else {
				BooksGUI p = new BooksGUI();
				frmLibraryManagementSystem.remove(panel11);
				frmLibraryManagementSystem.getContentPane().add(p);
				frmLibraryManagementSystem.revalidate();
				frmLibraryManagementSystem.repaint();
				//go to gui book
			}
		} else {
			lblLabel_Invalid.setVisible(true);
			panel.revalidate();
			panel.repaint();
		}

	}

}
