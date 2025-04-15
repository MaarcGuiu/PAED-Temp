package Fase3.RTree;

import java.util.Date;

/**
 * Representa a un jugador con sus estadísticas y datos personales.
 */
public class Jugador extends Figura {

    private int id;
    private String name;
    private Date registrationDate;
    private int battlesDone;
    private int battlesWon;
    private boolean pvp;
    private String color;
    private NodoRTree padre;


    public Jugador(int id, String name, Date registrationDate, int battlesDone, int battlesWon, boolean pvp, String color) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.battlesDone = battlesDone;
        this.battlesWon = battlesWon;
        this.pvp = pvp;
        this.color = color;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getRegistrationDate() {
        return registrationDate;
    }


    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


    public int getBattlesDone() {
        return battlesDone;
    }


    public void setBattlesDone(int battlesDone) {
        this.battlesDone = battlesDone;
    }


    public int getBattlesWon() {
        return battlesWon;
    }


    public void setBattlesWon(int battlesWon) {
        this.battlesWon = battlesWon;
    }


    public boolean isPvp() {
        return pvp;
    }


    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public Rectangulo getMBR() {
        return Rectangulo.RecJugador(this);
    }
    public void setPadre(NodoRTree padre) {
        this.padre = padre;
    }
    public NodoRTree getPadre() {
        return padre;
    }
}
