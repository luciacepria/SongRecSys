package org.example.Vista;
import Interfaces.Controlador;
import Interfaces.Distance;
import Interfaces.Modelo;
import Interfaces.Vista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.*;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class ImplementacionVista implements Vista {
    Stage stage;
    private Controlador controlador;
    private Modelo modelo;
    protected int recomType;
    protected int distType;
    protected String song;
    protected int numRec;

    public ImplementacionVista (Stage stage){
        this.stage = stage;
    }

    @Override
    public int getTipo() {
        return this.recomType;
    }

    @Override
    public int getDistancia() {
       return this.distType;
    }

    @Override
    public String getLikedSong() {
        return this.song;
    }

    @Override
    public int getNumRec(){
        return this.numRec;
    }

    public void pantalla1() throws IOException {
        AtomicBoolean recomSelected = new AtomicBoolean(false);
        AtomicBoolean distSelected = new AtomicBoolean(false);
        AtomicBoolean songSelected = new AtomicBoolean(false);

        this.stage.setTitle("Song Recommender");

        Font font1 = Font.font("Helvetica Neue", 15);
        Font font2 = Font.font("Helvetica Neue", 12);
        Label recommendationLabel = new Label("Recommendation Type:");

        RadioButton recommendSongFeatures = new RadioButton("Recommend based on song features");
        RadioButton recommendGuessedGenre = new RadioButton("Recommend based on guessed genre");
        ToggleGroup recommendationGroup = new ToggleGroup();
        recommendSongFeatures.setSelected(true);
        this.recomType = 0;
        controlador.putTipo(this.recomType);

        recommendSongFeatures.setToggleGroup(recommendationGroup);
        recommendGuessedGenre.setToggleGroup(recommendationGroup);

        Label distanceLabel = new Label("Distance Type:");

        RadioButton euclideanDistance = new RadioButton("Euclidean");
        RadioButton manhattanDistance = new RadioButton("Manhattan");
        euclideanDistance.setSelected(true);
        this.distType = 0;
        controlador.putDistancia(this.distType);

        Button btn = new Button("Recommend");
        btn.setFont(font2);
        btn.setDisable(true);

        recommendationGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (recommendationGroup.getSelectedToggle() != null) {
                if (newToggle.equals(recommendSongFeatures)) {
                    this.recomType = 0;
                } else if (newToggle.equals(recommendGuessedGenre)) {
                    this.recomType = 1;
                }
            }
            controlador.putTipo(this.getTipo());
        });

        recommendationLabel.setFont(font1);
        recommendSongFeatures.setFont(font2);
        recommendGuessedGenre.setFont(font2);

        distanceLabel.setFont(font1);
        euclideanDistance.setFont(font2);
        manhattanDistance.setFont(font2);

        ToggleGroup distanceGroup = new ToggleGroup();
        euclideanDistance.setToggleGroup(distanceGroup);
        manhattanDistance.setToggleGroup(distanceGroup);

        distanceGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (distanceGroup.getSelectedToggle() != null) {
                if (newToggle.equals(euclideanDistance)) {
                    this.distType = 0;
                } else if (newToggle.equals(manhattanDistance)) {
                    this.distType = 1;
                }
            }
            controlador.putDistancia(getDistancia());
        });

        Label songLabel = new Label("Song Titles");
        Font font3 = Font.font("Helvetica Neue", 20);
        songLabel.setFont(font3);

        ObservableList<String> items = FXCollections.observableArrayList(controlador.readNames("songs_test_names.csv"));
        ListView<String> lv = new ListView<>(items);

        lv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                songSelected.set(true);
                this.song = newValue;
            } else {
                songSelected.set(false);
            }
            if (songSelected.get()) {
                btn.setDisable(false);
            }
            controlador.putLikedSong(this.getLikedSong());
        });


        btn.setOnAction(event -> {
            try {
                pantalla2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TextField buscarCancion = new TextField();
        buscarCancion.setFont(font2);
        buscarCancion.setPromptText("Search song title");
        buscarCancion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                lv.setItems(items);
            } else {
                ObservableList<String> filteredList = FXCollections.observableArrayList();
                for (String item : items) {
                    if (item.toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                lv.setItems(filteredList);
            }
        });

        VBox box = new VBox(10);
        box.getChildren().addAll(recommendationLabel, recommendSongFeatures, recommendGuessedGenre, distanceLabel, euclideanDistance, manhattanDistance, songLabel, buscarCancion, lv, btn);

        Scene scene = new Scene(box, 350, 650);
        stage.setScene(scene);
        stage.show();
    }

    public void pantalla2() throws Exception {

        stage.setTitle("Recommended titles");

        Font font1 = Font.font("Helvetica Neue",12);
        Label nRec = new Label("Number of recommendations: ");
        Spinner<Integer> spinner = new Spinner<>(1, 25, 5, 1);
        this.numRec = spinner.getValue();

        nRec.setFont(font1);

        Label mightLike = new Label("If you liked "+ this.getLikedSong() +" you might like: ");
        mightLike.setFont(font1);


        modelo.start();
        ObservableList<String> items = FXCollections.observableArrayList(modelo.getRecommendations());
        ListView<String> lv = new ListView<>(items);

        spinner.setOnMouseClicked(event->{
            this.numRec = spinner.getValue();
            try {
                modelo.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ObservableList<String> newItems = FXCollections.observableArrayList(modelo.getRecommendations());
            lv.setItems(newItems);
        });



        Button btn2 = new Button("Close");

        btn2.setOnAction(event ->stage.close());
        btn2.setOnAction(actionEvent -> {
            try {
                pantalla1();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        VBox box2 = new VBox(10);

        box2.getChildren().addAll(nRec,spinner , mightLike, lv, btn2);
        Scene scene2 = new Scene(box2, 300, 400);
        stage.setScene(scene2);
        stage.show();
    }
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

}
