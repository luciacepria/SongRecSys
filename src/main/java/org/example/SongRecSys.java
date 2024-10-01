package org.example;

import Interfaces.Algorithm;
import Interfaces.Distance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SongRecSys {
    private RecSys recsys;
    protected Distance distancia;

    SongRecSys(String method, Distance distancia) throws Exception {

        this.distancia = distancia;
        // File names (could be provided as arguments to the constructor to be more general)
        Map<String,String> filenames = new HashMap<>();
        filenames.put("knn"+"train","songs_train.csv");
        filenames.put("knn"+"test","songs_test.csv");
        filenames.put("kmeans"+"train","songs_train_withoutnames.csv");
        filenames.put("kmeans"+"test","songs_test_withoutnames.csv");

        // Algorithms
        Map<String, Algorithm> algorithms = new HashMap<>();
        algorithms.put("knn",new KNN(this.distancia));
        algorithms.put("kmeans",new Kmeans(15, 200, 4321, this.distancia));

        // Tables
        Map<String, Table> tables = new HashMap<>();
        Map<String, TableWithLabels> tablesWithLabels = new HashMap<>();
        String [] stages = {"train", "test"};

        for (String stage : stages) {
            if (method.equals("knn")) {
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
        this.recsys = new RecSys(algorithms.get(method), this.distancia);

        if(method.equals("knn")){
            this.recsys.train(tablesWithLabels.get(method+"train"));
            this.recsys.run(tablesWithLabels.get(method+"test"), names);
        }else{
            this.recsys.train(tables.get(method +"train"));
            this.recsys.run(tables.get(method+"test"), names);
        }


        // Given a liked item, ask for a number of recomendations
        String liked_name = "Lootkemia";
        List<String> recommended_items = this.recsys.recommend(liked_name,5);

        // Display the recommendation text (to be replaced with graphical display with JavaFX implementation)
        reportRecommendation(liked_name,recommended_items);
    }

    private List<String> readNames(String fileOfItemNames) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileOfItemNames));
        String line;
        List<String> names = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }

    private void reportRecommendation(String liked_name, List<String> recommended_items) {
        System.out.println("If you liked \""+liked_name+"\" then you might like:");
        for (String name : recommended_items)
        {
            System.out.println("\t * "+name);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Knn y euclidean");
        new SongRecSys("knn", new EuclideanDistance());
        System.out.println("Knn y manhattan");
        new SongRecSys("knn", new ManhattanDistance());
        System.out.println("Kmeans y euclidean");
        new SongRecSys("kmeans", new EuclideanDistance());
        System.out.println("Kmeans y manhattan");
        new SongRecSys("kmeans", new ManhattanDistance());
    }
}