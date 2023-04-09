package view;

import java.io.File;
import java.io.FileInputStream;

import controller.SceneController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{
	public static Stage stg;
	@Override
	public void start(Stage stg) throws Exception {
		App.stg = stg;
		SceneController.toLogin();
		App.stg.setMaximized(false);
		App.stg.setResizable(false);
		App.stg.setTitle("Bluejack Bookstore");
		App.stg.getIcons().add(new Image(new FileInputStream(new File("assets/book.png"))));
		App.stg.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
