package Fase3.UI;
import Fase3.RTree.Figura;
import Fase3.RTree.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Fase3.RTree.NodoRTree;
import Fase3.RTree.RTree;


public class EliminarRtree {


    public static void eliminarJugador(RTree RTree) {

        System.out.println("Introduce el ID del jugador a eliminar:");
        Scanner input = new Scanner(System.in);
        int id =input.nextInt();

        Jugador jugador = BuscarJugadorRecursivo(id, RTree.getRaiz());
        NodoRTree padre = jugador.getPadre();
        padre.removeHijo(jugador);
        List<Figura> insertar = new ArrayList<>();
        EliminarRecursivo(padre,insertar);
        for (Figura figura : insertar) {
            if (figura instanceof Jugador) {
                RTree.insertar((Jugador) figura);
            }
        }

    }
    public static Jugador BuscarJugadorRecursivo(int id,Figura nodo) {
        if (nodo instanceof Jugador) {
            Jugador jugador = (Jugador) nodo;
            if (jugador.getId() == id) {
                return jugador;
            }
        } else if (nodo instanceof NodoRTree) {
            NodoRTree nodoRTree = (NodoRTree) nodo;
            for (Figura hijo : nodoRTree.getHijos()) {
                Jugador jugadorEncontrado= BuscarJugadorRecursivo(id, hijo);
                if (jugadorEncontrado != null) {
                    return jugadorEncontrado;
                }
            }
        }
        return null;
    }
    public static void EliminarRecursivo(NodoRTree nodo, List<Figura> RtreeReinsertar) {
        if (nodo == null || nodo.getPadre() == null){
            return;
        }

        if (nodo.getNumHijos() < NodoRTree.ENTRADAS_MINIMAS) {
            NodoRTree padre = nodo.getPadre();

            List <Figura> hijos = new ArrayList<>(nodo.getHijos());
            for (Figura hijo : hijos) {
                padre.removeHijo(hijo);
                RtreeReinsertar.add(hijo);
            }
            padre.removeHijo(nodo);
            EliminarRecursivo(nodo.getPadre(), RtreeReinsertar);

        } else {
            EliminarRecursivo(nodo.getPadre(), RtreeReinsertar);
        }
    }

}
