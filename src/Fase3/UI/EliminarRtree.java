package Fase3.UI;
import Fase3.RTree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class EliminarRtree {



    public static void eliminarJugador(RTree rTree) {


        System.out.println("📋 Estado del RTree antes de eliminar:");
        imprimirRTree(rTree.getRaiz(), "");
        System.out.println("Introduce el ID del jugador a eliminar:");
        Scanner input = new Scanner(System.in);
        int id =input.nextInt();

        Jugador jugador = BuscarJugadorRecursivo(id, rTree.getRaiz());
        NodoRTree padre = jugador.getPadre();
        padre.removeHijo(jugador);
        EliminarRecursivo(padre,rTree);

        System.out.println("Jugador "+ jugador.getId()+" eliminado y reinsertado correctamente.");
        System.out.println("\n📋 Estado del RTree después de eliminar y reinsertar:");
        imprimirRTree(rTree.getRaiz(), "");

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
            System.out.printf("⚠️ Nodo con %d hijos. Mínimo permitido: %d. Eliminando nodo...\n",
                    nodo.getNumHijos(), NodoRTree.ENTRADAS_MINIMAS);

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
    public static void imprimirRTree(NodoRTree nodo, String indent) {
        Rectangulo mbr = nodo.getMBR();
        System.out.printf("%s📦 Nodo MBR: [Hechas: %d-%d | Ganadas: %d-%d] (%d hijos)\n",
                indent,
                mbr.getMinHechas(), mbr.getMaxHechas(),
                mbr.getMinGanadas(), mbr.getMaxGanadas(),
                nodo.getNumHijos());

        for (Figura hijo : nodo.getHijos()) {
            if (hijo instanceof Jugador) {
                Jugador j = (Jugador) hijo;
                System.out.printf("%s  👤 Jugador ID: %d | Nombre: %s | Hechas: %d | Ganadas: %d\n",
                        indent, j.getId(), j.getName(), j.getBattlesDone(), j.getBattlesWon());
            } else if (hijo instanceof NodoRTree) {
                imprimirRTree((NodoRTree) hijo, indent + "  ");
            }
        }
    }


}
