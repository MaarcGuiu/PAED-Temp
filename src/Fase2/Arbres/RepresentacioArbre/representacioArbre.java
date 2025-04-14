package Fase2.Arbres.RepresentacioArbre;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Node;

public class representacioArbre {

    public static void representarArbre(Arbre arbre) {
        if (arbre.getArrel() == null) {
            System.out.println("L'arbre està buit.");
            return;
        }
        System.out.println("\nRepresentació de l'arbre d'herois (ordenat per poder):\n");

        // Printar el subárbol izquierdo
        if (arbre.getArrel().getEsquerra() != null) {
            representarArbreRec(arbre.getArrel().getEsquerra(), "", false, arbre.getArrel().getDreta() != null);
        }

        // Printar la raíz con color y *
        String colorANSI = obtenirColorANSI(arbre.getArrel().getMaterial());
        System.out.println("* " + colorANSI + arbre.getArrel().getNom() + " (" + arbre.getArrel().getId() + ", " + arbre.getArrel().getCasa() + "): " + arbre.getArrel().getPoder() + "\u001B[0m");

        // Printar el subárbol derecho
        if (arbre.getArrel().getDreta() != null) {
            representarArbreRec(arbre.getArrel().getDreta(), "", true, arbre.getArrel().getEsquerra() != null);
        }
    }


    private static void representarArbreRec(Node node, String prefix, boolean isRight, boolean hasSibling) {
        if (node == null) return;

        // Hacer subárbol izquierdo
        if (node.getEsquerra() != null) {
            representarArbreRec(node.getEsquerra(), prefix + (!hasSibling ? (node.getDreta() == null) ? "│   " : "    " : "│   "), false, node.getDreta() != null);
        }

        String colorANSI = obtenirColorANSI(node.getMaterial());

        if (node.enMissio()) {
            colorANSI = "\033[0;31m";
        }

        String prefixNode = "|--- ";
        String infoHeroi = node.getNom() + " (" + node.getId() + ", " + node.getCasa() + "): " + node.getPoder();

        // Imprimir el nodo con color
        System.out.println(prefix + prefixNode + colorANSI + infoHeroi + "\u001B[0m");

        // Hacer subárbol derecho
        if (node.getDreta() != null) {
            representarArbreRec(node.getDreta(), prefix + (!hasSibling ? (node.getEsquerra() == null) ? "    " : "│   " : "    "), true, node.getEsquerra() != null);
        }
    }


    private static String obtenirColorANSI(String material) {
        if (material.startsWith("#") && material.length() == 7) {
            return convertirHexAAnsi(material);
        }

        switch (material.toLowerCase()) {
            case "or":
                return "\u001B[38;2;255;215;0m"; // Dorado
            case "plata":
                return "\u001B[38;2;192;192;192m"; // Plateado
            case "bronze":
                return "\u001B[38;2;205;127;50m"; // Bronce
            case "ferro":
                return "\u001B[38;2;169;169;169m"; // Gris metálico
            case "acer":
                return "\u001B[38;2;176;196;222m"; // Azul acero
            case "vermell":
                return "\u001B[38;2;220;20;60m"; // Rojo para casos especiales
            default:
                return "\u001B[38;2;255;255;255m"; // Blanco por defecto
        }
    }

    private static String convertirHexAAnsi(String hex) {
        try {
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        } catch (Exception e) {
            return "\u001B[38;2;255;255;255m"; // Si falla, usar blanco por defecto
        }
    }


}