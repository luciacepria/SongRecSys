package Interfaces;

import java.io.IOException;
import java.util.EventListener;
import java.util.List;

public interface Controlador extends EventListener {
    void putTipo (int tipo);
    void putLikedSong (String song);
    void putDistancia (int distancia);
    void putNumRecs (Integer num);
    List<String> recommend();
    List<String> readNames(String fileOfItemNames) throws IOException;


}

