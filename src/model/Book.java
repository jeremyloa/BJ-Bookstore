package model;

import dataaccess.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Book {
	private static Database db = Database.getInstance();
	public static ArrayList<Book> currBooks;
	private static PreparedStatement selectOneID = Book.db.prep("SELECT * FROM book WHERE BookID LIKE ? LIMIT 1");
	private static PreparedStatement insertOne = Book.db.prep("INSERT INTO book VALUES (?,?,?,?,?,?)");
	private static PreparedStatement updateOne = Book.db.prep("UPDATE book SET GenreID = ?, BookName = ?, BookAuthor = ?, BookPrice = ?, BookStock = ? WHERE BookID = ?");
	private static PreparedStatement deleteOne = Book.db.prep("DELETE FROM book WHERE BookID = ?");
	private static ArrayList<Book> rsBooks(ResultSet res){
		ArrayList<Book> list = new ArrayList<>();
		if (res==null) return null;
		try {
			while (res.next()) {
				int id = res.getInt("BookID");
				int genreID = res.getInt("GenreID");
				Genre genre = Genre.selectOne(genreID);
				String bookName = res.getString("BookName");
				String bookAuthor = res.getString("BookAuthor");
				int bookPrice = res.getInt("BookPrice");
				int bookStock = res.getInt("BookStock");
				list.add(new Book(id, genre, bookName, bookAuthor, bookPrice, bookStock));
			}
		} catch (Exception e) {
			e.printStackTrace();
		};
		return list;
	}

	public static Book selectOne(int bookid){
		ArrayList<Book> res = null;
		try {
			selectOneID.setInt(1, bookid);
			res = rsBooks(selectOneID.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null || res.isEmpty()) return null;
		return res.get(0);
	}

	public static ArrayList<Book> selectAll(){
		ArrayList<Book> res = rsBooks(Book.db.select("SELECT * FROM book"));
		if (res.isEmpty()) return  new ArrayList<>();
		return res;
	}
	public static void refresh() {currBooks=selectAll();}

	public static void insert(Book book){
		try {
			insertOne.setInt(1, currBooks.get(currBooks.size()-1).getBookId()+1);
			insertOne.setInt(2, book.getBookGenre().getId());
			insertOne.setString(3, book.getBookName());
			insertOne.setString(4, book.getBookAuthor());
			insertOne.setInt(5, book.getBookPrice());
			insertOne.setInt(6, book.getBookStock());
			insertOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	public static void update(Book book){
		try {
			updateOne.setInt(1, book.getBookGenre().getId());
			updateOne.setString(2, book.getBookName());
			updateOne.setString(3, book.getBookAuthor());
			updateOne.setInt(4, book.getBookPrice());
			updateOne.setInt(5, book.getBookStock());
			updateOne.setInt(6, book.getBookId());
			updateOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	public static void delete(Book book){
		try {
			deleteOne.setInt(1, book.getBookId());
			deleteOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	private int id;
	private Genre genre;
	private String name, author, genreName;
	private int price, stock;
	
	public Book(Genre bookGenre, String bookName, String bookAuthor, int bookPrice, int bookStock) {
		this.genre = bookGenre;
		this.genreName = genre.getName();
		this.name = bookName;
		this.author = bookAuthor;
		this.price = bookPrice;
		this.stock = bookStock;
	}

	public Book(int bookId, Genre bookGenre, String bookName, String bookAuthor, int bookPrice, int bookStock) {
		this.id = bookId;
		this.genre = bookGenre;
		this.genreName = genre.getName();
		this.name = bookName;
		this.author = bookAuthor;
		this.price = bookPrice;
		this.stock = bookStock;
	}

	public int getBookId() {
		return id;
	}

	public void setBookId(int bookId) {
		this.id = bookId;
	}

	public Genre getBookGenre() {
		return genre;
	}

	public void setBookGenre(Genre bookGenre) {
		this.genre = bookGenre;
		this.genreName = genre.getName();
	}

	public String getGenreName() {
		return genreName;
	}

	public String getBookName() {
		return name;
	}

	public void setBookName(String bookName) {
		this.name = bookName;
	}

	public String getBookAuthor() {
		return author;
	}

	public void setBookAuthor(String bookAuthor) {
		this.author = bookAuthor;
	}

	public int getBookPrice() {
		return price;
	}

	public void setBookPrice(int bookPrice) {
		this.price = bookPrice;
	}

	public int getBookStock() {
		return stock;
	}

	public void setBookStock(int bookStock) {
		this.stock = bookStock;
	}
	

}
