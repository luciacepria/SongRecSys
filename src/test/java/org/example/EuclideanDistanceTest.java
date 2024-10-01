package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class EuclideanDistanceTest {

    @org.junit.jupiter.api.Test
    void calculateDistance() {
        EuclideanDistance eDist = new EuclideanDistance();
        List<Double> a1 = new ArrayList<>();
        List<Double> a2 = new ArrayList<>();
        a1.add(5.0);
        a1.add(2.0);
        a1.add(3.0);

        a2.add(0.0);
        a2.add(0.0);
        a2.add(0.0);
        double resultado = eDist.calculateDistance(a1,a2);
        assertEquals(sqrt(38), resultado);

        a1.clear();
        a2.clear();

        a1.add(0.0);
        a1.add(0.0);

        a2.add(0.0);
        a2.add(0.0);

        resultado = eDist.calculateDistance(a1, a2);
        assertEquals(0.0, resultado);

        a1.clear();
        a2.clear();

        a1.add(-5.0);
        a1.add(-2.0);
        a1.add(-3.0);

        a2.add(0.0);
        a2.add(0.0);
        a2.add(0.0);

        resultado = eDist.calculateDistance(a1, a2);
        assertEquals(sqrt(38), resultado);

    }
}