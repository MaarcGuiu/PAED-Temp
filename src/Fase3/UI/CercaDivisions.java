package Fase3.UI;

import Fase3.RTree.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CercaDivisions {

    public static void cerca(RTree arbre) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdueix el mínim de batalles realitzades: ");
        int minHechas = scanner.nextInt();
        System.out.print("Introdueix el mínim de batalles guanyades: ");
        int minGanadas = scanner.nextInt();

        System.out.print("Introdueix el màxim de batalles realitzades: ");
        int maxHechas = scanner.nextInt();
        System.out.print("Introdueix el màxim de batalles guanyades: ");
        int maxGanadas = scanner.nextInt();

        ArrayList<Jugador> resultats = cercarJugadors(arbre, minHechas, maxHechas, minGanadas, maxGanadas);

        if(resultats.isEmpty()) {
            System.out.println("\nNo s’han trobat jugadors en aquest rang!\n");
        }
        else {
            System.out.println("\nS’han trobat " + resultats.size() + " jugadors en aquest rang!");
            for (Jugador j : resultats) {
                if(j.isPvp())System.out.println("\t* Nom: " + j.getName() + " ( " + j.getId() + "- " + j.getBattlesWon() + "/" + j.getBattlesDone() + " ) - PvP activat");
                else System.out.println("\t* Nom: " + j.getName() + " ( " + j.getId() + "- " + j.getBattlesWon() + "/" + j.getBattlesDone() + " ) - PvP desactivat");
            }
            System.out.println("\n");
        }
    }

    public static ArrayList<Jugador> cercarJugadors(RTree arbre, int minHechas, int maxHechas, int minGanadas, int maxGanadas) {
        ArrayList<Jugador> resultats = new ArrayList<>();
        Rectangulo zonaConsulta = new Rectangulo(minHechas, maxHechas, minGanadas, maxGanadas);
        cercarRecursiu(arbre.getRaiz(), zonaConsulta, resultats);
        return resultats;
    }

    private static void cercarRecursiu(NodoRTree node, Rectangulo zona, ArrayList<Jugador> resultat) {
        for (Figura f : node.getHijos()) {
            Rectangulo mbr = f.getMBR();

            if (datos(zona, mbr)) {
                if (f instanceof Jugador jugador) {
                    if (zona.EstaDentro(jugador.getBattlesDone(), jugador.getBattlesWon())) {
                        resultat.add(jugador);
                    }
                } else if (f instanceof NodoRTree fill) {
                    cercarRecursiu(fill, zona, resultat);
                }
            }
        }
    }

    private static boolean datos(Rectangulo a, Rectangulo b) {
        return !(a.getMaxHechas() < b.getMinHechas() || a.getMinHechas() > b.getMaxHechas() ||
                a.getMaxGanadas() < b.getMinGanadas() || a.getMinGanadas() > b.getMaxGanadas());
    }

}
