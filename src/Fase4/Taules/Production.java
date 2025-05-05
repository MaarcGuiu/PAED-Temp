package Fase4.Taules;

import Fase4.Model.ProductionType;

import java.util.Objects;

/**
 * Representa una producción multimedia con nombre, tipo y facturación.
 */
public class Production {
    private String name;
    private ProductionType type;
    private int revenue;

    /**
     * Constructor de Production.
     *
     * @param name    Nombre único de la producción
     * @param type    Tipo de producción (FILM, BOOK, GAME, VIDEOGAME, SERIES, THEATRE)
     * @param revenue Ingresos facturados (en la unidad correspondiente)
     */
    public Production(String name, ProductionType type, int revenue) {
        this.name = name;
        this.type = type;
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionType getType() {
        return type;
    }

    public void setType(ProductionType type) {
        this.type = type;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Production)) return false;
        Production that = (Production) o;
        return revenue == that.revenue
                && Objects.equals(name, that.name)
                && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, revenue);
    }

    @Override
    public String toString() {
        return "Production{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", revenue=" + revenue +
                '}';
    }
}