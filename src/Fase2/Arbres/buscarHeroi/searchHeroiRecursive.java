package Fase2.Arbres.buscarHeroi;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Node;

public class searchHeroiRecursive {
    public Node searchHeroi(Arbre arbre, double poder) {
        Node heroi = searchHeroiRecursive(arbre.getArrel(), poder);
        if (heroi != null) {
            System.out.println("\tID: " + heroi.getId());
            System.out.println("\tNom: " + heroi.getNom());
            System.out.println("\tPoder: " + heroi.getPoder());
            System.out.println("\tCasa: " + heroi.getCasa());
            System.out.println("\tMaterial: " + heroi.getMaterial() + "\n");
        } else {
            System.out.println("No s'ha trobat cap heroi amb aquest poder.");
        }
        return heroi;
    }

    public static  Node searchHeroiRecursive(Node actual, double poder) {
        if (actual == null) {
            return null;
        }
        if (actual.getPoder() == poder) {
            return actual;
        } else if (poder < actual.getPoder()) {
            return searchHeroiRecursive(actual.getEsquerra(), poder);
        } else {
            return searchHeroiRecursive(actual.getDreta(), poder);
        }
    }
}
