package org.example;

import Interfaces.Distance;

import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class ManhattanDistance implements Distance {

    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double sum = 0;
        for(int i=0; i<p.size(); i++){
            sum += abs(q.get(i)-p.get(i));
        }
        return sum;
    }
}
