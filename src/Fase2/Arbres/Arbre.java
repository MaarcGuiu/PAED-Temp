package Fase2.Arbres;

import Fase2.Arbres.enviatsEspecials.EnviatsEspecials;

import java.util.ArrayList;
import java.util.List;

public class Arbre {
    Node arrel;
    Node actual;
    int nivell;


    public Arbre(Node arrel) {
        this.arrel = arrel;
    }

    public void eliminarHeroi(int id) {
        arrel = eliminarHeroiRecursiu(arrel, id);
    }

    private Node eliminarHeroiRecursiu(Node actual, int id) {
        if (actual == null) {
            return null;
        }

        // Buscamos el nodo con el ID, recorriendo el árbol en su totalidad
        if (actual.getId() == id) {
            System.out.println("L'heroi " + actual.getNom() + " ha estat eliminat.");

            // Caso 1: Nodo sin hijos o con un solo hijo
            if (actual.getEsquerra() == null) {
                return actual.getDreta();
            } else if (actual.getDreta() == null) {
                return actual.getEsquerra();
            }

            // Caso 2: Nodo con dos hijos, buscamos el sucesor in-order
            Node successor = trobarSuccessor(actual.getDreta());
            actual.id = successor.id;
            actual.poder = successor.poder;
            actual.setDreta(eliminarHeroiRecursiu(actual.getDreta(), successor.getId()));
        } else {
            // si el ID no coincide, seguimos buscando en ambos subárboles
            actual.setEsquerra(eliminarHeroiRecursiu(actual.getEsquerra(), id));
            actual.setDreta(eliminarHeroiRecursiu(actual.getDreta(), id));
        }

        return actual;
    }

    // Encuentra el nodo con el menor poder en el subárbol derecho (sucesor in-order)
    private Node trobarSuccessor(Node node) {
        while (node.getEsquerra() != null) {
            node = node.getEsquerra();
        }
        return node;
    }


    public void inserirNode(Node node, double poder) {
        if (arrel == null) { //Si es la primera vez que se añade un nudo al arbol
            arrel = node;
            nivell = 1;
            return;
        }else{
            inserirNodeRecursiu(arrel,node, node.poder);
        }
    }

    public void inserirNodeRecursiu(Node actual,Node node , double  poder) {
        if (poder <= actual.poder) {
            if (actual.esquerra == null) {
                node.pare = actual;
                actual.esquerra = node;
            } else {
                inserirNodeRecursiu(actual.esquerra,node, poder);
            }

        } else {
            if (poder > actual.poder) {
                if (actual.dreta == null) {
                    node.pare = actual;
                    actual.dreta = node;
                } else {
                    inserirNodeRecursiu(actual.dreta,node,poder);
                }
            }
        }

    }

    public Node getArrel() {
        return arrel;
    }

    public int  getNivell() {
        int nivell=calcularNivell(arrel);
        return nivell;
    }

    private int calcularNivell(Node node) {
        if (node == null) {
            return 0;
        } else{
            int nivellEsquerra = calcularNivell(node.esquerra);
            int nivellDreta = calcularNivell(node.dreta);
            if (nivellEsquerra > nivellDreta) {
                return nivellEsquerra + 1;
            } else {
                return nivellDreta + 1;
            }
        }
    }

    public List<Node> selHeroisMissio() {
        EnviatsEspecials enviatsEspecials = new EnviatsEspecials();
        List<Node> missioHerois = new ArrayList<>();
        enviatsEspecials.selHeroisMissioRec(arrel, missioHerois);
        return missioHerois;
    }

    public void resetMissioStat() {
        EnviatsEspecials enviatsEspecials = new EnviatsEspecials();
        enviatsEspecials.resetMissioStatRec(arrel);
    }

    public void setArrel(Node arrel) {
        this.arrel = arrel;
    }
}


