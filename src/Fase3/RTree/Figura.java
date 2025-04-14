package Fase3.RTree;

public abstract class Figura {
    /**
     * Este método debe ser implementado por cada subclase para devolver el MBR
     * (Minimum Bounding Rectangle) de la figura. El MBR es el rectángulo que
     * contiene por completo a la figura.
     *
     * @return el MBR de la figura en forma de un objeto Rectangulo.
     */
    public abstract Rectangulo getMBR();
}