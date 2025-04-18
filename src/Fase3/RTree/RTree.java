package Fase3.RTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * RTree: Representa un árbol R.
 *
 * <p>
 * Paso 1 (Descenso): Se recorre el árbol desde la raíz hasta la hoja en la que se insertará el nuevo elemento.
 * Se selecciona el hijo cuyo MBR ya contenga el elemento o, en su defecto, el que requiera el menor incremento.
 * </p>
 *
 * <p>
 * Paso 2 (División): Si la hoja supera el número máximo de entradas, se divide en dos nodos.
 * Se seleccionan las dos entradas (semillas) más separadas y se reinserta el resto de las entradas
 * en el grupo que minimice el incremento de perímetro. Si la división provoca que el nodo padre
 * exceda la capacidad, se repite el proceso de forma recursiva.
 * </p>
 */
public class RTree {
    private NodoRTree raiz;

    /**
     * Constructor del RTree.
     *
     * @param entradasMaximas Máximo número de entradas por nodo.
     * @param entradasMinimas Mínimo número de entradas por nodo.
     */
    public RTree(int entradasMaximas, int entradasMinimas) {
        NodoRTree.ENTRADAS_MAXIMAS = entradasMaximas;
        NodoRTree.ENTRADAS_MINIMAS = entradasMinimas;
        raiz = new NodoRTree(entradasMaximas, entradasMinimas, null);
    }

    /**
     * Devuelve la raíz del árbol.
     *
     * @return la raíz del RTree.
     */
    public NodoRTree getRaiz() {
        return raiz;
    }

    /**
     * Inserta una figura en el RTree.
     * <p>
     * Paso 1: Se realiza el descenso hasta la hoja adecuada.
     * Paso 2: Si se supera la capacidad, se divide el nodo.
     * </p>
     *
     * @param figura La figura a insertar.
     */
    public void insertar(Figura figura) {
        insertar(raiz, figura);
    }

    /**
     * Método recursivo para insertar una figura.
     *
     * @param nodo   Nodo actual donde insertar.
     * @param figura Figura a insertar.
     */
    private void insertar(NodoRTree nodo, Figura figura) {
        if (nodo.esHoja()) {
            nodo.addHijo(figura);
            if (nodo.getNumHijos() > NodoRTree.ENTRADAS_MAXIMAS) {
                splitNode(nodo);
            }
        } else {
            NodoRTree mejorHijo = null;
            double menorIncremento = Double.MAX_VALUE;
            for (Figura f : nodo.getHijos()) {
                if (f instanceof NodoRTree) {
                    NodoRTree hijo = (NodoRTree) f;
                    if (hijo.getMBR() != null && contains(hijo.getMBR(), figura.getMBR())) {
                        mejorHijo = hijo;
                        break;
                    } else {
                        Rectangulo hijoMBR = hijo.getMBR();
                        Rectangulo figuraMBR = figura.getMBR();

                        if (hijoMBR == null || figuraMBR == null) {
                            continue;
                        }

                        double incremento = hijo.getMBR().incrementoPerimetro(figura.getMBR());
                        if (incremento < menorIncremento) {
                            menorIncremento = incremento;
                            mejorHijo = hijo;
                        }
                    }
                }
            }
            if (mejorHijo != null) {
                insertar(mejorHijo, figura);
                actualizarMBRHaciaArriba(nodo);
            }
        }
    }

    /**
     * Comprueba si el MBR 'contenedor' engloba completamente al MBR 'contenido'.
     *
     * @param contenedor MBR contenedor.
     * @param contenido  MBR que se quiere comprobar.
     * @return true si contiene, false en caso contrario.
     */
    private boolean contains(Rectangulo contenedor, Rectangulo contenido) {
        return (contenido.getMinHechas() >= contenedor.getMinHechas() &&
                contenido.getMaxHechas() <= contenedor.getMaxHechas() &&
                contenido.getMinGanadas() >= contenedor.getMinGanadas() &&
                contenido.getMaxGanadas() <= contenedor.getMaxGanadas());
    }

    /**
     * Realiza la división de un nodo que excede la capacidad.
     * <p>
     * Paso 1: Se llama al método split() del nodo para dividirlo en dos.
     * Paso 2: Si el nodo es la raíz, se crea una nueva raíz; si no, se reemplaza en el padre.
     * </p>
     *
     * @param nodo Nodo a dividir.
     */
    private void splitNode(NodoRTree nodo) {
        NodoRTree[] grupos = nodo.split();
        NodoRTree g1 = grupos[0];
        NodoRTree g2 = grupos[1];
        int min = NodoRTree.ENTRADAS_MINIMAS;
        if (g1.getNumHijos() < min) {
            balanceMinEntries(g2, g1, min - g1.getNumHijos());
        } else if (g2.getNumHijos() < min) {
            balanceMinEntries(g1, g2, min - g2.getNumHijos());
        }
        if (nodo.getPadre() == null) {
            NodoRTree nuevaRaiz = new NodoRTree(NodoRTree.ENTRADAS_MAXIMAS, NodoRTree.ENTRADAS_MINIMAS, null);
            nuevaRaiz.addHijo(g1);
            nuevaRaiz.addHijo(g2);
            raiz = nuevaRaiz;
        } else {
            NodoRTree padre = nodo.getPadre();
            padre.getHijos().remove(nodo);
            padre.addHijo(g1);
            padre.addHijo(g2);
            if (padre.getNumHijos() > NodoRTree.ENTRADAS_MAXIMAS) {
                splitNode(padre);
            }
        }
    }

    private void balanceMinEntries(NodoRTree donante, NodoRTree receptor, int needed) {
        List<Figura> candidatos = new ArrayList<>(donante.getHijos());
        candidatos.sort(Comparator.comparingDouble(f -> receptor.getMBR().incrementoPerimetro(f.getMBR())));
        for (int i = 0; i < needed && i < candidatos.size(); i++) {
            Figura f = candidatos.get(i);
            donante.removeHijo(f);
            receptor.addHijo(f);
        }
    }

    private void actualizarMBRHaciaArriba(NodoRTree nodo) {
        while (nodo != null) {
            nodo.recalcularMBR();
            nodo = nodo.getPadre();
        }
    }
}