package ClasesPrincipales;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Heroi;
import Fase2.Arbres.Node;
import Fase1.Grafs.Lloc;
import Fase1.Grafs.Ruta;
import Fase1.Grafs.ExAssentamentRegional.AssentamentRegional;
import Fase1.Grafs.ExMapaSimplificat.MapaSimplificat;
import Fase1.Grafs.ExSimulacioDeViatge.SimulacioDeViatge;
import Fase2.Arbres.GeneracióDePatrulles.GeneracioDePatrulles;
import Fase2.Arbres.buscarHeroi.searchHeroiRecursive;
import Fase3.RTree.Figura;
import Fase3.RTree.Jugador;
import Fase3.RTree.NodoRTree;
import Fase3.RTree.RTree;
import Fase3.UI.MenuInteraccioRTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static Fase2.Arbres.FuncionalitatsBasiques_Arbre.FuncionalitatsBasiques.*;
import static Fase2.Arbres.FuncionalitatsBasiques_Arbre.FuncionalitatsBasiques.eliminacioHerois;
import static Fase2.Arbres.LluitaCases.LluitaCases.accioLluitaCases;
import static Fase2.Arbres.RepresentacioArbre.representacioArbre.representarArbre;
import static Fase2.Arbres.redistribucioMentors.redistribucioMentors.redistribuirMentors;
import static Fase3.UI.EliminarRtree.eliminarJugador;
import static Fase3.UI.RepresentTTree.representGraficament;
import static Fase3.UI.CercaDivisions.cerca;

/**
 * Clase principal que integra los distintos módulos del sistema "Song of Structures".
 * <p>
 * Se encargará de leer los datasets, construir las estructuras (grafs, arbres y R-Trees)
 * y gestionar la interacción por consola con el usuario.
 * </p>
 */
public class Main {
    static List<Lloc> llocs = new ArrayList<>();
    static List<Ruta> rutes = new ArrayList<>();
    static List<Heroi> herois = new ArrayList<>();
    static List<Jugador> jugadors = new ArrayList<>();
    static RTree rtree ;

    /**
     * Método principal. Lee los datos de los archivos, construye las estructuras y muestra
     * el menú principal para interactuar con el usuario.
     *
     * @param args Argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        llegirDataGrafs();
        Ruta[][] matriuAdj = new Ruta[llocs.size()][llocs.size()];
        omplirMatriuAdj(matriuAdj);
        llegirDataArbres();
        llegirDataArbresR();

        Node nodeArrel = new Node(herois.get(0).getId(), herois.get(0).getNom(), herois.get(0).getPoder(), herois.get(0).getCasa(), herois.get(0).getMaterial());
        Arbre arbre = new Arbre(nodeArrel);

        for (int i = 1; i < herois.size(); i++) {
            Node node = new Node(herois.get(i).getId(), herois.get(i).getNom(), herois.get(i).getPoder(), herois.get(i).getCasa(), herois.get(i).getMaterial());
            arbre.inserirNode(node, node.getPoder());
        }

        AssignarFillsMaxims();

        for(Jugador jugador : jugadors) {
            rtree.insertar(jugador);
        }
        MenuInteraccioRTree menu = new MenuInteraccioRTree(rtree, jugadors);


        Scanner input = new Scanner(System.in);
        char option2;

        int option = readInt(input, 1, 5, "\n·*:. Song of Structures .:*·\n" +
                "1. Disseny de lore (Grafs)\n" +
                "2. Control d’herois (Arbres)\n" +
                "3. Expansió virtual (Arbres R)\n" +
                "4. PER ESPECIFICAR\n" +
                "5. Aturar\n" +
                "Escull un bloc: ");

        while (option != 5) {
            switch (option) {
                case 1:
                    option2 = readChar(input, "A", "D", "A. Assentaments regionals\n" +
                            "B. Mapa simplificat\n" +
                            "C. Simulació de viatge\n\n" +
                            "D. Tornar enrere\n\n" +
                            "Quina eina vols fer servir?");
                    while (option2 != 'D' && option2 != 'd') {
                        switch (option2) {
                            case 'A':
                                AssentamentRegional.main(matriuAdj);
                                System.out.println("\n");
                                resetMatriuAdj(matriuAdj);
                                option2 = readChar(input, "A", "D", "A. Assentaments regionals\n" +
                                        "B. Mapa simplificat\n" +
                                        "C. Simulació de viatge\n" +
                                        "D. Tornar enrere\n\n" +
                                        "Quina eina vols fer servir?");
                                break;
                            case 'B':
                                MapaSimplificat.main(llocs, rutes, matriuAdj);
                                System.out.println("\n");
                                resetMatriuAdj(matriuAdj);
                                option2 = readChar(input, "A", "D", "A. Assentaments regionals\n" +
                                        "B. Mapa simplificat\n" +
                                        "C. Simulació de viatge\n" +
                                        "D. Tornar enrere\n\n" +
                                        "Quina eina vols fer servir?");
                                break;
                            case 'C':
                                SimulacioDeViatge.main(llocs, rutes, matriuAdj);
                                System.out.println("\n");
                                resetMatriuAdj(matriuAdj);
                                option2 = readChar(input, "A", "D", "A. Assentaments regionals\n" +
                                        "B. Mapa simplificat\n" +
                                        "C. Simulació de viatge\n" +
                                        "D. Tornar enrere\n\n" +
                                        "Quina eina vols fer servir?");
                                break;
                        }
                    }
                    option = readInt(input, 1, 5, "·*:. Song of Structures .:*·\n" +
                            "1. Disseny de lore (Grafs)\n" +
                            "2. Control d’herois (Arbres)\n" +
                            "3. Expansió virtual (Arbres R)\n" +
                            "4. PER ESPECIFICAR\n" +
                            "5. Aturar\n" +
                            "Escull un bloc: ");
                    break;
                case 2:
                    option2 = readChar(input, "A", "H", "A. Afegir heroi\n" +
                            "B. Eliminar heroi\n" +
                            "C. Representació visual\n" +
                            "D. Cercar heroi\n" +
                            "E. Generació de patrulles\n" +
                            "F. Enviats especials\n" +
                            "G. Lluita de cases\n" +
                            "H. Redistribució de mentors\n\n" +
                            "I. Tornar enrere\n\n" +
                            "Quina eina vols fer servir?");
                    while (option2 != 'I' && option2 != 'i') {
                        switch (option2) {
                            case 'A':
                                InsercioHeroi(arbre);
                                break;
                            case 'B':
                                eliminacioHerois(arbre);
                                break;
                            case 'C':
                                representarArbre(arbre);
                                System.out.println("\n");
                                break;
                            case 'D':
                                input.nextLine();
                                System.out.print("\nIntrodueix el poder de l'heroi que vols cercar: ");
                                String inputPower = input.nextLine().trim();
                                try {
                                    double power = Double.parseDouble(inputPower);
                                    searchHeroiRecursive searchHeroiRecursive = new searchHeroiRecursive();
                                    Node heroi = searchHeroiRecursive.searchHeroi(arbre, power);
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Entrada no vàlida. Si us plau, introdueix un número decimal vàlid.");
                                }
                                break;
                            case 'E':
                                GeneracioDePatrulles.generacioDePatrulles(arbre);
                                break;
                            case 'F':
                                List<Node> missioHerois = arbre.selHeroisMissio();
                                arbre.selHeroisMissio();
                                System.out.println("S’envien " + missioHerois.size() + " herois a la missió especial:");
                                for (Node hero : missioHerois) {
                                    System.out.println("    * " + hero.getNom() + " ( " + hero.getId() + ", " + hero.getCasa() + "): " + hero.getPoder());
                                }
                                break;
                            case 'G':
                                accioLluitaCases(arbre);
                                break;
                            case 'H':
                                arbre = redistribuirMentors(herois);
                                break;
                        }
                        option2 = readChar(input, "A", "I", "A. Afegir heroi\n" +
                                "B. Eliminar heroi\n" +
                                "C. Representació visual\n" +
                                "D. Cercar heroi\n" +
                                "E. Generació de patrulles\n" +
                                "F. Enviats especials\n" +
                                "G. Lluita de cases\n" +
                                "H. Redistribució de mentors\n\n" +
                                "I. Tornar enrere\n\n" +
                                "Quina eina vols fer servir?");
                    }
                    break;
                case 3:
                    option2 = readChar(input, "A", "F", "A. Afegir jugador\n" +
                            "B. Eliminar jugador\n" +
                            "C. Representació gràfica\n" +
                            "D. Cerca de divisions\n" +
                            "E. Formació de grups\n\n" +
                            "F. Tornar enrere\n\n" +
                            "Quina eina vols fer servir?");
                    switch (option2) {
                        case 'A':
                            menu.afegirJugador();
                            break;
                        case 'B':
                            eliminarJugador(rtree);
                            break;
                        case 'C':
                            representGraficament(jugadors);
                            break;
                        case 'D':
                            cerca(rtree);
                            break;
                        case 'E':
                            //TODO: formació de grups
                            break;
                    }
                    break;
                case 4:
                    //TODO: implementar funcionalitat per a especificar
                    option = readInt(input, 1, 5, "·*:. Song of Structures .:*·\n" +
                            "1. Disseny de lore (Grafs)\n" +
                            "2. Control d’herois (Arbres)\n" +
                            "3. Expansió virtual (Arbres R)\n" +
                            "4. PER ESPECIFICAR\n" +
                            "5. Aturar\n" +
                            "Escull un bloc: ");
                    break;
                case 5:
                    System.out.println("\nAturant Song of Structures.\n" +
                            ".:*· ··· --- ··· ·*:.");
                    break;
                default:
                    System.out.println("Opció no vàlida");
            }
        }
    }

    /**
     * Llegeix el dataset dels grafs des d'un fitxer i omple la llista de llocs i rutes.
     */
    public static void llegirDataGrafs() {
        try (Scanner input = new Scanner(new File("src/Graphs/graphsXXS.paed"))) {
            int numLlocs = Integer.parseInt(input.nextLine().trim());
            for (int i = 0; i < numLlocs; i++) {
                String[] parts = input.nextLine().split(";");
                llocs.add(new Lloc(Integer.parseInt(parts[0]), parts[1], parts[2]));
            }
            int numRutes = Integer.parseInt(input.nextLine().trim());
            for (int i = 0; i < numRutes; i++) {
                String[] parts = input.nextLine().split(";");
                rutes.add(new Ruta(getLloc(parts[0]), getLloc(parts[1]), parts[2],
                        Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna un lloc donat el seu identificador.
     *
     * @param id Identificador del lloc.
     * @return Lloc corresponent o null si no es troba.
     */
    public static Lloc getLloc(String id) {
        for (Lloc lloc : llocs) {
            if (lloc.getId() == Integer.parseInt(id)) {
                return lloc;
            }
        }
        return null;
    }

    /**
     * Omple la matriu d'adjacència amb les rutes disponibles.
     *
     * @param matriuAdj Matriu a omplir.
     */
    private static void omplirMatriuAdj(Ruta[][] matriuAdj) {
        for (Ruta ruta : rutes) {
            int indexA = llocs.indexOf(ruta.getLlocA());
            int indexB = llocs.indexOf(ruta.getLlocB());
            matriuAdj[indexA][indexB] = ruta;
            matriuAdj[indexB][indexA] = ruta;
        }
    }

    /**
     * Llegeix un enter de la consola validant que estigui dins d'un interval.
     *
     * @param input   Scanner per llegir.
     * @param min     Valor mínim permes.
     * @param max     Valor màxim permes.
     * @param message Missatge a mostrar.
     * @return Enter llegit.
     */
    public static int readInt(Scanner input, int min, int max, String message) {
        int number;
        while (true) {
            System.out.println(message);
            if (input.hasNextInt()) {
                number = input.nextInt();
                if (number >= min && number <= max) {
                    return number;
                }
            } else {
                input.next();
            }
            System.out.println("Entrada no vàlida. Torna-ho a intentar. El valor ha d'estar entre (" + min + " - " + max + "): ");
        }
    }

    /**
     * Llegeix un caràcter de la consola validant que estigui dins d'un rang donat.
     *
     * @param input     Scanner per llegir.
     * @param validFrom Caràcter mínim vàlid.
     * @param validTo   Caràcter màxim vàlid.
     * @param message   Missatge a mostrar.
     * @return Caràcter llegit en majúscules.
     */
    private static char readChar(Scanner input, String validFrom, String validTo, String message) {
        char character;
        while (true) {
            System.out.println(message);
            String inputStr = input.next().toUpperCase();
            if (inputStr.length() == 1) {
                character = inputStr.charAt(0);
                if (character >= validFrom.charAt(0) && character <= validTo.charAt(0)) {
                    return character;
                }
            }
            System.out.println("Entrada no vàlida. Torna-ho a intentar. El valor ha d'estar entre (" + validFrom + " - " + validTo + "): ");
        }
    }

    /**
     * Reseteja la matriu d'adjacència, posant la propietat 'visitat' a false per a cada lloc.
     *
     * @param matriuAdj Matriu d'adjacència a resetear.
     */
    private static void resetMatriuAdj(Ruta[][] matriuAdj) {
        for (int i = 0; i < matriuAdj.length; i++) {
            for (int j = 0; j < matriuAdj.length; j++) {
                if (matriuAdj[i][j] != null) {
                    matriuAdj[i][j].getLlocA().setVisitat(false);
                    matriuAdj[i][j].getLlocB().setVisitat(false);
                }
            }
        }
    }

    /**
     * Llegeix el dataset d'arbres (heroïs) des d'un fitxer i omple la llista de heroïs.
     */
    public static void llegirDataArbres() {
        try (Scanner input = new Scanner(new File("src/Arbres/treeXXS.paed"))) {
            int numHerois = Integer.parseInt(input.nextLine().trim());
            for (int i = 0; i < numHerois; i++) {
                String[] parts = input.nextLine().split(";");
                herois.add(new Heroi(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2]),
                        parts[3],
                        parts[4]
                ));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Llegeix el dataset dels jugadors des d'un fitxer i omple la llista de jugadors.
     */
    public static void llegirDataArbresR() {
        try (Scanner input = new Scanner(new File("src/ArbresR/rtreeXXS.paed"))) {
            int numJugadors = Integer.parseInt(input.nextLine().trim());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < numJugadors; i++) {
                String[] parts = input.nextLine().split(";");
                Date date = dateFormat.parse(parts[2]);
                Jugador jugador = new Jugador(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        date,
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Boolean.parseBoolean(parts[5]),
                        parts[6]
                );
                jugadors.add(jugador);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AssignarFillsMaxims() {
        int total = jugadors.size();


        int maxEntrades = (int) (Math.ceil(jugadors.size()*0.6));
        if(maxEntrades>50){
            maxEntrades = 50;
        }
        if(maxEntrades<5){
            maxEntrades = 10;
        }
        int minEntrades = (int)(Math.ceil(maxEntrades*0.3));

        rtree = new RTree(maxEntrades, minEntrades);

    }


}