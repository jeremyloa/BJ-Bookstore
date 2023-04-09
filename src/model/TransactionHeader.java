package model;

import dataaccess.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionHeader {
	private static Database db = Database.getInstance();
	public static ArrayList<TransactionHeader> currTH;
	private static PreparedStatement selectOneID = TransactionHeader.db.prep("SELECT * FROM headertransaction WHERE TransactionID LIKE ? LIMTI 1");
	private static PreparedStatement insertOne = TransactionHeader.db.prep("INSERT INTO headertransaction VALUES (?,?,?)");

	private static ArrayList<TransactionHeader> rsTH(ResultSet res){
		ArrayList<TransactionHeader> list = new ArrayList<>();
		if (res==null) return null;
		try {
			while (res.next()) {
				int transactionID = res.getInt("TransactionID");
				int userID = res.getInt("UserID");
				User usr = User.selectOne(userID);
				Date transactionDateTemp = res.getDate("TransactionDate");
				LocalDate transactionDate = transactionDateTemp.toLocalDate();
				list.add(new TransactionHeader(transactionID, usr, transactionDate));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public static TransactionHeader selectOne(int transactionID){
		ArrayList<TransactionHeader> res = null;
		try {
			selectOneID.setInt(1, transactionID);
			res = rsTH(selectOneID.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res==null) return null;
		return res.get(0);
	}
	public static ArrayList<TransactionHeader> selectAll(){
		ArrayList<TransactionHeader> res = rsTH(TransactionHeader.db.select("SELECT * FROM headertransaction"));
		if (res.isEmpty()) return new ArrayList<>();
		return res;
	}
	public static void refresh() {currTH = selectAll();}
	public static void insert(TransactionHeader th){
		try {
			insertOne.setInt(1, th.getId());
			insertOne.setInt(2, th.getUser().getUserId());
			insertOne.setDate(3, Date.valueOf(th.getDate()));
			insertOne.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}
	private int id;
	private LocalDate date;
	private User user;

	public TransactionHeader(int id, User user, LocalDate date) {
		this.id = id;
		this.user = user;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
