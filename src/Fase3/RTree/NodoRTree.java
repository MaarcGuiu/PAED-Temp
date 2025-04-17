package Fase3.RTree;

import java.util.ArrayList;
import java.util.List;

public class NodoRTree extends Figura {
    private List<Figura> hijos;
    private NodoRTree padre;

    // Configuración de la capacidad de cada nodo
    public static int ENTRADAS_MAXIMAS;
    public static int ENTRADAS_MINIMAS;

    public NodoRTree(int entradasMaximas, int entradasMinimas, NodoRTree padre) {
        this.hijos = new ArrayList<>();
        this.padre = padre;
        ENTRADAS_MAXIMAS = entradasMaximas;
        ENTRADAS_MINIMAS = entradasMinimas;
    }

    public List<Figura> getHijos() {
        return hijos;
    }

    public void setHijos(List<Figura> hijos) {
        this.hijos = hijos;
    }

    public NodoRTree getPadre() {
        return padre;
    }

    public void setPadre(NodoRTree padre) {
        this.padre = padre;
    }

    public void addHijo(Figura hijo) {
        this.hijos.add(hijo);
        if (hijo instanceof NodoRTree) {
            ((NodoRTree) hijo).setPadre(this);
        } else if (hijo instanceof Jugador) {
            ((Jugador) hijo).setPadre(this);
        }
    }

    public void removeHijo(Figura hijo) {
        this.hijos.remove(hijo);
    }

    public int getNumHijos() {
        return hijos.size();
    }

    @Override
    public Rectangulo getMBR() {
        Rectangulo mbr = null;
        for (Figura hijo : hijos) {
            if (mbr == null) {
                mbr = hijo.getMBR().copiaHijo();
            } else {
                mbr.expandir(hijo.getMBR());
            }
        }
        return mbr;
    }

    // https://www.baeldung.com/java-instanceof
    public boolean esHoja() {
        for (Figura f : hijos) {
            if (f instanceof NodoRTree) {
                return false;
            }
        }
        return true;
    }

    /**
     * Realiza la división (split) del nodo cuando se supera el número máximo de entradas.
     *
     * <p>
     * Se seleccionan dos entradas (semillas) que estén más separadas entre sí para inicializar dos grupos.
     * Luego se asigna cada entrada restante al grupo cuyo MBR requiera un menor incremento de perímetro.
     * </p>
     *
     * @return un arreglo de dos nodos resultantes del split.
     */
    public NodoRTree[] split() {
        // Seleccionar las dos semillas más separadas
        int seed1 = -1, seed2 = -1;
        double maxDistance = -1;
        for (int i = 0; i < hijos.size() - 1; i++) {
            Rectangulo mbr1 = hijos.get(i).getMBR();
            for (int j = i + 1; j < hijos.size(); j++) {
                Rectangulo mbr2 = hijos.get(j).getMBR();
                double distance = distance(mbr1, mbr2);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    seed1 = i;
                    seed2 = j;
                }
            }
        }
        NodoRTree grupo1 = new NodoRTree(ENTRADAS_MAXIMAS, ENTRADAS_MINIMAS, this.padre);
        NodoRTree grupo2 = new NodoRTree(ENTRADAS_MAXIMAS, ENTRADAS_MINIMAS, this.padre);

        grupo1.addHijo(hijos.get(seed1));
        grupo2.addHijo(hijos.get(seed2));

        List<Figura> restantes = new ArrayList<>(hijos);
        if (seed1 > seed2) {
            restantes.remove(seed1);
            restantes.remove(seed2);
        } else {
            restantes.remove(seed2);
            restantes.remove(seed1);
        }

        for (Figura f : restantes) {
            Rectangulo mbrF = f.getMBR();
            double incGrupo1 = grupo1.getMBR().incrementoPerimetro(mbrF);
            double incGrupo2 = grupo2.getMBR().incrementoPerimetro(mbrF);
            if (incGrupo1 < incGrupo2) {
                grupo1.addHijo(f);
            } else if (incGrupo2 < incGrupo1) {
                grupo2.addHijo(f);
            } else {
                // Si hay empate, se asigna al grupo con menor número de entradas
                if (grupo1.getNumHijos() <= grupo2.getNumHijos()) {
                    grupo1.addHijo(f);
                } else {
                    grupo2.addHijo(f);
                }
            }
        }
        return new NodoRTree[] { grupo1, grupo2 };
    }

    /**
     * Calcula la distancia Euclidiana entre los centros de dos rectángulos.
     *
     * @param r1 primer rectángulo.
     * @param r2 segundo rectángulo.
     * @return la distancia entre los centros de r1 y r2.
     */
    private double distance(Rectangulo r1, Rectangulo r2) {
        double cx1 = (r1.getMinHechas() + r1.getMaxHechas()) / 2.0;
        double cy1 = (r1.getMinGanadas() + r1.getMaxGanadas()) / 2.0;
        double cx2 = (r2.getMinHechas() + r2.getMaxHechas()) / 2.0;
        double cy2 = (r2.getMinGanadas() + r2.getMaxGanadas()) / 2.0;
        return Math.sqrt(Math.pow(cx1 - cx2, 2) + Math.pow(cy1 - cy2, 2));
    }
}