package Fase2.Arbres.redistribucioMentors;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Heroi;
import Fase2.Arbres.Node;

import java.util.List;

public class redistribucioMentors {
    public static  Arbre redistribuirMentors(List<Heroi> herois) {


        Node nodeArrel = new Node(herois.get(0).getId(), herois.get(0).getNom(), herois.get(0).getPoder(), herois.get(0).getCasa(), herois.get(0).getMaterial());
        Arbre arbre = new Arbre(nodeArrel);

        for (int i = 1; i < herois.size(); i++) {
            Node node = new Node(herois.get(i).getId(), herois.get(i).getNom(), herois.get(i).getPoder(), herois.get(i).getCasa(), herois.get(i).getMaterial());
            arbre.inserirNode(node, node.getPoder());
            arbre.setArrel(determinarTipoDesequilibrioRecursivo(arbre.getArrel()));

        }

        //Hacemos la redistribucion de los mentores ,tambien tenemos que contemplar si la raiz tambien puede estar desequilibrada,el balanceo lo hacmoes de abajo a arriba

        int  nivell = Math.max(obtenerAltura(arbre.getArrel().getEsquerra()), obtenerAltura(arbre.getArrel().getDreta()));
        System.out.println("Els mentors han estat redistribuïts. Ara l'abre té "  + nivell + " nivells.");

        return arbre;
    }

    private static int obtenerAltura(Node nodo) {
        if (nodo == null) {
            return 0;
        }

        return 1 + Math.max(obtenerAltura(nodo.getEsquerra()), obtenerAltura(nodo.getDreta()));
    }

    private  static int  calcularFactorEquilibrio(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.getDreta()) - obtenerAltura(nodo.getEsquerra());
    }

    public static Node  determinarTipoDesequilibrioRecursivo(Node node) {
        Node aux = node;
        if (node == null) {
            return null;
        }
        //Empezamos balanceando los nodos hijo,es decir vamos hacia abajo del todo para que asi cuando se cambie el padre no afecta al comportamiento de los hijos
        node.setDreta(determinarTipoDesequilibrioRecursivo(node.getDreta()));
        node.setEsquerra(determinarTipoDesequilibrioRecursivo(node.getEsquerra()));

        int FE = calcularFactorEquilibrio(node);

        // Left-Left
        if (FE <= -2 && calcularFactorEquilibrio(node.getEsquerra()) <= 0) {
             return rotacioDreta(node);
        }

        // Left-Right (LR)
        if (FE <= -2 && calcularFactorEquilibrio(node.getEsquerra()) > 0) {
            node.setEsquerra(rotacioEsquerra(node.getEsquerra()));//Primero modificamos al hijo para que asi se convierta en un caso de Left left,como no puedo devolver en este caso directamente hago un set
            return rotacioDreta(node);
        }

        // Right-Right (RR)
        if (FE >= 2 && calcularFactorEquilibrio(node.getDreta()) >= 0) {
            return rotacioEsquerra(node);
        }

        // Right-Left (RL)
        if (FE >= 2 && calcularFactorEquilibrio(node.getDreta()) < 0) {
            node.setDreta(rotacioDreta(node.getDreta()));
            return rotacioEsquerra(node);
        }

        return node;
    }

    private static  Node rotacioDreta(Node node) {
        if (node == null || node.getEsquerra() == null) {
            return node ;  //Si no hay hijo izquierdo no se puede hacer la rotacion ,aunque esto no tendria que pasar
        }
        Node aux = node.getEsquerra();
        Node T2 = aux.getDreta();

        aux.setDreta(node);
        node.setEsquerra(T2);
        return aux; //Devolvemos el nodo que ahora es el nuevo "padre" por asi decirlo

    }

    private static Node  rotacioEsquerra(Node node) {
        if (node == null || node.getDreta() == null) {
            return node;  //Si no hay hijo detecho no se puede hacer la rotacion ,aunque esto no tendria que pasar
        }
        Node aux = node.getDreta();
        Node T2 = aux.getEsquerra();

        aux.setEsquerra(node);
        node.setDreta(T2);
        return aux; //Devolvemos el nodo que ahora es el nuevo "padre" por asi decirlo
    }

    public static void mostrarFactorsEquilibri(Node node) {
        if (node == null) {
            return; // Caso base: si el nodo es nulo, no hacemos nada
        }

        int FE = calcularFactorEquilibrio(node);

        System.out.println("Node " + node.getId() + " → Factor d'Equilibri: " + FE);

        int altura = obtenerAltura(node);
        System.out.println("Node " + node.getId() + " → Altura: " + altura);

        mostrarFactorsEquilibri(node.getEsquerra());
        mostrarFactorsEquilibri(node.getDreta());
    }
}