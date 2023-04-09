package view;

import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jfxtras.labs.scene.control.window.Window;
import model.TransactionDetail;
import model.TransactionHeader;

import java.time.LocalDate;

public class TransactionForm {
	private Window win;
	private GridPane base;
	private TableView<TransactionHeader> tvTH;
	private TableView.TableViewSelectionModel<TransactionHeader> selTH;
	private TableView<TransactionDetail> tvTD;
	private TableView.TableViewSelectionModel<TransactionDetail> selTD;

	private void initComp(){
		tvTH = new TableView<>();
		tvTH.setEditable(true);
		tvTH.setPlaceholder(new Label("Empty"));
		TableColumn<TransactionHeader, Integer> tvthid = new TableColumn<>("ID");
		tvthid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tvTH.getColumns().add(tvthid);
		TableColumn<TransactionHeader, LocalDate> tvthdate = new TableColumn<>("Date");
		tvthdate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tvTH.getColumns().add(tvthdate);
		tvTH.setOnMouseClicked( event -> {
			if (event.getClickCount() >= 1) {
				selTH = tvTH.getSelectionModel();
				TransactionHeader sel = selTH.getSelectedItem();
				if (sel!=null) {
					tvTDRefresh(sel.getId());
				}
			}
		});
		tvTHRefresh();
		tvTD = new TableView<>();
		tvTD.setEditable(true);
		tvTD.setPlaceholder(new Label("Empty"));
		TableColumn <TransactionDetail, Integer> tvTDbookid = new TableColumn<>("Book ID");
		tvTDbookid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tvTD.getColumns().add(tvTDbookid);
		TableColumn <TransactionDetail, String> tvTDbookname = new TableColumn<>("Name");
		tvTDbookname.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		tvTD.getColumns().add(tvTDbookname);
		TableColumn <TransactionDetail, String> tvTDbookauthor = new TableColumn<>("Author");
		tvTDbookauthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		tvTD.getColumns().add(tvTDbookauthor);
		TableColumn <TransactionDetail, Integer> tvTDbookprice = new TableColumn<>("Price");
		tvTDbookprice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
		tvTD.getColumns().add(tvTDbookprice);
		TableColumn <TransactionDetail, Integer> tvTDbookqty = new TableColumn<>("Qty");
		tvTDbookqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		tvTD.getColumns().add(tvTDbookqty);
		tvTDRefresh(0);
	}

	private void tvTHRefresh(){
		ObservableList<TransactionHeader> th = FXCollections.observableArrayList(TransactionController.getAllTHCurr());
		if (th == null || th.isEmpty()) tvTH.setItems(null);
		else tvTH.setItems(th);
	}

	private void tvTDRefresh(int id){
		ObservableList<TransactionDetail> td = FXCollections.observableArrayList(TransactionController.getAllTDCurr(id));
		if (td == null || td.isEmpty()) tvTD.setItems(null);
		else tvTD.setItems(td);
	}

	private void initBase(){
		base = new GridPane();
		base.setPadding(new Insets(10));
		base.setHgap(20);
		base.setVgap(10);
		base.add(tvTH, 0, 0);
		tvTH.prefWidthProperty().bind(App.stg.widthProperty().divide(5));
		base.add(tvTD, 1,0);
		tvTD.prefWidthProperty().bind(App.stg.widthProperty().divide(5).multiply(3));
		base.setAlignment(Pos.CENTER);
	}

	private void initWindow(){
		win = new Window("Transaction History");
		win.getContentPane().getChildren().add(base);
	}

	public TransactionForm() {
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
