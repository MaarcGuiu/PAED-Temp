package Fase2.Arbres.GeneracióDePatrulles;

import java.util.PriorityQueue;
import java.util.Scanner;
import Fase2.Arbres.Arbre;
import Fase2.Arbres.Node;
import java.util.Comparator;

public class GeneracioDePatrulles {
    private static PriorityQueue<Node> herois = new PriorityQueue<>(Comparator.comparing(Node::getPoder));

    public static void generacioDePatrulles(Arbre arbre) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdueix el poder mínim: ");
        double minim = scanner.nextDouble();

        System.out.print("Introdueix el poder màxim: ");
        double maxim = scanner.nextDouble();

        herois.clear();
        searchHeroisEnRang(arbre.getArrel(), minim, maxim);

        if (herois.isEmpty()) {
            System.out.println("\nNo s'ha trobat cap heroi dins del rang.\n");
        } else {
            System.out.println("S'han trobat " + herois.size() + " per la patrulla:\n");
            while (!herois.isEmpty()) {
                Node heroi = herois.poll();
                System.out.printf("\t* %s (%d, %s): %.2f%n",
                        heroi.getNom(),
                        heroi.getId(),
                        heroi.getCasa(),
                        heroi.getPoder());            }
            System.out.println();
        }
    }

    private static void searchHeroisEnRang(Node actual, double minim, double maxim) {
        if (actual == null) return;

        // Buscar en el subárbol izquierdo si puede haber valores en el rango
        if (actual.getPoder() > minim) {
            searchHeroisEnRang(actual.getEsquerra(), minim, maxim);
        }

        // Si el nodo actual está en el rango, lo agregamos
        if (actual.getPoder() >= minim && actual.getPoder() <= maxim) {
            herois.add(actual);
        }

        // Buscar en el subárbol derecho si puede haber valores en el rango
        if (actual.getPoder() < maxim) {
            searchHeroisEnRang(actual.getDreta(), minim, maxim);
        }
    }
}