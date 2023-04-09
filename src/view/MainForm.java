package view;

import java.io.File;
import java.io.FileInputStream;

import controller.SceneController;
import model.User;
import controller.WindowController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainForm {
	private Scene scene;
	public static BorderPane base;
	private Text copyright;
	private MenuBar menubar;
	private Menu user, trans, manage;
	private MenuItem home, logout, buyBook, viewHistory, mgBook, mgGenre;
	
	private void initComp() {
		copyright = new Text("� Bluejack Bookstore 2022.");
		copyright.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
		copyright.setTextAlignment(TextAlignment.CENTER);
		
		menubar = new MenuBar();
		
		user = new Menu("User");
		menubar.getMenus().add(user);
		home = new MenuItem("Home");
		home.setOnAction(event -> WindowController.clear());
		user.getItems().add(home);
		logout = new MenuItem("Logout");
		logout.setOnAction(event -> {
			User.curr = null;
			SceneController.toLogin();
		});
		user.getItems().add(logout);
		
		trans = new Menu("Transaction");
		if (User.curr.getUserRole().equals("user")) menubar.getMenus().add(trans);
		buyBook = new MenuItem("Buy Book");
		buyBook.setOnAction(event -> {
			WindowController.clear();
			WindowController.toBuyBook();
		});
		trans.getItems().add(buyBook);
		viewHistory = new MenuItem("View Transaction History");
		viewHistory.setOnAction(event -> {
			WindowController.clear();
			WindowController.toTransForm();
		});
		trans.getItems().add(viewHistory);
		
		manage = new Menu("Manage");
		if (User.curr.getUserRole().equals("admin")) menubar.getMenus().add(manage);
		mgBook = new MenuItem("Book");
		mgBook.setOnAction(event -> {
			WindowController.clear();
			WindowController.toMgBook();
		});
		manage.getItems().add(mgBook);
		mgGenre = new MenuItem("Genre");
		mgGenre.setOnAction(event -> {
			WindowController.clear();
			WindowController.toMgGenre();
		});
		manage.getItems().add(mgGenre);
	}
	
	private void initBase() {
		base = new BorderPane();
		
		base.setPadding(new Insets(0, 0, 30, 0));
		
		base.setTop(menubar);
		
		base.setBottom(copyright);
		BorderPane.setAlignment(copyright, Pos.CENTER);
		
		try {
			base.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File("assets/bg.jpg"))), null, null, BackgroundPosition.CENTER, new BackgroundSize(800, 600, false, false, false, true))));
		} catch (Exception e) {}
	}
	
	private void initScene() {
		scene = new Scene(base, 1024, 600);
	}

	public MainForm() {
		initComp();
		initBase();
		initScene();
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
