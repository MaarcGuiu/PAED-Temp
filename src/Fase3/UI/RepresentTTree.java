package Fase3.UI;

import Fase3.RTree.Jugador;

import java.util.List;

public class RepresentTTree {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 20;

    public static void representGraficament(List<Jugador> jugadors) {
        char[][] grid = new char[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = '.';
            }
        }

        int maxX = jugadors.stream().mapToInt(Jugador::getBattlesDone).max().orElse(1);
        int maxY = jugadors.stream().mapToInt(Jugador::getBattlesWon).max().orElse(1);

        for (Jugador j : jugadors) {
            int x = (int) ((j.getBattlesDone() / (double) maxX) * (WIDTH - 1));
            int y = HEIGHT - 1 - (int) ((j.getBattlesWon() / (double) maxY) * (HEIGHT - 1));
            grid[y][x] = 'J';
        }

        for (char[] row : grid) {
            System.out.println(new String(row));
        }
    }
}
