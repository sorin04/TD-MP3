module fr.btsciel.td_mp3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens fr.btsciel.td_mp3 to javafx.fxml;
    exports fr.btsciel.td_mp3;
}