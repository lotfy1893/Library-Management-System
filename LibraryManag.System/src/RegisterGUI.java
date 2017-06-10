import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author Peter
 *
 */
@SuppressWarnings("serial")
public class RegisterGUI extends JPanel {
	private JPasswordField passwordField;
	private JTextField textField_Email;
	private JTextField textField_FullName;
	private JLabel label_FullName;
	private JLabel label_Email;
	private JLabel label_Password;
	private JLabel lblSignUp;
	private JLabel label_4;
	private JLabel lblLabel_Invalid;

	/**
	 * Create the panel.
	 */
	public RegisterGUI() {
		setBackground(new Color(51, 102, 102));
		setBounds(0, 0, 950, 575);
		setLayout(null);

		passwordField = new JPasswordField();
		passwordField.setBounds(263, 327, 465, 29);
		add(passwordField);

		textField_Email = new JTextField();
		textField_Email.setColumns(10);
		textField_Email.setBounds(263, 287, 465, 29);
		add(textField_Email);

		textField_FullName = new JTextField();
		textField_FullName.setColumns(10);
		textField_FullName.setBounds(263, 247, 465, 29);
		add(textField_FullName);

		label_FullName = new JLabel("Full Name");
		label_FullName.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_FullName.setBounds(156, 250, 147, 20);
		add(label_FullName);

		label_Email = new JLabel("Email");
		label_Email.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_Email.setBounds(156, 290, 147, 20);
		add(label_Email);

		label_Password = new JLabel("Password");
		label_Password.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_Password.setBounds(156, 330, 147, 20);
		add(label_Password);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				inputListenerForRegisterButton();

			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRegister.setBounds(263, 387, 465, 29);
		add(btnRegister);

		lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 26));
		lblSignUp.setBounds(153, 152, 198, 62);
		add(lblSignUp);

		label_4 = new JLabel("Library Management System");
		label_4.setForeground(new Color(51, 204, 0));
		label_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 34));
		label_4.setBounds(241, 75, 492, 57);
		add(label_4);

		lblLabel_Invalid = new JLabel("Invalid Email or Password, Please try again.");
		lblLabel_Invalid.setForeground(new Color(204, 0, 0));
		lblLabel_Invalid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLabel_Invalid.setBounds(263, 206, 394, 20);
		lblLabel_Invalid.setVisible(false);
		add(lblLabel_Invalid);
	}

	@SuppressWarnings("deprecation")
	public void inputListenerForRegisterButton() {

		String email = textField_Email.getText().toLowerCase(); // put the input
		// email String
		// to a lower
		// case
		textField_Email.setText("");

		String fullName = textField_FullName.getText(); // Full name
		textField_FullName.setText("");

		String password = passwordField.getText();
		passwordField.setText("");

		Member m = new Member(email, password,fullName);

		if (!m.isEmailFormatCorrect(email) || password.equals("")) {
			lblLabel_Invalid.setVisible(true);
			revalidate();
			repaint();
		}

		else{
			
			    // add the new member to the database 
				// takes place in member class)
				// TODO code to switch to the BooksGUI Panel
			
			BooksGUI p = new BooksGUI();
		    this.removeAll();
		    add(p);
			revalidate();
		    repaint();

			} 

		

	}
}
