package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CSV {
    public Table readTable(String nombreArchivo) throws FileNotFoundException {
        List<String> headers = new ArrayList<>();
        List<Row> rows = new ArrayList<>();

        Scanner scnr = new Scanner(new File(nombreArchivo));
        int cntLineas = 0;

        while (scnr.hasNextLine()){
            cntLineas += 1;
            if(cntLineas == 1){
                String[] headerArray = scnr.nextLine().split(",");
                for(String header : headerArray){
                    headers.add(header.trim());
                }
            }else{
                String[] dataArray = scnr.nextLine().split(",");
                List<Double> rowArray = new ArrayList<>();
                for (String data : dataArray){
                    Double i = Double.parseDouble(data);
                    rowArray.add(i);
                }
                Row fila = new Row(rowArray);
                rows.add(fila);
            }
        }
        scnr.close();
        Table table = new Table(headers, rows);
        return table;
    }

    public TableWithLabels readTableWithLabels(String s) throws FileNotFoundException {
        List<String> headers = new ArrayList<>();
        List<RowWithLabel> rows = new ArrayList<>();
        Map<String, Integer> labelsToIndex = new HashMap<>();

        Scanner scnr = new Scanner(new File(s));
        int cntLineas = 0;


        while (scnr.hasNextLine()) {
            cntLineas += 1;
            if(cntLineas == 1){
                String[] headerArray = scnr.nextLine().split(",");
                for(String header : headerArray){
                    headers.add(header.trim());
                }
            }else{

                String[] dataArray = scnr.nextLine().split(",");
                List<Double> rowArray = new ArrayList<>();
                int numberClass = 0;
                int cntNumClass = 0;
                for(int i = 0; i < dataArray.length-1; i++ ){
                    Double t = Double.parseDouble(dataArray[i]);
                    rowArray.add(t);
                }
                if(labelsToIndex.containsKey(dataArray[dataArray.length-1])){
                    numberClass =labelsToIndex.get(dataArray[dataArray.length-1]);
                }else{
                    labelsToIndex.put(dataArray[dataArray.length-1], cntNumClass);
                    numberClass = cntNumClass;
                    cntNumClass++;
                }
                RowWithLabel row = new RowWithLabel(rowArray, numberClass);
                rows.add(row);
            }

        }
        scnr.close();
        TableWithLabels table = new TableWithLabels(headers, rows, labelsToIndex);
        return table;
    }


}
