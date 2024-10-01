package org.example;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class ReaderTemplate {
    public abstract void openSource(String source) throws FileNotFoundException;

    public abstract void processHeaders(String headers);

    public abstract void processData(String data);
    public abstract void closeSource();
    public abstract boolean hasMoreData();             // comprueba si hay más datos; en nuestro caso, si hay mas línea(s) en el fichero CSV
    public abstract String getNextData();             // obtener el siguiente dato; una línea del fichero CSV en nuestro caso
    public Table readTableFromSource() throws FileNotFoundException {
        openSource(getSource());
        List<String> headers = readHeaders();
        processHeaders(headers.toString());

        while (hasMoreData()) {
            String data = getNextData();
            processData(data);
        }

        closeSource();
        return createTable(headers);

    }
    protected abstract String getSource();

    protected abstract List<String> readHeaders();

    protected abstract Table createTable(List<String> headers);
}
