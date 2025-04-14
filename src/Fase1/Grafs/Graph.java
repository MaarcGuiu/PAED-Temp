package Fase1.Grafs;

import java.util.List;

public class Graph {
    private List<Lloc> llocs;
    private List<Ruta> rutes;

    public Graph(List<Lloc> llocs, List<Ruta> rutes) {
        this.llocs = llocs;
        this.rutes = rutes;
    }

    public List<Ruta> getRutes() {
        return rutes;
    }

    public List<Lloc> getLlocs() {
        return llocs;
    }
}