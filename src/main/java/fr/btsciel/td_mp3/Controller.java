package fr.btsciel.td_mp3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button buttonPlay;
    @FXML
    public Button buttonStop;
    public Button buttonLiretag;
    public TextField titreText;
    public TextField artistetexte;
    public TextField albumtexte;
    public TextField anneetexte;
    public TextField commentairetexte;
    public TextField genretexte;
    public TextField tracktexte;
    public Button buttonmodifier;

    public Button buttonEnregistrer;
    @FXML
    private Label labelChemin;
    @FXML
    private Label labelFichier;
    @FXML
    private Button buttonFichier;
    @FXML
    private Media media;
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private String str_SelectionerFichier;
    @FXML
    private Path path;
    private Gestion_MP3 gestionMp3;

    private BooleanProperty modifierClique = new SimpleBooleanProperty(false);
    private BooleanProperty isEditable = new SimpleBooleanProperty(false);
    private SimpleStringProperty background = new SimpleStringProperty("-fx-background-color: #bbbbbb");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titreText.editableProperty().bind(isEditable);
        artistetexte.editableProperty().bind(isEditable);
        albumtexte.editableProperty().bind(isEditable);
        anneetexte.editableProperty().bind(isEditable);
        commentairetexte.editableProperty().bind(isEditable);
        tracktexte.editableProperty().bind(isEditable);
        genretexte.editableProperty().bind(isEditable);

        titreText.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        artistetexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        albumtexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        anneetexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        commentairetexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        tracktexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));
        genretexte.styleProperty().bind(Bindings.when(isEditable).then("-fx-background-color: cyan").otherwise(background));

        labelChemin.setText("");
        labelFichier.setText("");
        buttonPlay.setDisable(true);
        buttonStop.setDisable(true);

        buttonFichier.setOnAction(event -> {
            ouvreFichier();
            buttonPlay.setDisable(false);
            buttonStop.setDisable(true);
        });

        buttonPlay.setOnAction(event ->{
            play();
            buttonPlay.setDisable(true);
            buttonStop.setDisable(false);
        });

        buttonStop.setOnAction(event -> {
            stop();
            buttonStop.setDisable(true);
            buttonPlay.setDisable(false);
        });

        buttonLiretag.setOnAction(event -> {
            try {
                gestionMp3.lireTags();
                titreText.setText(gestionMp3.getTag().getTitre());
                artistetexte.setText(gestionMp3.getTag().getArtiste());
                albumtexte.setText(gestionMp3.getTag().getAlbum());
                anneetexte.setText(gestionMp3.getTag().getAnnee());
                commentairetexte.setText(gestionMp3.getTag().getCommentaire());
                genretexte.setText(String.valueOf(gestionMp3.getTag().getGenre()));
                tracktexte.setText(String.valueOf(gestionMp3.getTag().getTrack()));
                background.setValue("-fx-background-color: grey");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonmodifier.setOnAction(event -> Modification());
        buttonEnregistrer.setOnAction(event -> {
            try {
                Enregistrer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void Modification() {
        isEditable.set(true);
        background.setValue("-fx-background-color: cyan");
        buttonEnregistrer.setDisable(false);
    }

    private void Enregistrer() throws IOException {
        background.setValue("-fx-background-color: red");
        gestionMp3.getTag().setTitre(titreText.getText());
        gestionMp3.getTag().setArtiste(artistetexte.getText());
        gestionMp3.getTag().setAlbum(albumtexte.getText());
        gestionMp3.getTag().setAnnee(anneetexte.getText());
        gestionMp3.getTag().setCommentaire(commentairetexte.getText());
        gestionMp3.getTag().setTrack(Byte.parseByte(tracktexte.getText()));
        gestionMp3.getTag().setGenre(Byte.parseByte(genretexte.getText()));
        gestionMp3.enregistrerTags();
        isEditable.set(false);
    }

    private void stop() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            buttonStop.setDisable(true);
            buttonStop.setDisable(false);
        }
    }

    private void play() {
        buttonPlay.setDisable(false);
        buttonStop.setDisable(true);
        mediaPlayer.play();
    }

    private void ouvreFichier() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir un fichier MP3");
        chooser.setInitialDirectory(new File("C:\\Users\\sorin\\Desktop\\TD_MP3\\mp3"));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter( "MP3","*.mp3"));
        File fichierSelectionne = chooser.showOpenDialog(null);
        if (fichierSelectionne != null) {
            gestionMp3 = new Gestion_MP3(fichierSelectionne.toPath());
            labelChemin.setText(fichierSelectionne.getAbsolutePath());
            labelFichier.setText(fichierSelectionne.getName());
            String uriPath = fichierSelectionne.toURI().toString();
            media = new Media(uriPath);
            mediaPlayer = new MediaPlayer(media);
            modifierClique.set(false);
        }
    }
}
