package model;

import dataaccess.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionDetail {
	private static Database db = Database.getInstance();
	private static PreparedStatement selectAllID = TransactionDetail.db.prep("SELECT * FROM detailtransaction WHERE TransactionID LIKE ?");
	private static PreparedStatement insertOne = TransactionDetail.db.prep("INSERT INTO detailtransaction VALUES (?,?,?)");

	private static ArrayList<TransactionDetail> rsTD(ResultSet res){
		ArrayList<TransactionDetail> list = new ArrayList<>();
		if (res==null) return null;
		try {
			while (res.next()) {
				int transactionID = res.getInt("TransactionID");
				int bookID = res.getInt("BookID");
				Book bk = Book.selectOne(bookID);
				int transactionQty = res.getInt("TransactionQty");
				list.add(new TransactionDetail(transactionID, bk, transactionQty));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<TransactionDetail> selectAllbyID(int transactionID) {
		ArrayList<TransactionDetail> res = null;
		try {
			selectAllID.setInt(1, transactionID);
			res = rsTD(selectAllID.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void insert(TransactionDetail td){
		try {
			insertOne.setInt(1, td.getId());
			insertOne.setInt(2, td.getBook().getBookId());
			insertOne.setInt(3, td.getQty());
			insertOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int id;
    private Book book;
	private int qty;

	private int bookId;
	private int bookPrice;
	private String bookName;
	private String bookAuthor;

    public TransactionDetail(int id, Book book, int qty) {
        this.id = id;
        this.book = book;
		this.setBookId(book.getBookId());
		this.setBookName(book.getBookName());
		this.setBookAuthor(book.getBookAuthor());
		this.setBookPrice(book.getBookPrice());
		this.qty = qty;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
		this.setBookId(book.getBookId());
		this.setBookName(book.getBookName());
		this.setBookAuthor(book.getBookAuthor());
		this.setBookPrice(book.getBookPrice());
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
}
