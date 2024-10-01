package org.example;
import Interfaces.Algorithm;
import Interfaces.Distance;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.DataInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecSys {
    private Algorithm algorithm;
    private Kmeans kmeans;
    private KNN knn;
    private Map<Integer, List<String>> predictions;
    Distance distancia;

    public RecSys(Algorithm algorithm, Distance distancia){
        this.algorithm = algorithm;
        if(algorithm instanceof KNN){
            knn = new KNN(distancia);
        }else{
            Kmeans alg = (Kmeans)algorithm;
            kmeans = new Kmeans(alg.getNumClusters(), alg.getNumIterations(), alg.getSeed(), distancia);
        }
    }

    public void train(Table trainData) throws Exception {
        if(algorithm instanceof KNN){
            knn.train((TableWithLabels) trainData);
        }else{
            kmeans.train(trainData);
        }
    }

    public void run(Table testData, List<String> testItemNames) throws Exception {
        Map<Integer, List<String>> mapa = new HashMap<>();
        if(algorithm instanceof KNN) {
            TableWithLabels tableWL = (TableWithLabels) testData;
            for(int i = 0; i<tableWL.rows.size(); i++ ){
                int estimate = knn.estimate(tableWL.rows.get(i).data);
                if(mapa.containsKey(estimate)){
                    mapa.get(estimate).add(testItemNames.get(i));
                }else{
                    List<String> lista = new ArrayList<>();
                    lista.add(testItemNames.get(i));
                    mapa.put(estimate, lista);
                }
            }
        }else{
            for(int i = 0; i<testData.rows.size(); i++ ){
                int estimate = kmeans.estimate(testData.rows.get(i).data);
                if(mapa.containsKey(estimate)){
                    mapa.get(estimate).add(testItemNames.get(i));
                }else{
                    List<String> lista = new ArrayList<>();
                    lista.add(testItemNames.get(i));
                    mapa.put(estimate, lista);
                }
            }
        }
        this.predictions = mapa;
    }
    public List<String> recommend(String nameLikedItem, int numRecommendations){
        int index = 0;
        for(Map.Entry<Integer, List<String>> par : this.predictions.entrySet()){
            if(par.getValue().contains(nameLikedItem)){
                index = par.getKey();
                break;
            }
        }
        List<String> lista = this.predictions.get(index);
        this.predictions.get(index).remove(nameLikedItem);
        if(this.predictions.get(index).size() <= numRecommendations){
            return this.predictions.get(index);
        }else{
            return this.predictions.get(index).subList(0, numRecommendations);
        }
    }
}

