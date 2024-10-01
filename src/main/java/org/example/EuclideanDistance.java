package org.example;

import Interfaces.Distance;

import java.util.List;

import static java.lang.Math.sqrt;

public class EuclideanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double sum = 0;
        for(int i = 0; i< p.size(); i++){
            sum  +=  (q.get(i) -p.get(i))*(q.get(i) -p.get(i));
        }
        return sqrt(sum);
    }
}
