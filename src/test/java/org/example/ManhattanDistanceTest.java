package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanDistanceTest {

    @org.junit.jupiter.api.Test
    void calculateDistance() {
        ManhattanDistance mDist = new ManhattanDistance();
        List<Double> a1 = new ArrayList<>();
        List<Double> a2 = new ArrayList<>();

        a1.add(5.0);
        a1.add(-2.0);
        a1.add(1.0);

        a2.add(0.0);
        a2.add(0.0);
        a2.add(0.0);

        assertEquals(8.0, mDist.calculateDistance(a1,a2));

        a1.clear();
        a2.clear();

        a1.add(0.0);
        a1.add(0.0);

        a2.add(0.0);
        a2.add(0.0);

        assertEquals(0, mDist.calculateDistance(a1, a2));
    }
}