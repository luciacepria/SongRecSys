package Interfaces;

import org.example.Table;

import java.util.List;

public interface Algorithm<T, R> {
    void train(T datos) throws Exception;
    R estimate(List<Double> datos) throws Exception;
}
