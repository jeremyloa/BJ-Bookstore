package controller;

import view.App;
import view.LoginForm;
import view.MainForm;
import view.RegisterForm;

public class SceneController {
	private static LoginForm login;
	private static RegisterForm register;
	private static MainForm main;
	
	public static void toLogin() {
		login  = new LoginForm();
		App.stg.setScene(login.getScene());
	}
	
	public static void toRegister() {
		register = new RegisterForm();
		App.stg.setScene(register.getScene());
	}

	public static void toMain() {
		main = new MainForm();
		App.stg.setScene(main.getScene());
	}
}
