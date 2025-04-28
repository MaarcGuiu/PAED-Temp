package Fase4.Taules;

import java.util.*;

/**
 * Representa una tabla en memoria de producciones multimedia.
 * Proporciona operaciones CRUD sin depender de ninguna interfaz externa.
 */
public class ProductionTable {
    private final Map<String, Production> rows = new HashMap<>();

    /**
     * Construye una tabla vacía.
     */
    public ProductionTable() {
    }

    /**
     * Construye la tabla inicializando con las producciones dadas.
     *
     * @param productions colección de producciones a cargar
     */
    public ProductionTable(Collection<Production> productions) {
        for (Production p : productions) {
            rows.put(p.getName(), p);
        }
    }

    /**
     * Devuelve todas las producciones almacenadas.
     *
     * @return lista de producciones
     */
    public List<Production> getAllProductions() {
        return new ArrayList<>(rows.values());
    }

    /**
     * Busca una producción por su nombre.
     *
     * @param name nombre de la producción
     * @return la producción, o null si no existe
     */
    public Production findProductionByName(String name) {
        return rows.get(name);
    }

    /**
     * Añade o reemplaza una producción.
     *
     * @param production producción a añadir
     */
    public void addProduction(Production production) {
        rows.put(production.getName(), production);
    }

    /**
     * Elimina la producción con el nombre dado.
     *
     * @param name nombre de la producción a eliminar
     */
    public void removeProduction(String name) {
        rows.remove(name);
    }
}