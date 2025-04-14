package Fase1.Grafs.ExAssentamentRegional;

import Fase1.Grafs.Lloc;
import java.util.List;

public class Regio {
    private int tamany;
    private List<Lloc> llocs;

    public Regio(int tamany, List<Lloc> llocs) {
        this.tamany = tamany;
        this.llocs = llocs;

    }
    public int getTamany() {
        return llocs.size();
    }

    public List<Lloc> getLlocs() {
        return llocs;
    }

    public void setTamany(int tamany) {
        this.tamany = tamany;
    }

    public void addLloc(Lloc lloc) {
        this.llocs.add(lloc);
    }
}
