package controller;

import view.BuyBookForm;
import view.MainForm;
import view.ManageBookForm;
import view.ManageGenreForm;
import view.TransactionForm;

public class WindowController {
	private static BuyBookForm buyBookForm;
	private static TransactionForm transForm;
	private static ManageBookForm mgBookForm;
	private static ManageGenreForm mgGenreForm;
	
	public static void clear() {
		MainForm.base.setCenter(null);
	}
	
	public static void toBuyBook() {
		buyBookForm = new BuyBookForm();
		MainForm.base.setCenter(buyBookForm.getWin());
	}
	
	public static void toTransForm() {
		transForm = new TransactionForm();
		MainForm.base.setCenter(transForm.getWin());
	}
	
	public static void toMgBook() {
		mgBookForm = new ManageBookForm();
		MainForm.base.setCenter(mgBookForm.getWin());
	}
	
	public static void toMgGenre() {
		mgGenreForm = new ManageGenreForm();
		MainForm.base.setCenter(mgGenreForm.getWin());
	}
}
