package org.example.Controlador;
import Interfaces.Controlador;
import Interfaces.*;
import Interfaces.Modelo;
import Interfaces.Vista;
import org.example.EuclideanDistance;
import org.example.ManhattanDistance;

import java.io.IOException;
import java.util.List;

public class ImplementacionControlador implements Controlador {


    private Modelo modelo;
    private Vista vista;


    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public void putTipo(int tipo) {
        if(tipo == 0){
            modelo.putTipo("knn");
        }else{
            modelo.putTipo("kmeans");
        }
    }

    @Override
    public void putLikedSong(String song) {
        modelo.putLikedSong(song);
    }

    @Override
    public void putDistancia(int distancia) {
        if(distancia == 0){
            modelo.putDistancia(new EuclideanDistance());
        }else{
            modelo.putDistancia(new ManhattanDistance());
        }
    }

    @Override
    public void putNumRecs(Integer num) {
        modelo.putNumRec(num);
    }


    @Override
    public List<String> recommend() {
        return modelo.getRecommendations();
    }
    @Override
    public List<String> readNames(String fileOfItemNames) throws IOException{
        return modelo.readNames(fileOfItemNames);
    }

}
