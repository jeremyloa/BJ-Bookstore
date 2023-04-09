package model;

import dataaccess.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Genre {
	private static Database db = Database.getInstance();
	public static ArrayList<Genre> currGenres;
	private static PreparedStatement selectOneID = Genre.db.prep("SELECT * FROM genre WHERE GenreID = ? LIMIT 1");
	private static PreparedStatement insertOne = Genre.db.prep("INSERT INTO genre VALUES (?,?)");
	public static ArrayList<Genre> allGenres(ResultSet res){
		ArrayList<Genre> list = new ArrayList<>();
		if (res==null) return null;
		try{
			while (res.next()) {
				int id = res.getInt("GenreID");
				String name = res.getString("GenreName");
				list.add(new Genre(name, id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static Genre selectOne(int id){
		ArrayList<Genre> res = null;
		try {
			selectOneID.setInt(1, id);
			res = allGenres(selectOneID.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null || res.isEmpty()) return null;
		return res.get(0);
	}
	public static ArrayList<Genre> selectAll(){
		ArrayList<Genre> res = allGenres(Genre.db.select("SELECT * FROM genre"));
		if (res.isEmpty()) return null;
		return res;
	}
	public static void refresh() { currGenres = selectAll(); }
	public static void insert(Genre genre){
		try{
			insertOne.setInt(1, genre.getId());
			insertOne.setString(2, genre.getName());
			insertOne.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
		refresh();
	}
	private String name;
	private int id;
	
	public Genre(String genreName, int genreId) {
		this.setName(genreName);
		this.setId(genreId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
