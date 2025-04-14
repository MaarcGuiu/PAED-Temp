package Fase3.RTree;

import Fase3.RTree.Jugador;


public class Rectangulo {
    private int minHechas;
    private int maxHechas;
    private int minGanadas;
    private int maxGanadas;


    public Rectangulo(int minHechas, int maxHechas, int minGanadas, int maxGanadas) {
        this.minHechas = minHechas;
        this.maxHechas = maxHechas;
        this.minGanadas = minGanadas;
        this.maxGanadas = maxGanadas;
    }

    public boolean EstaDentro(int x, int y) {
        if(x >= minHechas && x <= maxHechas) {
           if(y >= minGanadas && y <= maxGanadas) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

    public int perimetro() {
        return 2 * ((maxHechas - minHechas) + (maxGanadas - minGanadas));
    }

    public double incrementoPerimetro(Rectangulo rNuevo) {
        int maxMinimoHechas = Math.min(this.minHechas, rNuevo.minHechas);
        int maxMaxHechas = Math.max(this.maxHechas, rNuevo.maxHechas);
        int maxMinGanadas = Math.min(this.minGanadas, rNuevo.minGanadas);
        int maxMaximasGanadas = Math.max(this.maxGanadas, rNuevo.maxGanadas);
        int nuevoPerimetro = 2 * ((maxMaxHechas - maxMinimoHechas) + (maxMaximasGanadas - maxMinGanadas));

        return nuevoPerimetro - this.perimetro();
    }

    public void expandir(Rectangulo rNuevo) {
        this.minHechas = Math.min(minHechas, rNuevo.minHechas);
        this.maxHechas = Math.max(maxHechas, rNuevo.maxHechas);
        this.minGanadas = Math.min(minGanadas, rNuevo.minGanadas);
        this.maxGanadas = Math.max(maxGanadas, rNuevo.maxGanadas);
    }


    public static Rectangulo RecJugador(Jugador jugador) {
        Rectangulo rectangulo = new Rectangulo(jugador.getBattlesDone(), jugador.getBattlesDone(), jugador.getBattlesWon(), jugador.getBattlesWon());
        return rectangulo;
    }
    public Rectangulo copiaHijo() {
        return new Rectangulo(this.minHechas, this.maxHechas, this.minGanadas, this.maxGanadas);
    }

    // Getters
    public int getMinHechas() {
        return minHechas;
    }

    public int getMaxHechas() {
        return maxHechas;
    }

    public int getMinGanadas() {
        return minGanadas;
    }

    public int getMaxGanadas() {
        return maxGanadas;
    }
}