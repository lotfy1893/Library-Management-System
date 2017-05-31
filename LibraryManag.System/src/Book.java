import java.sql.*;

/**
 * 
 */

/**
 * @author 
 *
 */
public class Book {
	
	private String name;
	private int id;
	private String description;
	private Category category;
	private String author;
	private Date bookIssueDate;
	private Timestamp bookEntryDate; // in db should be borrowDatetime and Return Date only type
	private Timestamp bookBorrowDate;
	private Date bookReturnDate;

	
	public static enum Category{//all possible categories to be entered
		ROMANCE,
		THRILLER,
		HORROR,
		POLICE,
		MYSTERY
	};
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

	public Timestamp getBookBorrowDate() {
		return bookBorrowDate;
	}

	public void setBookBorrowDate(Timestamp bookBorrowDate) {
		this.bookBorrowDate = bookBorrowDate;
	}

	public Date getBookReturnDate() {
		return bookReturnDate;
	}

	public void setBookReturnDate(Date bookReturnDate) {
		this.bookReturnDate = bookReturnDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;   // to print the books' names for each member
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

}
