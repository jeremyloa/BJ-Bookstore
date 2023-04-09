module bookstore {
	opens view;
	opens model;
	opens controller;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	requires jfxtras.labs;
}