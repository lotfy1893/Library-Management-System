import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
/**
 * 
 * @author Bassem E-Hamed
 * @version 3
 *
 */
@SuppressWarnings("serial")
public class OverdueSearchApp extends JFrame {

	private JPanel contentPane;
	private JTextField EmailAddTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private OverDueDAO overDueDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OverdueSearchApp frame = new OverdueSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public OverdueSearchApp() throws Exception {

		// create the DAO
		try {
			overDueDAO = new OverDueDAO();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}

		setTitle("Overdue Search Tool");
		setBounds(100, 100, 708, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblEnterEmailAdd = new JLabel("Enter Email");
		panel.add(lblEnterEmailAdd);

		EmailAddTextField = new JTextField();
		panel.add(EmailAddTextField);
		EmailAddTextField.setColumns(30);

		List<OverDue> alldueees = overDueDAO.getAllOverDue();
		OverdueTableModel initialModel = new OverdueTableModel(alldueees);
		table = initiateTable(table, initialModel);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get Email from the text field

				// Call DAO and get Member for the Email

				// If Email is empty, then get all Membrs

				// Print out Member

				try {
					String EmailAdd = EmailAddTextField.getText();

					List<OverDue> Members = null;

					if (EmailAdd != null && EmailAdd.trim().length() > 0) {
						Members = overDueDAO.searchOverDue(EmailAdd);
					} else {
						Members = overDueDAO.getAllOverDue();
					}

					OverdueTableModel model = new OverdueTableModel(Members);
					// table = initiateTable(table,model);
					table.setModel(model);

					/*
					 * for (Overdue temp : Members) { System.out.println(temp);
					 * }
					 */
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(OverdueSearchApp.this, "Error: " + exc, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				table.getColumnModel().getColumn(0).setPreferredWidth(70);
				table.getColumnModel().getColumn(1).setPreferredWidth(200);
			}
		});
		panel.add(btnSearch);

		contentPane.add(scrollPane, BorderLayout.CENTER);

	}

	public JTable initiateTable(JTable table, OverdueTableModel model) {
		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
				JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);

				int val = Integer.parseInt(getValueAt(rowIndex, 0).toString());
				if (val > 0 && columnIndex == 0) {
					component.setBackground(Color.RED);
				}
				if (val <= 0 && columnIndex == 0) {
					component.setBackground(Color.GREEN);
				}

				return component;
			}
		};

		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		return table;
	}

}
