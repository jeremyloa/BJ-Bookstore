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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class RegisterForm {
	private BorderPane base;
	private GridPane form;
	private Scene scene;
	private Text apptitle, registitle, subtitle, copyright;
	private Label lblName, lblEmail, lblPass, lblConf, lblAddress, lblDOB, lblGender;
	private TextField name, email, address;
	private PasswordField pass, confpass;
	private DatePicker dob;
	private FlowPane fpGender;
	private ToggleGroup tgGender;
	private RadioButton rbFemale, rbMale;
	private CheckBox tnc;
	private Hyperlink goToLogin;
	private Button goRegis;
	
	/*
		•	Text for Title and “Already have an account?”.
		•	Label for Full Name, Email, Password, Confirm Password, Address, Date of Birth, and Gender.
		•	TextField for input Full Name, Email, and Address.
		•	PasswordField for input Password and Confirm Password.
		•	DatePicker for input Date of Birth.
		•	RadioButton for input Gender (“Male” or “Female”).
		•	CheckBox for input Terms & Conditions.
		•	Hyperlink for Login.
		•	Button for Register.
	 */
	
	private void initComp() {
		apptitle = new Text("Bluejack Bookstore");
		apptitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		registitle = new Text("Register");
		registitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
		registitle.setTextAlignment(TextAlignment.CENTER);
		subtitle = new Text("Already have an account?");
		subtitle.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
		subtitle.setTextAlignment(TextAlignment.CENTER);
		copyright = new Text("© Bluejack Bookstore 2022.");
		copyright.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
		copyright.setTextAlignment(TextAlignment.CENTER);
		lblName = new Label("Full Name");
		name = new TextField();
		lblEmail = new Label("Email");
		email = new TextField();
		lblPass = new Label("Password");
		pass = new PasswordField();
		lblConf = new Label("Confirm Password");
		confpass = new PasswordField();
		lblAddress = new Label("Address");
		address = new TextField("");
		lblDOB = new Label("Date of Birth");
		dob = new DatePicker();
		fpGender = new FlowPane();
		tgGender = new ToggleGroup();
		lblGender = new Label("Gender");
		rbFemale = new RadioButton("Female");
		rbFemale.setToggleGroup(tgGender);
		rbMale = new RadioButton("Male");
		rbMale.setToggleGroup(tgGender);
		fpGender.getChildren().add(rbFemale);
		fpGender.getChildren().add(rbMale);
		tgGender.selectToggle(rbMale);
		tnc = new CheckBox("Agree to terms and conditions");
		goToLogin = new Hyperlink("Login");
		goToLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneController.toLogin();
			}
		});
		goRegis = new Button("Register");
		
		/*
		 	-	Full Name must be between 5 - 30 characters.
			-	Email must haven’t been taken and be in valid format:
							[email]@[provider].[domain]
				-	Character ‘@’ must not be next to ‘.’.
				-	Must not starts and ends with ‘@’ nor ‘.’.
				-	Must contain exactly one ‘@’.
				-	Must contain minimum one ‘.’ after ‘@’.
			-	Password must 6 - 20 length of character and digit (must at least contain 1 character and 1 digit).
			-	Confirm Password must equal to Password.
			-	Address must be filled.
			-	Date of Birth must be filled.
			-	Gender must be selected either “Male” or “Female”.
			-	Terms and Conditions must be checked.
			(additional note: every error occurrence will be shown on Error Alert).
			•	If user successfully register, insert new user data to database, then program will display a Notification Alert. Lastly, program will close Register Form and open Login Form.
		 */
		goRegis.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RadioButton gender = (RadioButton) tgGender.getSelectedToggle();
				Alert prompt = UserController.register(name.getText(), email.getText(), pass.getText(), confpass.getText(), address.getText(), dob.getValue(), gender.getText(), tnc.isSelected());
				prompt.show();
			}
		});
	}
	
	private void initForm() {
		form = new GridPane();
		
		form.add(registitle, 0, 0, 2, 1);
		GridPane.setHalignment(registitle, HPos.CENTER);
		
		form.add(lblName, 0, 1);
		form.add(name, 1, 1);
		
		form.add(lblEmail, 0, 2);
		form.add(email, 1, 2);
		
		
		form.add(lblPass, 0, 3);
		form.add(pass, 1, 3);
		
		form.add(lblConf, 0, 4);
		form.add(confpass, 1, 4);
		
		form.add(lblAddress, 0, 5);
		form.add(address, 1, 5);
		
		form.add(lblDOB, 0, 6);
		form.add(dob, 1, 6);
		
		form.add(lblGender, 0, 7);
		form.add(fpGender, 1, 7);
		
		form.add(tnc, 0, 8, 2, 1);
		GridPane.setHalignment(tnc, HPos.CENTER);
		
		form.add(goRegis, 0, 9, 2, 1);
		GridPane.setHalignment(goRegis, HPos.CENTER);
		
		form.add(subtitle, 0, 11, 2, 1);
		GridPane.setHalignment(subtitle, HPos.CENTER);
		
		form.add(goToLogin, 0, 12, 2, 1);
		GridPane.setHalignment(goToLogin, HPos.CENTER);
		
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
	public RegisterForm() {
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
