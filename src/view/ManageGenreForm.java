package view;

import controller.GenreController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.window.Window;
import model.Genre;

public class ManageGenreForm {
	private Window win;
	private GridPane base;
	private VBox vbInsert;
	private TextField tfName;
	private Button btnInsert;
	private TableView<Genre> tvGenre;

	private void initComp(){
		vbInsert = new VBox();
		vbInsert.setPadding(new Insets(20));
		vbInsert.setAlignment(Pos.CENTER_LEFT);
		vbInsert.getChildren().add(new Label("Insert Genre Name"));
		tfName = new TextField();
		vbInsert.getChildren().add(tfName);
		btnInsert = new Button("Insert");
		btnInsert.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		btnInsert.setOnAction(event -> {
			Alert prompt = GenreController.insert(tfName.getText());
			prompt.show();
			if (prompt.getAlertType().equals(Alert.AlertType.INFORMATION)) tvGenRefresh();
		});
		vbInsert.getChildren().add(btnInsert);
		vbInsert.prefWidthProperty().bind(App.stg.widthProperty().divide(4));
		tvGenre = new TableView<>();
		tvGenre.setPlaceholder(new Label("Empty"));
		tvGenre.setEditable(true);
		TableColumn<Genre, Integer> tvgenid = new TableColumn<>("ID");
		tvgenid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tvGenre.getColumns().add(tvgenid);
		TableColumn<Genre, String> tvgenname = new TableColumn<>("Genre Name");
		tvgenname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvGenre.getColumns().add(tvgenname);
		tvGenRefresh();
		tvGenre.prefWidthProperty().bind(App.stg.widthProperty().divide(4).multiply(3));
		tvGenre.prefHeightProperty().bind(App.stg.heightProperty());
	}
	private void tvGenRefresh(){
		ObservableList<Genre> genres = FXCollections.observableList(GenreController.getAllGenre());
		if (genres == null || genres.isEmpty()) tvGenre.setItems(null);
		else tvGenre.setItems(genres);
	}
	private void initBase(){
		base = new GridPane();
		base.add(vbInsert, 0,0);
		base.add(tvGenre, 1,0);
		base.setAlignment(Pos.CENTER);
		base.setBackground(new Background(new BackgroundFill(Color.web("e6e6fa"), CornerRadii.EMPTY, new Insets(0))));
	}
	private void initWindow(){
		win = new Window("Manage Genre");
		win.getContentPane().getChildren().add(base);
	}
	public ManageGenreForm() {
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
