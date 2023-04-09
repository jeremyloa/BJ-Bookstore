package view;

import controller.BookController;
import controller.GenreController;
import controller.WindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.window.Window;
import model.Book;
import model.Genre;

public class ManageBookForm {
	private Window win;
	private GridPane base;
	private VBox vbForm;
	private TextField tfID, tfName, tfAuthor, tfPrice;
	private ComboBox cbGenre;
	private Spinner<Integer> spinStock;
	private SpinnerValueFactory<Integer> spinStockFactory;
	private Button btnInsert, btnUpdate, btnDelete;
	private TableView<Book> tvBook;
	private TableView.TableViewSelectionModel<Book> selBook;

	private void initComp(){
		vbForm = new VBox();
		vbForm.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		vbForm.setPadding(new Insets(10));
		initForm();
		putForm();

		tvBook = new TableView<>();
		tvBook.setPlaceholder(new Label("Empty"));
		tvBook.setEditable(true);
		TableColumn<Book, Integer> tvbookid = new TableColumn<>("ID");
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
		tvBook.prefWidthProperty().bind(App.stg.widthProperty().divide(4).multiply(3));
		tvBook.setOnMouseClicked( event -> {
			if (event.getClickCount() >=1) {
				selBook = tvBook.getSelectionModel();
				Book sel = selBook.getSelectedItem();
				if (sel!=null) setContent(sel);
			}
		});
	}
	private void initForm(){
		tfID = new TextField();
		tfID.setEditable(false);
		tfID.setDisable(true);
		tfID.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		tfName = new TextField();
		tfName.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		tfAuthor = new TextField();
		tfAuthor.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		cbGenre = new ComboBox();
		cbGenre.setItems(FXCollections.observableArrayList(GenreController.getAllGenre()));
		cbGenre.setCellFactory(new Callback<ListView<Genre> , ListCell<Genre> >() {
			@Override
			public ListCell<Genre> call(ListView listView) {
				return new ListCell<Genre>() {
					@Override
					protected void updateItem(Genre item, boolean empty){
						super.updateItem(item, empty);
						if (item == null || empty) setGraphic(null);
						else setText(item.getName());
					}
				};
			}
		});
		cbGenre.setConverter(new StringConverter<Genre>() {
			@Override
			public String toString(Genre genre) {
				if (genre==null) return null;
				else return genre.getName();
			}

			@Override
			public Genre fromString(String s) {
				return null;
			}
		});
		cbGenre.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		tfPrice = new TextField();
		tfPrice.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		spinStock = new Spinner<>();
		spinStockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,1);
		spinStock.setValueFactory(spinStockFactory);
		spinStock.setEditable(true);
		spinStock.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		btnInsert = new Button("Insert");
		btnInsert.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		btnInsert.setOnAction(event -> {
			Alert prompt = BookController.insert(tfName.getText(), tfAuthor.getText(), ((Genre) cbGenre.getSelectionModel().getSelectedItem()), tfPrice.getText(), spinStock.getValue());
			prompt.show();
			if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) {
				WindowController.toMgBook();
//				tvBookRefresh();
//				clearForm();
//				initForm();
			}
		});
		btnUpdate = new Button("Update");
		btnUpdate.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		btnUpdate.setOnAction(event -> {
			Alert prompt = BookController.update(tfID.getText(), tfName.getText(), tfAuthor.getText(), ((Genre) cbGenre.getSelectionModel().getSelectedItem()), tfPrice.getText(), spinStock.getValue());
			prompt.show();
			if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) WindowController.toMgBook();
		});
		btnDelete = new Button("Delete");
		btnDelete.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		btnDelete.setOnAction(event -> {
			selBook = tvBook.getSelectionModel();
			Alert prompt = BookController.delete(selBook.getSelectedItem());
			prompt.show();
			if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) tvBookRefresh();
		});

	}

	private void putForm(){
		vbForm.getChildren().add(new Label("ID"));
		vbForm.getChildren().add(tfID);
		vbForm.getChildren().add(new Label("Name"));
		vbForm.getChildren().add(tfName);
		vbForm.getChildren().add(new Label("Author"));
		vbForm.getChildren().add(tfAuthor);
		vbForm.getChildren().add(new Label("Genre"));
		vbForm.getChildren().add(cbGenre);
		vbForm.getChildren().add(new Label("Price"));
		vbForm.getChildren().add(tfPrice);
		vbForm.getChildren().add(new Label("Stock"));
		vbForm.getChildren().add(spinStock);
		vbForm.getChildren().add(btnInsert);
		vbForm.getChildren().add(btnUpdate);
		vbForm.getChildren().add(btnDelete);
	}
	private void setContent(Book book){
		tfID.setText(String.valueOf(book.getBookId()));
		tfName.setText(book.getBookName());
		tfAuthor.setText(book.getBookAuthor());
		cbGenre.getSelectionModel().select(book.getBookGenre());
		tfPrice.setText(String.valueOf(book.getBookPrice()));
		spinStock.getValueFactory().setValue(book.getBookStock());
	}
	private void clearForm(){
		vbForm.getChildren().set(1, tfID);
		vbForm.getChildren().set(3,tfName);
		vbForm.getChildren().set(5, tfAuthor);
		vbForm.getChildren().set(7, cbGenre);
		vbForm.getChildren().set(9, tfPrice);
		vbForm.getChildren().set(11, spinStock);
	}
	private void tvBookRefresh(){
		ObservableList<Book> books = FXCollections.observableArrayList(BookController.getAllBooks());
		if (books == null || books.isEmpty()) tvBook.setItems(null);
		else tvBook.setItems(books);
	}
	private void initBase(){
		base = new GridPane();
		base.add(vbForm, 0,0);
		base.add(tvBook, 1,0);
		base.setAlignment(Pos.CENTER);
		base.setBackground(new Background(new BackgroundFill(Color.web("e0ffff"), CornerRadii.EMPTY, new Insets(0))));
//		tvBook.setMaxHeight(base.getHeight());
	}

	private void initWindow(){
		win = new Window("Manage Book");
		win.getContentPane().getChildren().add(base);
	}
	public ManageBookForm() {
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
