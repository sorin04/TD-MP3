package fr.btsciel.td_mp3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MP3");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/java/fr/btsciel/td_mp3/icone_tag.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}