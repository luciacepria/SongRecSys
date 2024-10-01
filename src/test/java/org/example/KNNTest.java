package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KNNTest {

    @org.junit.jupiter.api.Test
    void estimate() throws Exception {
        CSV csv = new CSV();
        TableWithLabels table = csv.readTableWithLabels("iris.csv");
        KNN knn = new KNN(table, new EuclideanDistance());
        List<Double> lista = new ArrayList<>();
        lista.add(5.1);
        lista.add(3.4);
        lista.add(1.4);
        lista.add(0.2);
        assertEquals(table.labelsToIndex.get("Iris-setosa"), knn.estimate(lista));
        List<Double> lista1 = new ArrayList<>();
        lista1.add(7.0);
        lista1.add(3.2);
        lista1.add(4.8);
        lista1.add(1.3);
        assertEquals(table.labelsToIndex.get("Iris-versicolor"), knn.estimate(lista1));
        List<Double> lista2 = new ArrayList<>();
        lista2.add(7.7);
        lista2.add(2.8);
        lista2.add(6.7);
        lista2.add(2.0);
        assertEquals(table.labelsToIndex.get("Iris-virginica"), knn.estimate(lista2));
    }

}