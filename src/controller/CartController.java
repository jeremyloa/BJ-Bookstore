package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class CartController {
    public static ArrayList<Cart> getAllCartCurrUser(){
        Cart.refresh();
        ArrayList<Cart> list = new ArrayList<>();
        ArrayList<Cart> maincart = Cart.currCart;
        if (maincart != null && !maincart.isEmpty())
            for (Cart cart:maincart)
                if (cart.getUser().getUserId() == User.curr.getUserId()) list.add(cart);
        return list;
    }
    public static Alert insert(Book book, int qty) {
        if (book == null) return new Alert(AlertType.ERROR, "You have not selected a book!", ButtonType.OK);
        else if (qty < 1) return new Alert(AlertType.ERROR, "Quantity should be more than 0!", ButtonType.OK);
        else if (qty > book.getBookStock()) return new Alert(AlertType.ERROR, "Quantity should be less than the book stock!", ButtonType.OK);
        Cart.insert(new Cart(User.curr, book, qty));
        return new Alert(AlertType.INFORMATION, "Add to cart success", ButtonType.OK);
    }

    public static Alert update(Cart cart, int qty) {
        if (cart == null) return new Alert(AlertType.ERROR, "You have not selected a cart item!", ButtonType.OK);
        else if (qty < 1) return new Alert(AlertType.ERROR, "Quantity should be more than 0!", ButtonType.OK);
        else if (qty > Book.selectOne(cart.getBookId()).getBookStock()) return new Alert(AlertType.ERROR, "Quantity should be less than the book stock!", ButtonType.OK);
        Cart.update(new Cart(cart.getUser(), cart.getBook(), qty));
        return new Alert(AlertType.INFORMATION, "Update cart success", ButtonType.OK);
    }

    public static Alert remove(Cart cart) {
        if (cart == null) return new Alert(AlertType.ERROR, "You have not selected a cart item!", ButtonType.OK);
        Cart.delete(cart);
        return new Alert(AlertType.INFORMATION, "Remove from cart success", ButtonType.OK);
    }

    public static Alert checkout() {
        if (Cart.currCart == null || Cart.currCart.isEmpty()) return new Alert(AlertType.ERROR, "Cart is empty!", ButtonType.OK);
        TransactionHeader.refresh();
        int id = TransactionHeader.currTH.size()<1 ? 1 : TransactionHeader.currTH.size()+1;
        TransactionHeader.insert(new TransactionHeader(id, User.curr, LocalDate.now()));
        for (Cart cart: Cart.currCart){
            TransactionDetail.insert(new TransactionDetail(id, cart.getBook(), cart.getQty()));
            Book.update(new Book(cart.getBookId(), cart.getBook().getBookGenre(), cart.getBookName(), cart.getBookAuthor(), cart.getBookPrice(), Book.selectOne(cart.getBookId()).getBookStock() - cart.getQty() ));
        }
        Cart.deleteAllUser(User.curr);
        return new Alert(AlertType.INFORMATION, "Checkout success", ButtonType.OK);
    }


}
