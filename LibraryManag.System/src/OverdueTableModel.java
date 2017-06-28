import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
class OverdueTableModel extends AbstractTableModel {

	private static final int OverdueDays = 0;
	private static final int Email = 1;
	private static final int BookName = 2;
	private static final int EntryDate = 3;
	private static final int BorrowDate = 4;
	private static final int ReturnDate = 5;

	private String[] columnNames = { "OverdueDays", "Email", "BookName", "EntryDate", "BorrowDate", "ReturnDate" };
	private List<OverDue> members;

	public OverdueTableModel(List<OverDue> overdueMembers) {
		members = overdueMembers;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return members.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		OverDue tempMembers = members.get(row);

		switch (col) {
		case OverdueDays:
			return tempMembers.getOverdueDays();
		case Email:
			return tempMembers.getEmail();
		case BookName:
			return tempMembers.getBookName();
		case EntryDate:
			return tempMembers.getEntryDate();
		case BorrowDate:
			return tempMembers.getBorrowDate();
		case ReturnDate:
			return tempMembers.getReturnDate();
		default:
			return tempMembers.getBookName();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
