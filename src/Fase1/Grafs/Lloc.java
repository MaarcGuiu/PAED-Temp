package Fase1.Grafs;

public class Lloc {
    private int id;
    private String nom;
    private String faccio;
    private boolean visitat;

    public Lloc(int id, String nom, String faccio) {
        this.id = id;
        this.nom = nom;
        this.faccio = faccio;
        this.visitat = false;

    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LlocInterest{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", faccio='" + faccio + '\'' +
                '}';
    }

    public String getFaccio() {
        return faccio;
    }

    public void setVisitat(boolean visitat) {
        this.visitat = visitat;
    }

    public boolean getVisitat() {
        return visitat;
    }

    public String  getNom() {
        return nom;
    }
}
