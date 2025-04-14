package Fase2.Arbres;

public class Heroi {
    int id;
    String nom;
    double poder;
    String casa;
    String material;

    public Heroi(int id, String nom, double poder, String casa, String material) {
        this.id = id;
        this.nom = nom;
        this.poder = poder;
        this.casa = casa;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPoder() {
        return poder;
    }

    public String getCasa() {
        return casa;
    }

    public String getMaterial() {
        return material;
    }
}
