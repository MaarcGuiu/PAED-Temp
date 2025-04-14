package Fase2.Arbres;

public class  Node {
    int id;
    String nom;
    double poder;
    String casa;
    String material;
    Node esquerra;
    Node dreta;
    Node pare;
    boolean enMissio;

    public Node(int id, String nom, double poder, String casa, String material) {
        this.id = id;
        this.nom = nom;
        this.poder = poder;
        this.casa = casa;
        this.material = material;
        this.esquerra = null;
        this.dreta = null;
        this.pare = null;
        this.enMissio = false;
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

    public Node getEsquerra() {
        return esquerra;
    }

    public Node getDreta() {
        return dreta;
    }

    public Node getPare() {
        return pare;
    }

    public void setEsquerra(Node esquerra) { this.esquerra = esquerra; }

    public void setDreta(Node dreta) { this.dreta = dreta; }

    public void setPare(Node pare) { this.pare = pare; }

    public boolean enMissio() { return enMissio; }

    public void setEnMissio(boolean enMissio) { this.enMissio = enMissio; }
}