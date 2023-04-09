package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Genre;

import java.util.ArrayList;

public class GenreController {

    public static ArrayList<Genre> getAllGenre() {
        Genre.refresh();
        return Genre.currGenres;
    }

    public static Alert insert(String name) {
        if (name == null || name.isEmpty()) return new Alert(Alert.AlertType.ERROR, "Name should not be empty!", ButtonType.OK);
        if (name.length()<5 || name.length()>12) return new Alert(Alert.AlertType.ERROR, "Name length should only be 5-12 characters!", ButtonType.OK);
        int size = Genre.currGenres.size() < 1 ? 1 : Genre.currGenres.size()+1;
        Genre.insert(new Genre(name,size));
        return new Alert(Alert.AlertType.INFORMATION, String.format("Added genre %s", name), ButtonType.OK);
    }

}
