package Fase4.Taules;

public class HashMap {
    public static class Entrada {
        String key;
        Production value;
        boolean isFree = true;

        Entrada(String key, Production value) {
            this.key = key;
            this.value = value;
            this.isFree = false;
        }

        public Production getValue() {
            return value;
        }

        public boolean getIsFree() {
            return isFree;
        }
    }

    private Entrada[] table;
    private int size;

    public HashMap(int size) {
        this.size = size;
        this.table = new Entrada[size];
    }

    /**
     * Calcula el índice de la clave para la tabla.
     * Si la clave es un entero válido, usa ese valor;
     * si no, utiliza su hashCode().
     * El resultado se convierte en no negativo para evitar errores de índice.
     *
     * @param key       clave a indexar
     * @param tableSize tamaño de la tabla para el módulo
     * @return índice en [0, tableSize)
     */
    private int getKeyIndex(String key, int tableSize) {
        int raw;
        try {
            raw = Integer.parseInt(key);
        } catch (NumberFormatException e) {
            raw = key.hashCode();
        }
        // Convierte Integer.MIN_VALUE a un valor no negativo también
        int nonNegative = raw & 0x7FFFFFFF;
        return nonNegative % tableSize;
    }

    public void put(String key, Production value) {
        int index = getKeyIndex(key, size);
        int originalIndex = index;

        while (table[index] != null && !table[index].isFree) {
            index = (index + 1) % size;
            if (index == originalIndex) {
                throw new RuntimeException("La taula està plena, no es pot inserir.");
            }
        }

        table[index] = new Entrada(key, value);
    }

    public Production get(String key) {
        int index = getKeyIndex(key, size);
        int originalIndex = index;

        while (table[index] != null) {
            if (!table[index].isFree && table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % size;
            if (index == originalIndex) break;
        }

        return null;
    }

    public Production remove(String key) {
        int index = getKeyIndex(key, size);
        int originalIndex = index;

        while (table[index] != null) {
            if (!table[index].isFree && table[index].key.equals(key)) {
                table[index].isFree = true;
                return table[index].value;
            }
            index = (index + 1) % size;
            if (index == originalIndex) break;
        }

        return null;
    }

    public Production[] getAll(String key) {
        Production[] result = new Production[size];
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (table[i] != null && !table[i].isFree && table[i].key.equals(key)) {
                result[count++] = table[i].value;
            }
        }

        Production[] finalResult = new Production[count];
        System.arraycopy(result, 0, finalResult, 0, count);
        return finalResult;
    }

    public int getSize() {
        return size;
    }
    public Entrada[] getAllProductions() {
        return table;
    }

}