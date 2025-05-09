package Fase3.UI;

import Fase3.RTree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EliminarRtree {
    private static int numRestructuracions = 0;

    public static void eliminarJugador(RTree rTree) {


        System.out.println("Introduce el ID del jugador a eliminar:");
        Scanner input = new Scanner(System.in);
        int id =input.nextInt();

        long startTime = System.nanoTime();

        Jugador jugador = BuscarJugadorRecursivo(id, rTree.getRaiz());
        if(jugador == null) {
            System.out.println("Jugador no encontrado.");
            return;
        }
        NodoRTree padre = jugador.getPadre();
        padre.removeHijo(jugador);
        EliminarRecursivo(padre,rTree);

        long endTime = System.nanoTime();

        System.out.println("Jugador "+ jugador.getId()+" eliminado y reinsertado correctamente.");
        System.out.println("\n📋 Estado del RTree después de eliminar y reinsertar:");

        long totalTime = endTime - startTime;

        //System.out.println("Tiempo total : " +totalTime + " ns");

        System.out.println("Reestructuraciones recursivas realizadas: " + numRestructuracions);


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

            numRestructuracions++;
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