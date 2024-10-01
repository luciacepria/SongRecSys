package org.example;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controlador.ImplementacionControlador;
import org.example.Modelo.ImplemenrtaconModelo;
import org.example.Vista.ImplementacionVista;

import java.util.concurrent.atomic.AtomicReference;

public class Principal extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        ImplementacionControlador controlador = new ImplementacionControlador();
        ImplemenrtaconModelo modelo = new ImplemenrtaconModelo();
        ImplementacionVista vista = new ImplementacionVista(stage);

        modelo.setControlador(controlador);
        modelo.setVista(vista);

        controlador.setVista(vista);
        controlador.setModelo(modelo);

        vista.setModelo(modelo);
        vista.setControlador(controlador);
        vista.pantalla1();



    }

}
