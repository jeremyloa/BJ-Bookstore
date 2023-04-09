package view;

import controller.BookController;
import controller.CartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jfxtras.labs.scene.control.window.Window;
import model.Book;
import model.Cart;

public class BuyBookForm {
	private Window win;
	private GridPane base;
	private VBox boxBookList, boxCart, boxActions;
	private Text booksTitle, cartsTitle;
	private TableView<Book> tvBook;
	private TableView.TableViewSelectionModel<Book> selBook;
	private TableView<Cart> tvCart;
	private TableView.TableViewSelectionModel<Cart> selCart;
	private Spinner<Integer> spinQty;
	private SpinnerValueFactory<Integer> spinQtyFactory;
	private Button addToCart, updateCart, removeFromCart, checkout;
	private void initComp() {
		boxBookList = new VBox();
		booksTitle = new Text("Book List");
		booksTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		boxBookList.getChildren().add(booksTitle);
		tvBook = new TableView<>();
		tvBook.setPlaceholder(new Label("Empty"));
		tvBook.setEditable(true);
		TableColumn <Book, Integer> tvbookid = new TableColumn<>("ID");
		tvbookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		tvBook.getColumns().add(tvbookid);
		TableColumn <Book, String> tvbookname = new TableColumn<>("Name");
		tvbookname.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		tvBook.getColumns().add(tvbookname);
		TableColumn <Book, String> tvbookauthor = new TableColumn<>("Author");
		tvbookauthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		tvBook.getColumns().add(tvbookauthor);
		TableColumn <Book, String> tvbookgenre = new TableColumn<>("Genre");
		tvbookgenre.setCellValueFactory(new PropertyValueFactory<>("genreName"));
		tvBook.getColumns().add(tvbookgenre);
		TableColumn <Book, Integer> tvbookstock = new TableColumn<>("Stock");
		tvbookstock.setCellValueFactory(new PropertyValueFactory<>("bookStock"));
		tvBook.getColumns().add(tvbookstock);
		TableColumn <Book, Integer> tvbookprice = new TableColumn<>("Price");
		tvbookprice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
		tvBook.getColumns().add(tvbookprice);
		tvBookRefresh();
		tvBook.prefWidthProperty().bind(App.stg.widthProperty());
		boxBookList.getChildren().add(tvBook);

		boxCart = new VBox();
		cartsTitle = new Text("My Cart");
		cartsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		boxCart.getChildren().add(cartsTitle);
		tvCart = new TableView<>();
		tvCart.setPlaceholder(new Label("Empty"));
		tvCart.setEditable(true);
		TableColumn <Cart, Integer> tvcartbookid = new TableColumn<>("Book ID");
		tvcartbookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		tvCart.getColumns().add(tvcartbookid);
		TableColumn <Cart, String> tvcartbookname = new TableColumn<>("Name");
		tvcartbookname.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		tvCart.getColumns().add(tvcartbookname);
		TableColumn <Cart, String> tvcartbookauthor = new TableColumn<>("Author");
		tvcartbookauthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		tvCart.getColumns().add(tvcartbookauthor);
		TableColumn <Cart, Integer> tvcartbookprice = new TableColumn<>("Price");
		tvcartbookprice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
		tvCart.getColumns().add(tvcartbookprice);
		TableColumn <Cart, Integer> tvcartbookqty = new TableColumn<>("Qty");
		tvcartbookqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		tvCart.getColumns().add(tvcartbookqty);
		tvCartRefresh();
		tvCart.prefWidthProperty().bind(App.stg.widthProperty().divide(4).multiply(3));
		boxCart.getChildren().add(tvCart);

		boxActions = new VBox();
		boxActions.getChildren().add(new Label("Quantity"));
		spinQty = new Spinner<>();
		spinQtyFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,1);
		spinQty.setValueFactory(spinQtyFactory);
		spinQty.setEditable(true);
		spinQty.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		boxActions.getChildren().add(spinQty);
		addToCart = new Button("Add to Cart");
		addToCart.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		addToCart.setOnAction(event -> {
					selBook = tvBook.getSelectionModel();
					Alert prompt = CartController.insert(selBook.getSelectedItem(), spinQty.getValue());
					prompt.show();
					if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) tvCartRefresh();
				}
		);
		boxActions.getChildren().add(addToCart);
		updateCart = new Button("Update Cart");
		updateCart.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		updateCart.setOnAction(event -> {
					selCart = tvCart.getSelectionModel();
					Alert prompt = CartController.update(selCart.getSelectedItem(), spinQty.getValue());
					prompt.show();
					if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) tvCartRefresh();
				}
			);
		boxActions.getChildren().add(updateCart);
		removeFromCart = new Button("Remove from Cart");
		removeFromCart.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		removeFromCart.setOnAction(event -> {
					selCart = tvCart.getSelectionModel();
					Alert prompt = CartController.remove(selCart.getSelectedItem());
					prompt.show();
					if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) tvCartRefresh();
				}
			);
		boxActions.getChildren().add(removeFromCart);
		checkout = new Button("Checkout");
		checkout.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		checkout.setOnAction(event -> {
					Alert prompt = CartController.checkout();
					prompt.show();
					if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) {
						tvCartRefresh();
						tvBookRefresh();
					}
				}
		);
		boxActions.getChildren().add(checkout);
	}

	private void tvBookRefresh(){
		ObservableList<Book> books = FXCollections.observableArrayList(BookController.getAllBooks());
		if (books == null || books.isEmpty()) tvBook.setItems(null);
		else tvBook.setItems(books);
	}

	private void tvCartRefresh(){
		ObservableList<Cart> carts = FXCollections.observableArrayList(CartController.getAllCartCurrUser());
		if (carts == null || carts.isEmpty()) tvCart.setItems(null);
		else tvCart.setItems(carts);
	}
	private void initBase() {
		base = new GridPane();
		base.setPadding(new Insets(20));
		base.setHgap(20);
		base.setVgap(10);
		base.add(boxBookList, 0, 0, 2,1);
		base.add(boxCart, 0, 1);
		base.add(boxActions, 1, 1);
		base.setAlignment(Pos.CENTER);
	}
	
	private void initWindow() {
		win = new Window("Buy Book");
		win.getContentPane().getChildren().add(base);
	}
	
	public BuyBookForm() {
		initComp();
		initBase();
		initWindow();
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}
}
