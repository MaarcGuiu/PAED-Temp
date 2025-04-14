package Fase2.Arbres.FuncionalitatsBasiques_Arbre;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Node;

import java.util.Scanner;

import static ClasesPrincipales.Main.readInt;

/**
 * Aquesta classe conté les funcionalitats bàsiques per a la gestió d'herois en un arbre.
 *
 * <p>
 * Proporciona mètodes per inserir i eliminar herois dins d'un arbre, interactuant amb l'usuari a través de la consola.
 * </p>
 */
public class FuncionalitatsBasiques {

    /**
     * Inserta un nou heroi en l'arbre.
     *
     * <p>
     * Aquest mètode sol·licita a l'usuari les dades necessàries per a la creació d'un heroi (identificador, nom,
     * nivell de poder, casa i material de l'armadura), crea un nou node amb aquestes dades i el insereix en l'arbre.
     * </p>
     *
     * @param arbre L'arbre on s'inserirà el nou heroi.
     */
    public static void InsercioHeroi(Arbre arbre) {
        int id;
        String nom;

        Scanner input = new Scanner(System.in);
        id = readInt(input,0,100000000,"Introdueix l'identificador de l'heroi:");
        input.nextLine();

        System.out.println("Introdueix el nom de l'heroi:");
        nom = input.nextLine();

        System.out.println("Nivell de poder de l'heroi:");
        double poder = input.nextDouble();
        input.nextLine();


        System.out.println("Casa  de l'heroi:");
        String casa = input.nextLine();

        System.out.println("Material de l'armadura:");
        String material = input.nextLine();

        Node node = new Node(id, nom, poder, casa, material);
        arbre.inserirNode(node, poder);
        System.out.println("L'univers s'ha expandit amb l'heroi " + nom);
    }


    /**
     * Elimina un heroi de l'arbre.
     *
     * <p>
     * Aquest mètode sol·licita a l'usuari l'identificador de l'heroi a eliminar i utilitza el mètode
     * {@code eliminarHeroi} de l'arbre. Per defecte s'utilitza la eliminació per successor.
     * </p>
     *
     * @param arbre L'arbre des del qual s'eliminarà l'heroi.
     */
    public static void eliminacioHerois(Arbre arbre) {
        System.out.print("Identificador de l'heroi: ");
        Scanner id = new Scanner(System.in);

        // usaremos el metodo eliminarHeroi por succesor, si quisiesemos hacerlo por
        // predesesor, deberíamos de pasarle true como segundo parámetro
        arbre.eliminarHeroi(id.nextInt());
    }
}
