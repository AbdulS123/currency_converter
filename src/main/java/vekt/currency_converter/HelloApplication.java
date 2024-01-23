package vekt.currency_converter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("currency-view.fxml")));

        //Window settings
        stage.setScene(new Scene(root));
        stage.setTitle("Currency Converter");

        stage.setResizable(false);
        stage.setWidth(798);
        stage.setHeight(600);


        Image logo = new Image("vekt/currency_converter/KyrptoGraphic/vekt_icon.png");
        stage.getIcons().add(logo);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}