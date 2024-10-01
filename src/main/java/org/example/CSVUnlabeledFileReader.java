package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVUnlabeledFileReader extends ReaderTemplate{
    protected String source;
    protected Scanner scnr;
    protected List<String> headers;
    protected List<Row> rows;

    public CSVUnlabeledFileReader(String source){
        this.source = source;
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }
    @Override
    public void openSource(String source) throws FileNotFoundException {
        scnr = new Scanner(new File(source));
    }
    @Override
    public void processHeaders(String headers){
        List<String> hdrs = new ArrayList<>();
        String[] headerArray = headers.split(",");
        for(String header : headerArray){
            hdrs.add(header.trim());
        }
        this.headers = hdrs;
    }
    @Override
    public void processData(String data){
        String[] dataArray = data.split(",");
        List<Double> rowArray = new ArrayList<>();

        for (String dat : dataArray){
            Double i = Double.parseDouble(dat.trim());
            rowArray.add(i);
        }
        Row fila = new Row(rowArray);
        this.rows.add(fila);

    }
    @Override
    public void closeSource(){
        scnr.close();
    }
    @Override
    public boolean hasMoreData(){           // comprueba si hay más datos; en nuestro caso, si hay mas línea(s) en el fichero CSV
        return scnr.hasNextLine();
    }
    @Override
    public String getNextData(){
        return scnr.nextLine();
    }

    @Override
    protected String getSource() {
        return this.source;
    }


    @Override
    protected List<String> readHeaders() {
        processHeaders(scnr.nextLine());
        return this.headers;
    }

    @Override
    protected Table createTable(List<String> headers) {
        Table tabla = new Table(headers, this.rows);
        return tabla;
    }
    protected String getHeaders(){
        return this.headers.toString();
    }
    protected String getData(){
        List<String> vecDat = new ArrayList<>();
        String dat = " ";
        for(int i = 0; i<this.rows.size(); i++){
            vecDat.add(this.rows.get(i).data.toString());
        }
        for(int t = 0; t<vecDat.size(); t++){
            dat = dat +"\n"+  vecDat.get(t);
        }
        return dat;
    }

}
