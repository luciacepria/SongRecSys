package org.example;
import java.io.FileNotFoundException;
import java.util.*;

public class CSVLabeledFileReader extends CSVUnlabeledFileReader{
    protected List<String> headers;
    protected List<RowWithLabel> rows;
    protected Map<String, Integer> labelsToIndex = new HashMap<>();
    private int cntNumClass;

    public CSVLabeledFileReader(String source){
        super(source);
        this.rows = new ArrayList<>();
        this.headers = new ArrayList<>();
        this.labelsToIndex = new HashMap<>();
        this.cntNumClass = 0;
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

        for (int i = 0; i < dataArray.length - 1; i++) {
            rowArray.add(Double.parseDouble(dataArray[i]));
        }

        String label = dataArray[dataArray.length - 1];
        int numberClass;
        if (labelsToIndex.containsKey(label)) {
            numberClass = labelsToIndex.get(label);
        } else {
            numberClass = cntNumClass;
            labelsToIndex.put(label, cntNumClass);
            cntNumClass++;
        }

        RowWithLabel row = new RowWithLabel(rowArray, numberClass);
        rows.add(row);

    }
    @Override
    protected TableWithLabels createTable(List<String> headers) {
        TableWithLabels tabla = new TableWithLabels(headers,this.rows, this.labelsToIndex);
        return tabla;
    }
    @Override
    public final TableWithLabels readTableFromSource() throws FileNotFoundException {
        openSource(getSource());
        List<String> headers = readHeaders();

        while (hasMoreData()) {
            String data = getNextData();
            processData(data);
        }

        closeSource();
        return createTable(headers);

    }
    protected String getLabels(){
        return labelsToIndex.keySet().toString();
    }

}
