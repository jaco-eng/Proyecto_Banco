package com.ejemplo.mvc.main;

import com.ejemplo.mvc.view.BancoView;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BancoView.primaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
