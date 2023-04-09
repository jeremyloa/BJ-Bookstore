package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Book;
import model.Genre;

import java.util.ArrayList;

public class BookController {
    public static ArrayList<Book> getAllBooks(){
        Book.refresh();
        return Book.selectAll();
    }

    public static Alert insert(String bookName, String bookAuthor, Genre bookGenre, String bookPrice, int bookStock){
        /*
        	If admin clicks Insert button, then validate:
        	Name must consist of 5 - 45 characters.
        	Author must consist of 5 - 20 characters.
        	Genre must be choosed.
        	Price must be numeric.
        	Stock must more than 0.
        	If all conditions for Insert are fulfilled, then program will insert new book to database, reset all fields, and show Notification Alert.
         */
        if (bookName == null || bookName.isEmpty() || bookAuthor == null || bookAuthor.isEmpty() || bookPrice == null) return new Alert(Alert.AlertType.ERROR, "All fields must be filled!", ButtonType.OK);
        if (bookName.length() < 5 || bookName.length()>45) return new Alert(Alert.AlertType.ERROR, "Book name length must be only 5-45 characters!", ButtonType.OK);
        if (bookAuthor.length() < 5 || bookAuthor.length()>20) return new Alert(Alert.AlertType.ERROR, "Book author length must be only 5-20 characters!", ButtonType.OK);
        if (bookGenre == null) return new Alert(Alert.AlertType.ERROR, "Book genre must be chosen!", ButtonType.OK);
        int price;
        try {
            price = Integer.parseInt(bookPrice);
        } catch (Exception e) {
            return new Alert(Alert.AlertType.ERROR, "Price should be numeric!", ButtonType.OK);
        }
        if (bookStock <1) return new Alert(Alert.AlertType.ERROR, "Stock should be more than 0!", ButtonType.OK);
        int size = Book.currBooks.size() < 1? 1 : Book.currBooks.size()+1;
        Book.insert(new Book(size, bookGenre,bookName,bookAuthor,price,bookStock));
        return new Alert(Alert.AlertType.INFORMATION, "Add book success", ButtonType.OK);
    }

    public static Alert update(String bookID, String bookName, String bookAuthor, Genre bookGenre, String bookPrice, int bookStock){
        /*
        	If admin clicks Update button, then validate:
        	ID is not empty (by click a row in book table).
        	Name must consist of 5 - 45 characters.
        	Author must consist of 5 - 20 characters.
        	Genre must be choosed.
        	Price must be numeric.
        	Stock must more than 0.
        	If all conditions for Update are fulfilled, then program will update book’s data in database, reset all fields, and show Notification Alert.
         */
        if (bookID == null) return new Alert(Alert.AlertType.ERROR, "Book must be chosen!", ButtonType.OK);
        if (bookName == null || bookName.isEmpty() || bookAuthor == null || bookAuthor.isEmpty() || bookPrice == null) return new Alert(Alert.AlertType.ERROR, "All fields must be filled!", ButtonType.OK);
        if (bookName.length() < 5 || bookName.length()>45) return new Alert(Alert.AlertType.ERROR, "Book name length must be only 5-45 characters!", ButtonType.OK);
        if (bookAuthor.length() < 5 || bookAuthor.length()>20) return new Alert(Alert.AlertType.ERROR, "Book author length must be only 5-20 characters!", ButtonType.OK);
        if (bookGenre == null) return new Alert(Alert.AlertType.ERROR, "Book genre must be chosen!", ButtonType.OK);
        int price;
        try {
            price = Integer.parseInt(bookPrice);
        } catch (Exception e) {
            return new Alert(Alert.AlertType.ERROR, "Price should be numeric!", ButtonType.OK);
        }
        if (bookStock <1) return new Alert(Alert.AlertType.ERROR, "Stock should be more than 0!", ButtonType.OK);
        Book.update(new Book(Integer.parseInt(bookID), bookGenre,bookName,bookAuthor,price,bookStock));
        return new Alert(Alert.AlertType.INFORMATION, "Update book success", ButtonType.OK);
    }

    public static Alert delete(Book book){
        if (book == null) return new Alert(Alert.AlertType.ERROR, "Book must be chosen!", ButtonType.OK);
        Book.delete(book);
        return new Alert(Alert.AlertType.INFORMATION, "Delete book success", ButtonType.OK);
    }

}
