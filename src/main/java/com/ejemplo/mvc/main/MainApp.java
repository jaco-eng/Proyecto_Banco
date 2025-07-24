package com.ejemplo.mvc.main;


import javafx.stage.Stage;

public class MainApp extends Application {

	public static void main(String[] args) {
		@Override
	    public void start(Stage primaryStage) {
	        new ProductoView(primaryStage);
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}

}
