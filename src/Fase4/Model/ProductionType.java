package Fase4.Model;

/**
 * Enumera los posibles tipos de producciones multimedia,
 * con nombre legible y método para parsear desde texto.
 */
public enum ProductionType {
    FILM("Película"),
    BOOK("Libro"),
    GAME("Juego"),
    VIDEOGAME("Videojuego"),
    SERIES("Serie"),
    THEATRE("Teatro");

    private final String displayName;

    ProductionType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Nombre legible para mostrar al usuario.
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Convierte un texto en el valor correspondiente de ProductionType.
     * Acepta tanto el nombre del enum (case-insensitive) como el nombre legible.
     *
     * @param text valor a convertir
     * @return ProductionType correspondiente
     * @throws IllegalArgumentException si no existe dicho tipo
     */
    public static ProductionType fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Tipo de producción nulo");
        }
        for (ProductionType type : values()) {
            if (type.name().equalsIgnoreCase(text.trim()) ||
                    type.displayName.equalsIgnoreCase(text.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de producción desconocido: " + text);
    }
}