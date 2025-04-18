package Fase3.UI;

import Fase3.RTree.Figura;
import Fase3.RTree.Jugador;
import Fase3.RTree.NodoRTree;
import Fase3.RTree.RTree;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la interacción por consola para insertar nuevos jugadores en el R-Tree.
 * Permite introducir los datos del jugador, validarlos y, asignándole automáticamente la fecha actual,
 * insertarlo en el árbol y, además, actualizar la lista de jugadores para futuras operaciones.
 */
public class MenuInteraccioRTree {

    private RTree arbol;
    private List<Jugador> jugadors; // Lista de jugadores ya insertados (para operaciones futuras)
    private Scanner scanner;

    /**
     * Constructor que recibe el RTree y la lista de jugadores.
     *
     * @param arbol    El RTree en el que se realizarán las inserciones.
     * @param jugadors Lista de jugadores a los que se añadirán los nuevos.
     */
    public MenuInteraccioRTree(RTree arbol, List<Jugador> jugadors) {
        this.arbol = arbol;
        this.jugadors = jugadors;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Solicita al usuario los datos necesarios para crear un nuevo jugador y lo inserta en el R-Tree.
     * Se valida cada campo y, en caso de error, se vuelve a pedir el valor correspondiente.
     * Además, añade el jugador a la lista (jugadors) para que pueda usarse posteriormente.
     */
    public void afegirJugador() {
        int id = leerEntero("Identificador: ");

        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();

        int batallesFetes = leerEntero("Batalles realitzades: ");
        int batallesGuanyades = leerEntero("Batalles guanyades: ");
        boolean pvp = leerBooleano("PvP activat (si/no): ");

        System.out.print("Color de perfil: ");
        String color = scanner.nextLine().trim();
        // Se asigna la fecha actual
        // Más info: https://docs.oracle.com/javase/7/docs/api/java/util/Date.html
        Date dataRegistre = new Date();

        Jugador jugador = new Jugador(id, nom, dataRegistre, batallesFetes, batallesGuanyades, pvp, color);
        arbol.insertar(jugador);
        jugadors.add(jugador);

        System.out.println("\nEl jugador " + nom + " ha entrat al sistema!");
    }

    /**
     * Lee un número entero desde la consola, validando la entrada.
     *
     * @param mensaje El mensaje a mostrar al usuario.
     * @return El número entero ingresado.
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                int input = Integer.parseInt(linea);
                if (input >= 0) {
                    return input;
                }
            } catch (NumberFormatException e) {
                // Entrada inválida, se volverá a pedir
            }
            System.out.println("Entrada no vàlida. Introdueix un número enter.");
        }
    }

    /**
     * Lee una entrada booleana desde la consola esperando "si" o "no".
     *
     * @param mensaje El mensaje a mostrar.
     * @return true si el usuario ingresa "si", false si ingresa "no".
     */
    private boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("si")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            }
            System.out.println("Entrada no vàlida. Escriu 'si' o 'no'.");
        }
    }
}