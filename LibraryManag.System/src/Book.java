import java.sql.*;

/**
 * 
 */

/**
 * @author Praktikant05
 *
 */
public class Book {

	private String name;
	private int id;
	private String description;
	private String category;
	private String author;
	private Date bookIssueDate;
	private Timestamp bookEntryDate; // in db should be borrowDatetime and
										// Return Date only type
	
	private int numberOfCopies;
	
	private String version;
	private int borrowPeriod; //in days

	public Book(String name, String description, String category, String author, Date issue,
			String version, int borrowPeriod, int no) {

		this.name = name;
		this.description = description;
		this.setCategory(category);
		this.author = author;
		this.bookIssueDate = issue;
//		this.bookEntryDate = entry;
		this.version = version;
		this.borrowPeriod = borrowPeriod;
		this.setNumberOfCopies(no);
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getBookIssueDate() {
		return bookIssueDate;
	}

	public void setBookIssueDate(Date bookIssueDate) {
		this.bookIssueDate = bookIssueDate;
	}

	public Timestamp getBookEntryDate() {
		return bookEntryDate;
	}

	public void setBookEntryDate(Timestamp bookEntryDate) {
		this.bookEntryDate = bookEntryDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name; // to print the books' names for each member
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(int borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getNumberOfCopies() {
		return numberOfCopies;
	}


	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

}
