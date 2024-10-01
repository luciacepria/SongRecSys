package org.example.Modelo;

import Interfaces.*;
import org.example.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplemenrtaconModelo implements Modelo {
    private Vista vista;
    private Controlador controlador;
    protected String tipo;
    protected Distance dist;
    protected String song;
    protected int numRecs;
    protected RecSys recSys;
    protected List<String> recommended_items = new ArrayList<>();



    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }
    public void setVista(Vista vista){
        this.vista = vista;
    }

    @Override
    public void putTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void putDistancia(Distance distancia) {
        this.dist = distancia;
    }

    @Override
    public void putLikedSong(String song) {
        this.song = song;

    }

    @Override
    public void putNumRec(Integer num) {
        this.numRecs = num;
    }


    @Override
    public void start() throws Exception{

        // File names (could be provided as arguments to the constructor to be more general)
        Map<String,String> filenames = new HashMap<>();
        filenames.put("knn"+"train","songs_train.csv");
        filenames.put("knn"+"test","songs_test.csv");
        filenames.put("kmeans"+"train","songs_train_withoutnames.csv");
        filenames.put("kmeans"+"test","songs_test_withoutnames.csv");

        // Algorithms
        Map<String, Algorithm> algorithms = new HashMap<>();
        algorithms.put("knn",new KNN(this.dist));
        algorithms.put("kmeans",new Kmeans(15, 200, 4321, this.dist));

        // Tables
        Map<String, Table> tables = new HashMap<>();
        Map<String, TableWithLabels> tablesWithLabels = new HashMap<>();
        String [] stages = {"train", "test"};

        for (String stage : stages) {
            if (this.tipo.equals("knn")) {
                CSVLabeledFileReader reader1 = new CSVLabeledFileReader(filenames.get("knn" + stage));
                tablesWithLabels.put("knn" + stage, reader1.readTableFromSource());
            } else {
                CSVUnlabeledFileReader reader2 = new CSVUnlabeledFileReader(filenames.get("kmeans" + stage));
                tables.put("kmeans" + stage, reader2.readTableFromSource());
            }
        }

        // Names of items
        List<String> names = readNames("songs_test_names.csv");

        // Start the RecSys
        this.recSys = new RecSys(algorithms.get(this.tipo), this.dist);

        if(this.tipo.equals("knn")){
            this.recSys.train(tablesWithLabels.get(this.tipo+"train"));
            this.recSys.run(tablesWithLabels.get(this.tipo+"test"), names);
        }else{
            this.recSys.train(tables.get(this.tipo +"train"));
            this.recSys.run(tables.get(this.tipo+"test"), names);
        }


        // Given a liked item, ask for a number of recomendations
        String liked_name = this.song;
        List<String> recommended_items = this.recSys.recommend(liked_name,vista.getNumRec());

        this.recommended_items = recommended_items;
    }



    public List<String> readNames(String fileOfItemNames) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileOfItemNames));
        String line;
        List<String> names = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }

    @Override
    public List<String> getRecommendations(){
        return this.recommended_items;
    }
}
