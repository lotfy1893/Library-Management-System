import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Peter
 *
 */
public class BooksGUI extends JPanel {

	/**
	 * Create the panel.
	 */
	
	
	
	public BooksGUI() {
	
			//setOpaque(true);
			setLayout(null);
			setBackground(new Color(51, 102, 102));
			setBounds(0, 0, 950, 575);
			
			JButton btnNewButton = new JButton("books GUI hena");
			btnNewButton.setBounds(148, 116, 159, 62);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			add(btnNewButton);

	}

}
