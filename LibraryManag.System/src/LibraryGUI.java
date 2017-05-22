import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class LibraryGUI {
	


	private JFrame frmLibraryManagementSystem;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPanel panel = new JPanel();
	private JPanel panel11 = new JPanel();
	private JLabel lblNewLabel_4 = new JLabel("Invalid Email or Password, Please try again.");
	static LibraryGUI window = new LibraryGUI();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//LibraryGUI window = new LibraryGUI();
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
	}

	/**
	 * Initialize the contents of the frame.
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
		
		
		JLabel lblNewLabel_2 = new JLabel("Library Management System");
		lblNewLabel_2.setForeground(new Color(51, 204, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 34));
		lblNewLabel_2.setBounds(218, 43, 492, 57);

	
		
		panel11.add(lblNewLabel_2);
		
		frmLibraryManagementSystem.setBounds(100, 100, 956, 604);
		frmLibraryManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		panel.setBackground(new Color(51, 153, 153));
		panel.setBounds(153, 137, 651, 339);
		frmLibraryManagementSystem.getContentPane().add(panel11); 
	 	panel.setLayout(null);
		
		
		
		

		JLabel lblNewLabel_1 = new JLabel("LOGIN");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 26));
		lblNewLabel_1.setBounds(32, 44, 198, 62);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(32, 146, 147, 20);
		panel.add(lblNewLabel);
	

		textField = new JTextField();
		textField.setBounds(106, 143, 465, 29);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(32, 186, 147, 20);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(106, 183, 465, 29);
		panel.add(passwordField);
	//	passwordField.getText();
		

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				inputListener();// call inputListener when "Login" button is pressed
			}
		});
		btnNewButton.setBounds(106, 241, 465, 29);
		panel.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("Login as Vistor");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(475, 281, 147, 20);
		panel.add(lblNewLabel_3);
		
		
	
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setForeground(new Color(204, 0, 0));
		lblNewLabel_4.setBounds(106, 100, 421, 35);
		panel.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);
		
		

		
		// if I pressed "Login as Vistor" Label--> then I will open the books frame
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// open the vistor view frame
				BooksGUI p = new BooksGUI();
				frmLibraryManagementSystem.remove(panel11);
				frmLibraryManagementSystem.getContentPane().add(p);
				frmLibraryManagementSystem.revalidate();
				frmLibraryManagementSystem.repaint();

			}
		});

	}
	
	public void inputListener(){  // do what inside inputListener when Login is pressed 
		String email = textField.getText().toLowerCase(); // put the input email String to a lower case
		textField.setText("");
		@SuppressWarnings("deprecation")
		String password = passwordField.getText();
		Member m = new Member(email,password);
		
		if(!m.isEmailFormatCorrect(email) || password.equals("")){
			lblNewLabel_4.setVisible(true);
			panel.revalidate();
			panel.repaint();	
		}
		
		else if(m.isAdmin(email, password)){
			//view el admin Panel
		
		}
		
		else if(m.isMemberExists(email,password)){
			if(m.isPasswordCorrect(email,password)){
				// switch to the Book Frame 
				

			}else {
				lblNewLabel_4.setVisible(true);
				panel.revalidate();
				panel.repaint();	
			}
				
		} else {
			// if it's  not a member then add the member to the database (adding takes place in member class)
		
			
		}
	
	}
}
