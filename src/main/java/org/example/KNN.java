package org.example;
import Interfaces.Algorithm;
import Interfaces.Distance;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.nextAfter;
import static java.lang.Math.sqrt;

public class KNN implements Algorithm<TableWithLabels, Integer> {
    protected TableWithLabels table;
    protected Distance distancia;
    public KNN(TableWithLabels table, Distance distancia){

        this.table = table;
        this.distancia = distancia;
    }
    public KNN(Distance distancia){
        this.table = new TableWithLabels();
        this.distancia = distancia;
    }
    @Override
    public void train(TableWithLabels data){
        this.table = data;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Integer estimate(List<Double> data)  {
        double distMin = Double.MAX_VALUE;
        int res = 0;
        for(RowWithLabel row : this.table.rows) {
            double dist = distancia.calculateDistance(row.data, data);
            if (dist <= distMin) {
                distMin = dist;
                res = row.numberClass;
            }
        }
        return res;
    }

}
