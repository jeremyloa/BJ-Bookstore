package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataaccess.Database;
public class User {
	private static Database db = Database.getInstance();
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static ArrayList<User> currUsers;
	public static User curr;
	private static PreparedStatement selectOneEmail = User.db.prep("SELECT * FROM users WHERE UserEmail LIKE ? LIMIT 1");
	private static PreparedStatement selectOneID = User.db.prep("SELECT * FROM users WHERE UserID = ? LIMIT 1");
	private static PreparedStatement insertOne = User.db.prep("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	private static ArrayList<User> rsUsers(ResultSet res) {
		ArrayList<User> list = new ArrayList<>();
		if (res==null) return null;
		try {
			while (res.next()) {
				int id = res.getInt("UserID");
				String fullname = res.getString("UserFullName");
				String mail = res.getString("UserEmail");
				String pass = res.getString("UserPass");
				String address = res.getString("UserAddress");
				Date dob = res.getDate("UserDOB");
				String gender = res.getString("UserGender");
				String role = res.getString("UserRole");
				list.add(new User(fullname, mail, pass, address, dob, gender, role, id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		};
		return list;
	}
	
	public static User selectOne(String email) {
//		String query = String.format("SELECT * FROM users WHERE UserEmail LIKE '%s' LIMIT 1", email);
//		ArrayList<User> res = allUsers(db.select(query));
		ArrayList<User> res = null;
		try {
			selectOneEmail.setString(1, email);
			res = rsUsers(selectOneEmail.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null || res.isEmpty()) return new User(-100);
		return res.get(0);
//		refresh();
//		for (User user: currUsers)
//			if (user.getUserEmail().equals(email)) return user;
//		return new User(-100);
	}
	
	public static User selectOne(int userid) {
//		String query = String.format("SELECT * FROM users WHERE UserID = %d LIMIT 1", userid);
//		ArrayList<User> res = allUsers(db.select(query));
		ArrayList<User> res = null;
		try {
			selectOneID.setInt(1, userid);
			res = rsUsers(selectOneID.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null || res.isEmpty()) return new User(-100);
		return res.get(0);
	}
	
	private static ArrayList<User> selectAll() {
		ArrayList<User> res = rsUsers(User.db.select("SELECT * FROM users"));
		if (res.isEmpty()) return null;
		return res;
	}
	
	public static void refresh() {currUsers = selectAll();}
	
	public static void insert(User user) {
//		String query = String.format(
//				"INSERT INTO users VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
//				currdb.get(currdb.size()-1).getUserId()+1, user.getUserFullName(), user.getUserEmail(), user.getUserPass(), user.getUserAddress(), df.format(user.getUserDOB()), user.getUserGender(), user.getUserRole());
//		db.insertupdatedelete(query);
		try {
			insertOne.setInt(1, currUsers.get(currUsers.size()-1).getUserId()+1);
			insertOne.setString(2, user.getUserFullName());
			insertOne.setString(3, user.getUserEmail());
			insertOne.setString(4, user.getUserPass());
			insertOne.setString(5, user.getUserAddress());
			insertOne.setString(6, df.format(user.getUserDOB()));
			insertOne.setString(7, user.getUserGender());
			insertOne.setString(8, user.getUserRole());
			insertOne.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}
	
	private String fullName, email, pass, address, gender, role;
	private Date dob;
	private int id;
	
	
	public User(int id) {
		this.id = id;
	}

	public User(String userFullName, String userEmail, String userPass, String userAddress, Date userDOB,
			String userGender, String userRole) {
		this.fullName = userFullName;
		this.email = userEmail;
		this.pass = userPass;
		this.address = userAddress;
		this.dob = userDOB;
		this.gender = userGender;
		this.role = userRole;
	}

	public User(String userFullName, String userEmail, String userPass, String userAddress, Date userDOB,
			String userGender, String userRole, int userId) {
		this.fullName = userFullName;
		this.email = userEmail;
		this.pass = userPass;
		this.address = userAddress;
		this.dob = userDOB;
		this.gender = userGender;
		this.role = userRole;
		this.id = userId;
	}

	public String getUserFullName() {
		return fullName;
	}

	public void setUserFullName(String userFullName) {
		this.fullName = userFullName;
	}

	public String getUserEmail() {
		return email;
	}

	public void setUserEmail(String userEmail) {
		this.email = userEmail;
	}

	public String getUserPass() {
		return pass;
	}

	public void setUserPass(String userPass) {
		this.pass = userPass;
	}

	public String getUserAddress() {
		return address;
	}

	public void setUserAddress(String userAddress) {
		this.address = userAddress;
	}

	public Date getUserDOB() {
		return dob;
	}

	public void setUserDOB(Date userDOB) {
		this.dob = userDOB;
	}

	public String getUserGender() {
		return gender;
	}

	public void setUserGender(String userGender) {
		this.gender = userGender;
	}

	public String getUserRole() {
		return role;
	}

	public void setUserRole(String userRole) {
		this.role = userRole;
	}

	public int getUserId() {
		return id;
	}

	public void setUserId(int userId) {
		this.id = userId;
	}
	
}
