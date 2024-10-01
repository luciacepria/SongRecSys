package org.example;
import java.util.ArrayList;
import java.util.List;

public class Row {
    public List<Double> data;

    public Row(List<Double> data){
        this.data = data;
    }
    public Row(){
        this.data= new ArrayList<>();
    }
    public void setData(List<Double> data){
        this.data = data;
    }

    public List<Double> getData(){
        return this.data;
    }
}
