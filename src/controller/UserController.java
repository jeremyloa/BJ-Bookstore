package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class UserController {
	
	public static Alert register(String name, String email, String pass, String confpass, String address, LocalDate dob, String gender, boolean tnc) {
		Alert prompt = new Alert(AlertType.INFORMATION);
		String emailval = checkEmail(email);
		String passval = checkPass(pass);
		
		if (name.isEmpty() || name.length() < 5 || name.length() > 30) {
			prompt = new Alert(AlertType.ERROR, "Full name length must be between 5 - 30 characters!", ButtonType.OK);
		} else if (!emailval.equals("")) {
			prompt = new Alert(AlertType.ERROR, emailval, ButtonType.OK);
		} else if (!passval.equals("")) {
			prompt = new Alert(AlertType.ERROR, passval, ButtonType.OK);
		} else if (!pass.equals(confpass)) {
			prompt = new Alert(AlertType.ERROR, "Confirm password should be the same to password!", ButtonType.OK);
		} else if (address.isEmpty() || address.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Address should not be empty!", ButtonType.OK);
		} else if (dob==null || dob.toString().isEmpty()) {
			prompt = new Alert(AlertType.ERROR, "Date of birth should not be empty!", ButtonType.OK);
		} else if (gender==null || (!gender.equals("Male") && !gender.equals("Female"))) {
			prompt = new Alert(AlertType.ERROR, "Gender should not be empty!", ButtonType.OK);
		} else if (!tnc) {
			prompt = new Alert(AlertType.ERROR, "Terms and condition should be accepted!", ButtonType.OK);
		} else {
//			System.out.println(email.getText());
			User user = User.selectOne(email);
//			if (user != null) System.out.println(user.getUserId());
			if (user.getUserId()!=-100) {
				prompt = new Alert(AlertType.ERROR, "User is already registered!", ButtonType.OK);
			}  else {
//				System.out.printf("%s %s %s %s %s %s %s \n", name.getText(), email.getText(), pass.getText(), address.getText(), Date.from(dob.getValue().atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant()), gender.getText(), "user");
				User.refresh();
				User.insert(new User(name, email, pass, address, Date.from(dob.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant()), gender, "user"));
				prompt = new Alert(AlertType.INFORMATION, "Register success. Please login.", ButtonType.OK);
				prompt.show();
				SceneController.toLogin();
			}
		}		
		return prompt;
	}
	public static Alert login(String email, String pass) {
		Alert prompt;
		if (email.isEmpty() || email.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Email is empty!", ButtonType.OK);
		} else if (pass.isEmpty() || pass.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Password is empty!", ButtonType.OK);
		} else {
			User user = User.selectOne(email);
			if (user == null) {
				prompt = new Alert(AlertType.ERROR, "User is not found!", ButtonType.OK);
			} else if (user.getUserId() == -100) {
				prompt = new Alert(AlertType.ERROR, "User is invalid!", ButtonType.OK);
			} else if (!user.getUserPass().equals(pass)) {
				prompt = new Alert(AlertType.ERROR, "Credential is invalid!", ButtonType.OK);
			} else {
				User.curr = user;
				prompt = new Alert(AlertType.INFORMATION, String.format("Welcome, %s!", user.getUserFullName()), ButtonType.OK);
				SceneController.toMain();
			}
		}	
		return prompt;
	}
	static String checkEmail(String email) {
		/*
		  	-	Email must haven’t been taken and be in valid format:
						[email]@[provider].[domain]
			-	Character ‘@’ must not be next to ‘.’.
			-	Must not starts and ends with ‘@’ nor ‘.’.
			-	Must contain exactly one ‘@’.
			-	Must contain minimum one ‘.’ after ‘@’.
		 */
		if (email.isEmpty() || email.equals("")) return "Email must not be empty!";
		if (!email.contains("@") || !email.contains(".")) return "Email must contain @ and .";
		if (email.startsWith("@") || email.startsWith(".") || email.endsWith("@") || email.endsWith(".")) return "Email must not starts and ends with ‘@’ nor ‘.’!"; 
		if (email.charAt(email.indexOf("@")+1) == '.') return "'@' must not be next to '.'!";
		if (email.chars().filter(c -> c == '@').count() != 1) return "Email must contain exactly one ‘@’!";
		if (email.substring(email.indexOf("@")+1).chars().filter(c -> c == '.').count() < 1) return "Email must contain minimum one ‘.’ after ‘@’!";
		return "";
	}
	
	static String checkPass(String pass) {
//	-	Password must 6 - 20 length of character and digit (must at least contain 1 character and 1 digit).
		if (pass.isEmpty() || pass.equals("")) return "Password must not be empty!";
		if (pass.length()<6 || pass.length()>20) return "Password length must be 6 - 20 characters!";
		boolean car = false;
		boolean num = false;
		for (Character ch : pass.toCharArray()) {
			if (ch >= '0' && ch <= '9') num = true;
			if (ch >= 'A' && ch <= 'Z') car = true;
			if (ch >= 'a' && ch <= 'z') car = true;
			if (num && car) break;
		}
		if (!num || !car) return "Password must contain at least 1 character and 1 digit!";
		return "";
	}
}
