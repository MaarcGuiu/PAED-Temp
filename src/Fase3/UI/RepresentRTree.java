package Fase3.UI;

import Fase3.RTree.Figura;
import Fase3.RTree.Jugador;
import Fase3.RTree.NodoRTree;
import Fase3.RTree.Rectangulo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RepresentRTree extends JFrame {

    public static void representGraficament(NodoRTree root) {
        RepresentRTree frame = new RepresentRTree();
        frame.setTitle("Representació gràfica R-Tree");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new RTreePanel(root));
        frame.setVisible(true);
    }

    static class RTreePanel extends JPanel {
        private final NodoRTree arrel;
        private static final int PADDING = 50;
        private double escalaX, escalaY;
        private int maxBattlesDone = 1;
        private int maxBattlesWon = 1;

        public RTreePanel(NodoRTree root) {
            this.arrel = root;
            calcularEscales(root);
        }

        private void calcularEscales(NodoRTree node) {
            Rectangulo mbr = node.getMBR();
            maxBattlesDone = Math.max(maxBattlesDone, mbr.getMaxHechas());
            maxBattlesWon = Math.max(maxBattlesWon, mbr.getMaxGanadas());

            for (Figura hijo : node.getHijos()) {
                if (hijo instanceof NodoRTree) {
                    calcularEscales((NodoRTree) hijo);
                }
            }
        }

        private int escalarX(int x) {
            return PADDING + (int) (x * escalaX);
        }

        private int escalarY(int y) {
            return getHeight() - PADDING - (int) (y * escalaY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            escalaX = (getWidth() - 2.0 * PADDING) / maxBattlesDone;
            escalaY = (getHeight() - 2.0 * PADDING) / maxBattlesWon;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            dibujarNodo(g2d, arrel);
        }

        private void dibujarNodo(Graphics2D g2d, NodoRTree node) {
            Rectangulo rect = node.getMBR();
            int x = escalarX(rect.getMinHechas());
            int y = escalarY(rect.getMaxGanadas());
            int w = escalarX(rect.getMaxHechas()) - x;
            int h = escalarY(rect.getMinGanadas()) - y;

            Stroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(dashed);
            g2d.drawRect(x, y, w, h);

            for (Figura hijo : node.getHijos()) {
                if (hijo instanceof Jugador) {
                    dibujarJugador(g2d, (Jugador) hijo);
                } else if (hijo instanceof NodoRTree) {
                    dibujarNodo(g2d, (NodoRTree) hijo);
                }
            }
        }

        private void dibujarJugador(Graphics2D g2d, Jugador jugador) {
            int x = escalarX(jugador.getBattlesDone());
            int y = escalarY(jugador.getBattlesWon());

            LocalDate registrationDate = jugador.getRegistrationDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            long antiguedad = ChronoUnit.YEARS.between(registrationDate, LocalDate.now());
            int tamaño = 5 + (int) antiguedad;

            Color color = Color.decode(jugador.getColor());

            g2d.setColor(color);

            if (jugador.isPvp()) {
                g2d.fillRect(x - tamaño / 2, y - tamaño / 2, tamaño, tamaño);
            } else {
                g2d.fillOval(x - tamaño / 2, y - tamaño / 2, tamaño, tamaño);
            }
        }
    }
}
