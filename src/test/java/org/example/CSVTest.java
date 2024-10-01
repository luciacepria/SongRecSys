package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {

    @org.junit.jupiter.api.Test
    void readTable() throws Exception{
        CSV csv = new CSV();
        Table table = csv.readTable("miles_dollars.csv");
        CSVUnlabeledFileReader reader_unlabeled = new CSVUnlabeledFileReader("iris.csv");
        reader_unlabeled.readTableFromSource();

        int filas = 25;
        assertEquals(filas, table.rows.size());
        assertEquals(filas, reader_unlabeled.rows.size());

        int columnas = 2;
        assertEquals(columnas, table.rows.get(0).data.size());
        assertEquals(columnas, reader_unlabeled.rows.get(0).data.size());

        Row row = new Row();
        List<Double> list = new ArrayList<>();
        list.add(1211.0);
        list.add(1802.0);
        row.setData(list);
        assertEquals(row.data, table.rows.get(0).data);
        assertEquals(row.data, reader_unlabeled.rows.get(0).data);

        List<String> list2 = new ArrayList<>();
        list2.add("Miles");
        list2.add("Dollars");
        assertEquals(list2, table.headers);
        assertEquals(list2, reader_unlabeled.headers);
    }

    @org.junit.jupiter.api.Test
    void readTableWithLabels() throws Exception{
        CSV csv = new CSV();
        TableWithLabels table = csv.readTableWithLabels("iris.csv");
        CSVLabeledFileReader reader_labeled = new CSVLabeledFileReader("iris.csv");
        reader_labeled.readTableFromSource();
        int filas = 150;
        assertEquals(filas, table.rows.size());
        assertEquals(filas, reader_labeled.rows.size());

        int columnas = 4;
        assertEquals(columnas, table.rows.get(0).data.size());
        assertEquals(columnas, reader_labeled.rows.get(0).data.size());


        List<Double> list = new ArrayList<>();
        list.add(5.1);
        list.add(3.5);
        list.add(1.4);
        list.add(0.2);
        RowWithLabel row = new RowWithLabel(list, table.labelsToIndex.get("Iris-setosa"));
        assertEquals(row.data, table.rows.get(0).data);
        assertEquals(row.data, reader_labeled.rows.get(0).data);

        List<String> list2 = new ArrayList<>();
        list2.add("sepal length");
        list2.add("sepal width");
        list2.add("petal length");
        list2.add("petal width");
        list2.add("class");

        assertEquals(list2, table.headers);
        assertEquals(list2, reader_labeled.headers);

    }
}
