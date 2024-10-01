package org.example;
import java.util.List;

public class RowWithLabel extends Row{
    public int numberClass;
    public RowWithLabel(List<Double> data, int numberClass){
        this.data = data;
        this.numberClass = numberClass;
    }
    public int getNumberClass(){
        return this.numberClass;
    }

}
