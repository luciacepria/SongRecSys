package Interfaces;

import javafx.scene.control.ListView;
import org.example.Row;
import org.w3c.dom.events.Event;

import java.util.EventListener;
import java.util.List;

public interface Vista extends EventListener {
    int getTipo();
    int getDistancia();
    String getLikedSong();
    int getNumRec();
}
