package org.example;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

class KmeansTest {

    @Test
    void train() throws Exception {

        Kmeans Kmeans = new Kmeans(3, 5, 123456, new EuclideanDistance());
        Table table1 = new Table();

        Row row1 = new Row();
        List<Double> dRow1 = new ArrayList<>();
        dRow1.add(1.0);
        row1.data = dRow1;

        Row row2 = new Row();
        List<Double> dRow2 = new ArrayList<>();
        dRow2.add(2.0);
        row2.data = dRow2;

        Row row3 = new Row();
        List<Double> dRow3 = new ArrayList<>();
        dRow3.add(3.0);
        row3.data = dRow3;

        Row row4 = new Row();
        List<Double> dRow4 = new ArrayList<>();
        dRow4.add(101.0);
        row4.data = dRow4;

        Row row5 = new Row();
        List<Double> dRow5 = new ArrayList<>();
        dRow5.add(102.0);
        row5.data = dRow5;

        Row row6 = new Row();
        List<Double> dRow6 = new ArrayList<>();
        dRow6.add(103.0);
        row6.data = dRow6;

        Row row7 = new Row();
        List<Double> dRow7 = new ArrayList<>();
        dRow7.add(1001.0);
        row7.data = dRow7;

        Row row8 = new Row();
        List<Double> dRow8 = new ArrayList<>();
        dRow8.add(1002.0);
        row8.data = dRow8;

        Row row9 = new Row();
        List<Double> dRow9 = new ArrayList<>();
        dRow9.add(1003.0);
        row9.data = dRow9;

        table1.rows.add(row1);
        table1.rows.add(row2);
        table1.rows.add(row3);
        table1.rows.add(row4);
        table1.rows.add(row5);
        table1.rows.add(row6);
        table1.rows.add(row7);
        table1.rows.add(row8);
        table1.rows.add(row9);

        Kmeans.train(table1);

        assertEquals(Kmeans.numClusters, 3);

        assertEquals(Kmeans.estimate(row1.data), Kmeans.estimate(row2.data), Kmeans.estimate(row3.data));
        assertEquals(Kmeans.estimate(row4.data), Kmeans.estimate(row5.data), Kmeans.estimate(row6.data));
        assertEquals(Kmeans.estimate(row7.data), Kmeans.estimate(row8.data), Kmeans.estimate(row9.data));

        Row r1 = new Row();
        List<Double> list = new ArrayList<>();
        list.add(2.0);
        r1.setData(list);
        assertEquals( r1.data, Kmeans.centroides.get(Kmeans.estimate(row1.data)).data);

        Row r2 = new Row();
        List<Double> list2 = new ArrayList<>();
        list2.add(102.0);
        r2.setData(list2);

        assertEquals( r2.data, Kmeans.centroides.get(Kmeans.estimate(row4.data)).data);


        Row r3 = new Row();
        List<Double> list3 = new ArrayList<>();
        list3.add(1002.0);
        r3.setData(list3);
        assertEquals( r3.data, Kmeans.centroides.get(Kmeans.estimate(row7.data)).data);

    }

}

