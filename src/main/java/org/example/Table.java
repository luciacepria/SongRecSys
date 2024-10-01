package org.example;
import java.util.ArrayList;
import java.util.List;

public class Table {
    public List<String> headers;
    public List<Row> rows;
    public Table (List<String> headers, List<Row> rows){
        this.headers = headers;
        this.rows = rows;
    }
    public Table(){
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }
    public Row getRowAt(int i){
        Row fila;
        fila = this.rows.get(i);
        return fila;
    }
}
