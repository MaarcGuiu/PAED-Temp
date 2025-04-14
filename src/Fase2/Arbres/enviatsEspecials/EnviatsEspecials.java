package Fase2.Arbres.enviatsEspecials;

import Fase2.Arbres.Node;

import java.util.List;

public class EnviatsEspecials {

    public void selHeroisMissioRec(Node node, List<Node> missioHerois) {
        if (node == null) {
            return;
        }
        int altura = calcularAltura(node);
        selHeroisMissioRec(node, missioHerois, 0, altura % 2 != 0);
    }

    private void selHeroisMissioRec(Node node, List<Node> missioHerois, int nivell, boolean enviarRaiz) {
        if (node == null) {
            return;
        }

        boolean enviar = (nivell % 2 != 0) == enviarRaiz;

        if (enviar) {
            node.setEnMissio(true);
            missioHerois.add(node);
        }

        selHeroisMissioRec(node.getEsquerra(), missioHerois, nivell + 1, enviarRaiz);
        selHeroisMissioRec(node.getDreta(), missioHerois, nivell + 1, enviarRaiz);
    }

    private int calcularAltura(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calcularAltura(node.getEsquerra()), calcularAltura(node.getDreta()));
    }


    public void resetMissioStatRec(Node node) {
        if (node == null) {
            return;
        }
        node.setEnMissio(false);
        resetMissioStatRec(node.getEsquerra());
        resetMissioStatRec(node.getDreta());
    }
}
