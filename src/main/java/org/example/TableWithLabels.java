package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table{
    public Map<String, Integer> labelsToIndex;
    public List<RowWithLabel> rows;
    public TableWithLabels(List<String> headers, List<RowWithLabel> rows, Map<String, Integer> labelsToIndex){
        this.headers= headers;
        this.rows = rows;
        this.labelsToIndex =labelsToIndex;
    }

    public TableWithLabels(){
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.labelsToIndex = new HashMap<>();
    }

    public RowWithLabel getRowAt(int i) {
        return this.rows.get(i);
    }
}
