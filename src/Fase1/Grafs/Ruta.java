package Fase1.Grafs;

public class Ruta {
    private Lloc llocA;
    private Lloc llocB;
    private String clima;
    private double distancia;
    private int tempsIdeal;
    private int tempsAdvers;

    public Ruta(Lloc llocA, Lloc llocB, String clima, double distancia, int tempsIdeal, int tempsAdvers) {
        this.llocA = llocA;
        this.llocB = llocB;
        this.clima = clima;
        this.distancia = distancia;
        this.tempsIdeal = tempsIdeal;
        this.tempsAdvers = tempsAdvers;
    }

    public Lloc getLlocA() {
        return llocA;
    }

    public Lloc getLlocB() {
        return llocB;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "llocA=" + llocA +
                ", llocB=" + llocB +
                ", clima='" + clima + '\'' +
                ", distancia=" + distancia +
                ", tempsIdeal=" + tempsIdeal +
                ", tempsAdvers=" + tempsAdvers +
                '}';
    }

    public double getDistancia() {
        return distancia;
    }

    public int getTempsIdeal() {
        return tempsIdeal;
    }

    public int getTempsAdvers() {
        return tempsAdvers;
    }

    public String getClima() {
        return clima;
    }
}
