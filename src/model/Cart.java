package model;

import dataaccess.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Cart {
	private static Database db = Database.getInstance();
	public static ArrayList<Cart> currCart;
	private static PreparedStatement selectOneID = Cart.db.prep("SELECT * FROM cart WHERE UserID LIKE ? AND BookID LIKE ? LIMIT 1");
	private static PreparedStatement insertOne = Cart.db.prep("INSERT INTO cart VALUES (?,?,?)");
	private static PreparedStatement updateOne = Cart.db.prep("UPDATE cart SET CartQty = ? WHERE UserID LIKE ? AND BookID LIKE ?");
	private static PreparedStatement deleteOne = Cart.db.prep("DELETE FROM cart WHERE UserID LIKE ? AND BookID LIKE ?");
	private static ArrayList<Cart> rsCart(ResultSet res){
		ArrayList<Cart> list = new ArrayList<>();
		if (res==null) return null;
		try {
			while (res.next()) {
				int userID = res.getInt("UserID");
				User usr = User.selectOne(userID);
				int bookID = res.getInt("BookID");
				Book bk = Book.selectOne(bookID);
				int cartQty = res.getInt("CartQty");
				list.add(new Cart(usr, bk, cartQty));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public static Cart selectOne(int userid, int bookid){
		ArrayList<Cart> res = null;
		try {
			selectOneID.setInt(1, userid);
			selectOneID.setInt(2, bookid);
			res = rsCart(selectOneID.executeQuery());
		} catch (Exception e){
			e.printStackTrace();
		}
		if (res==null||res.isEmpty()) return null;
		return res.get(0);
	}
	public static ArrayList<Cart> selectAll(){
		ArrayList<Cart> res = rsCart(Cart.db.select("SELECT * FROM cart"));
		if (res.isEmpty()) return null;
		return res;
	}
	public static void refresh() {currCart=selectAll();}
	public static void insert(Cart cart){
		try {
			insertOne.setInt(1,cart.getUser().getUserId());
			insertOne.setInt(2, cart.getBook().getBookId());
			insertOne.setInt(3, cart.getQty());
			insertOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}
	public static void update(Cart cart){
		try {
			updateOne.setInt(1, cart.getQty());
			updateOne.setInt(2,cart.getUser().getUserId());
			updateOne.setInt(3, cart.getBook().getBookId());
			updateOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}
	public static void delete(Cart cart){
		try {
			deleteOne.setInt(1,cart.getUser().getUserId());
			deleteOne.setInt(2, cart.getBook().getBookId());
			deleteOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	public static void deleteAllUser(User user){
		try{
			Cart.db.insertupdatedelete(String.format("DELETE FROM cart WHERE UserID LIKE '%s'", user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	private User user;
	private Book book;
	private int qty;

	private int userId, bookId, bookPrice;
	private String bookName, bookAuthor;

	public Cart(User user, Book book, int qty) {
		this.user = user;
		this.userId = user.getUserId();
		this.book = book;
		this.bookId = book.getBookId();
		this.bookName = book.getBookName();
		this.bookAuthor = book.getBookAuthor();
		this.bookPrice = book.getBookPrice();
		this.qty = qty;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
		this.userId = user.getUserId();
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
		this.bookId = book.getBookId();
		this.bookName = book.getBookName();
		this.bookAuthor = book.getBookAuthor();
		this.bookPrice = book.getBookPrice();
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
