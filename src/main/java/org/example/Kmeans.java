package org.example;
import Interfaces.Algorithm;
import Interfaces.Distance;

import java.util.*;
public class Kmeans implements Algorithm<Table, Integer> {

    public int numClusters;
    public int numIterations;
    public long seed;
    public List<Row> centroides;
    Distance dist;

    public Kmeans(int numClusters, int numIterations, long seed, Distance dist){
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.dist = dist;
        this.centroides = new ArrayList<>();
    }
    public Kmeans(Distance dist){
        this.numClusters = 0;
        this.numIterations = 0;
        this.seed = 0;
        this.dist = dist;
        this.centroides = new ArrayList<>();
    }
    public int getNumClusters(){
        return this.numClusters;
    }
    public int getNumIterations(){
        return this.numIterations;
    }
    public long getSeed(){
        return this.seed;
    }

    @Override
    public  void train(Table datos) throws Exception {
        if (datos.rows.size() < this.numClusters) {
            throw new Exception("Hay menos filas en la tabla que centroides");          //Si hay menos filas en la tabla que en los clusters necesarios se lanza excepcion
        }

        Random random = new Random(this.seed);
        List<Row> clusters = inicializarClusters(datos.rows, random);       // llamo a iniicializarClusters para asignar unos valores iniciales a estos
        Map<Row, List<Row>> clusterMap = new HashMap<>();

        for (int r = 0; r < this.numIterations; r++) {                      //repito tantas veces como iteraciones se pidan
            clusterMap.clear();                                             //vacio el mapa
            for (Row row : datos.rows) {                                    //itero sobre las filas que hay dentro de la tabla
                Row closestCentroid = centroideCercano(row, clusters);      //llamo al metodo centroide_cercano
                if (clusterMap.containsKey(closestCentroid)) {              // si ese centroide ya esta en el mapa de los grupos
                    clusterMap.get(closestCentroid).add(row);               // añado la fila a la lista asociada a ese cluster
                } else {
                    List<Row> lista = new ArrayList<>();                    //sino creo una nueva lista
                    lista.add(row);                                         //añado esa fila
                    clusterMap.put(closestCentroid, lista);                 // creo el par del cluster con la lista donde he añadido la fila
                }
            }

            List<Row> newClusters = calcularClusters(clusterMap);       //llamo a calcular Clusters para que recalcule los nuevos clusters
            clusters = newClusters;                                     //cambio la lista de los clusters a la nueva calculada
        }

        this.centroides = clusters;                                     //cambio el valor de la variable centroides de la clase a los nuevos clusters calculados

    }

    private List<Row> inicializarClusters(List<Row> data, Random random) {
        List<Row> clusters = new ArrayList<>();                             // inicializo una lista vacia
        while (clusters.size() < this.numClusters) {                        // mientras esa lista sea mas pequeña que los clusters que me piden
            Row randomRow = data.get(random.nextInt(data.size()));          // cojo una fila aleatorio de la tabla
            if (!clusters.contains(randomRow)) {                            // si en el vector no esta esa fila
                clusters.add(randomRow);                                    // se añade a la lista de los clusters
            }
        }
        return clusters;                                                    //devuelve la lista de los clusters
    }

    private Row centroideCercano(Row row, List<Row> centroids) {
        Row centroide_cercano = centroids.get(0);                            //coje un centroide, en este caso el primero que suponemos que sera el centroide mas cercano
        double minDistance = Double.MAX_VALUE;                              // inicializamos una variable que guardara la distancia minima con un valor muy grande
        for (Row centroid : centroids) {                                    // para cada centroide centroide en la lista de los centroides
            double distancia = dist.calculateDistance(centroid.data, row.data);           // se calcula la distancia de la flia dada con el centroide
            if (distancia < minDistance) {                                       // si la distancia es mas pequeña que la distancia minima
                minDistance = distancia;                                         // se guarda en la variable minDistande la distancia calculada
                centroide_cercano = centroid;                                // se asigna a la variable centroide cercano el centroide con el que hemos calculado la nueva distancia minima
            }
        }
        return centroide_cercano;                                            // devuelve el centroide que esta mas cerca de la fila dada
    }

    private List<Row> calcularClusters(Map<Row, List<Row>> clusterMap) {
        List<Row> newClusters = new ArrayList<>();                          // creamos una nueva lista vacia que sera auxiliar
        for (Row cluster : clusterMap.keySet()) {                           // para cada centroide en el kayset del mapa
            List<Double> newCenter = new ArrayList<>();                     // creamos una lista auxiliar
            for (int i = 0; i < cluster.data.size(); i++) {                 //iteramos sobre los clusters
                double sum = 0;                                             // inicializamos una variable que almacenara la suma total
                for (Row row : clusterMap.get(cluster)) {                   // para cada fila en la lista de datos asociados al cluster
                    sum += row.data.get(i);                                 // sumamos la posicion i de cada vector
                }
                newCenter.add(sum / clusterMap.get(cluster).size());        // dividimos la suma total entre la cantidad de vectores asociados para sacar la media
            }
            newClusters.add(new Row(newCenter));                            // añadimos a la lista auxiliar el nuevo vector hecho con las medias
        }
        return newClusters;
    }

    public Integer estimate (List<Double> dato){
        double distMin = dist.calculateDistance(dato, this.centroides.get(0).data);
        int index = 0;
        for(Row centroide : this.centroides){
            if(dist.calculateDistance(dato, centroide.data)<distMin){
                distMin = dist.calculateDistance(dato, centroide.data);
                index = centroides.indexOf(centroide);
            }
        }
        return index;

    }
}
