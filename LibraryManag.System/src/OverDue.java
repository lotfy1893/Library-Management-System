/**
 *
 * 
 *
 */
public class OverDue {

	private int overdueDays;
	private String email;
	private String bookName;
	private String entryDate;
	private String borroweDate;
	private String returnDate;

	public OverDue(int overdueDays, String email, String bookName, String entryDate, String borroweDate,
			String returnDate) {
		super();
		this.overdueDays = overdueDays;
		this.email = email;
		this.bookName = bookName;
		this.entryDate = entryDate;
		this.borroweDate = borroweDate;
		this.returnDate = returnDate;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBorroweDate() {
		return borroweDate;
	}

	public void setBorroweDate(String borroweDate) {
		this.borroweDate = borroweDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return String.format(
				"Overdue [overdueDays=%s, email=%s, bookName=%s, entryDate=%s, borroweDate=%s, returnDate=%s]",
				overdueDays, email, bookName, entryDate, borroweDate, returnDate);
	}

}
