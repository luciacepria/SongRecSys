package Interfaces;

import java.io.IOException;
import java.util.EventListener;
import java.util.List;

public interface Modelo extends EventListener {
    void putTipo (String tipo );
    void putDistancia (Distance distancia);
    void putLikedSong (String song);
    void putNumRec (Integer num);
    void start () throws Exception;
    List<String> getRecommendations();
    public List<String> readNames(String fileOfItemNames) throws IOException;

    void setControlador(Controlador controlador);
    void setVista(Vista vista);


}
