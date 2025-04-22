package Fase3.UI;

import Fase3.RTree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EliminarRtree {

    public static void eliminarJugador(RTree rTree) {

        System.out.println("Introduce el ID del jugador a eliminar:");
        Scanner input = new Scanner(System.in);
        int id =input.nextInt();

        Jugador jugador = BuscarJugadorRecursivo(id, rTree.getRaiz());
        NodoRTree padre = jugador.getPadre();
        padre.removeHijo(jugador);
        EliminarRecursivo(padre,rTree);

        System.out.println("Jugador "+ jugador.getId()+" eliminado y reinsertado correctamente.");


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

    public static void EliminarRecursivo(NodoRTree nodo, RTree rTree) {
        if (nodo == null || nodo.getPadre() == null){
            return;
        }

        if (nodo.getNumHijos() < NodoRTree.ENTRADAS_MINIMAS) {

            NodoRTree padre = nodo.getPadre();

            List <Figura> hijos = new ArrayList<>(nodo.getHijos());
            for (Figura hijo : hijos) {
                nodo.removeHijo(hijo);
                if (hijo instanceof Jugador) {
                    rTree.insertar((Jugador) hijo);
                }
            }
            padre.removeHijo(nodo);
            EliminarRecursivo(nodo.getPadre(), rTree);
        } else {
            EliminarRecursivo(nodo.getPadre(), rTree);
        }
    }




}
