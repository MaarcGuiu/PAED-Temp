package Fase3.RTree;

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
        // Paso 1: Descenso hasta la hoja
        if (nodo.esHoja()) {
            nodo.addHijo(figura);
            // Paso 2: Si se supera la capacidad, dividir el nodo.
            if (nodo.getNumHijos() > NodoRTree.ENTRADAS_MAXIMAS) {
                splitNode(nodo);
            }
        } else {
            NodoRTree mejorHijo = null;
            double menorIncremento = Double.MAX_VALUE;
            // Se recorre cada hijo para elegir el más adecuado
            for (Figura f : nodo.getHijos()) {
                if (f instanceof NodoRTree) {
                    NodoRTree hijo = (NodoRTree) f;
                    // Si el MBR del hijo ya contiene la figura, se selecciona
                    if (hijo.getMBR() != null && contains(hijo.getMBR(), figura.getMBR())) {
                        mejorHijo = hijo;
                        break;
                    } else {
                        // Se calcula el incremento de perímetro que requiere añadir la figura
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
        NodoRTree[] splitted = nodo.split();
        if (nodo.getPadre() == null) {
            // Si es la raíz, se crea una nueva raíz con los dos nodos resultantes
            NodoRTree newRoot = new NodoRTree(NodoRTree.ENTRADAS_MAXIMAS, NodoRTree.ENTRADAS_MINIMAS, null);
            newRoot.addHijo(splitted[0]);
            newRoot.addHijo(splitted[1]);
            splitted[0].setPadre(newRoot);
            splitted[1].setPadre(newRoot);
            raiz = newRoot;
        } else {
            // Si tiene padre, se reemplaza el nodo en el padre
            NodoRTree padre = nodo.getPadre();
            padre.getHijos().remove(nodo);
            splitted[0].setPadre(padre);
            splitted[1].setPadre(padre);
            padre.addHijo(splitted[0]);
            padre.addHijo(splitted[1]);
            if (padre.getNumHijos() > NodoRTree.ENTRADAS_MAXIMAS) {
                splitNode(padre);
            }
        }
    }

}