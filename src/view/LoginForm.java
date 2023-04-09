package view;

import controller.SceneController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LoginForm {
	private BorderPane base;
	private GridPane form;
	private Scene scene;
	private Text apptitle, logintitle, subtitle, copyright;
	private Label lblEmail, lblPass;
	private TextField email;
	private PasswordField pass;
	private Hyperlink goToRegis;
	private Button goLogin;
	
	/*
		•	Text for Title and “Don’t have an account?”.
		•	Label for Email and Password.
		•	TextField for input Email.
		•	PasswordField for input Password.
		•	Hyperlink for Register.
		•	Button for Login.
	 */
	
	private void initComp() {
		apptitle = new Text("Bluejack Bookstore");
		apptitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		logintitle = new Text("Login");
		logintitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
		logintitle.setTextAlignment(TextAlignment.CENTER);
		subtitle = new Text("Don't have an account?");
		subtitle.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
		subtitle.setTextAlignment(TextAlignment.CENTER);
		copyright = new Text("© Bluejack Bookstore 2022.");
		copyright.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
		copyright.setTextAlignment(TextAlignment.CENTER);
		lblEmail = new Label("Email");
		email = new TextField();
		lblPass = new Label("Password");
		pass = new PasswordField();
		goToRegis = new Hyperlink("Register");
		goToRegis.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneController.toRegister();
			}
		});
		goLogin = new Button("Login");
		/*
		 	-	Email field must be filled.
			-	Password field must be filled.
			-	Email and Password must be correct according to the existing data in database.
		 */
		goLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert prompt = UserController.login(email.getText(), pass.getText());
				prompt.show();
			}
		});
	}
	
	private void initForm() {
		form = new GridPane();
		
		form.add(logintitle, 0, 0, 2, 1);
		GridPane.setHalignment(logintitle, HPos.CENTER);
		
		form.add(lblEmail, 0, 1);
		form.add(email, 1, 1);
		
		form.add(lblPass, 0, 2);
		form.add(pass, 1, 2);
		
		form.add(goLogin, 0, 3, 2, 1);
		GridPane.setHalignment(goLogin, HPos.CENTER);
		
		form.add(subtitle, 0, 5, 2, 1);
		GridPane.setHalignment(subtitle, HPos.CENTER);
		
		form.add(goToRegis, 0, 6, 2, 1);
		GridPane.setHalignment(goToRegis, HPos.CENTER);
		
		form.setAlignment(Pos.CENTER);
		form.setHgap(20);
		form.setVgap(10);
	}
	
	private void initBase() {
		base = new BorderPane();
		
		base.setPadding(new Insets(30));
		
		base.setTop(apptitle);
		BorderPane.setAlignment(apptitle, Pos.CENTER);
		
		base.setCenter(form);
		
		base.setBottom(copyright);
		BorderPane.setAlignment(copyright, Pos.CENTER);
		
		base.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));
	}
	
	private void initScene() {
		scene = new Scene(base, 400, 600);
	}
	public LoginForm() {
		initComp();
		initForm();
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
