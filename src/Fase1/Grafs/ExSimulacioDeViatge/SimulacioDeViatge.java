package Fase1.Grafs.ExSimulacioDeViatge;

import Fase1.Grafs.Graph;
import Fase1.Grafs.Lloc;
import Fase1.Grafs.Ruta;
import java.util.*;

/**
 * Classe que simula el viatge entre dos llocs d'interès utilitzant l'algorisme de Dijkstra.
 * <p>
 * El programa llegeix un graf format per una llista de llocs i rutes, demana a l'usuari el lloc d'origen,
 * el lloc destí i l'estació de l'any. Després, calcula el camí mínim basant-se en els temps de trajecte
 * (que poden variar segons l'estació) i finalment mostra la ruta i el temps total necessari.
 * </p>
 */
public class SimulacioDeViatge {
    private static final int ERROR = -1;
    private static List<Lloc> llocs = new ArrayList<>();

    /**
     * Mètode principal per a la simulació del viatge.
     * <p>
     * Llegeix els paràmetres d'entrada (llocs, rutes i matriu d'adjacència), demana a l'usuari els identificadors
     * del lloc d'origen i destí, així com l'estació de l'any, i calcula el camí mínim amb Dijkstra.
     * Si es troba un camí, es mostra la ruta i el temps total de trajecte.
     * </p>
     *
     * @param llocsParam Llista de llocs del graf.
     * @param rutes      Llista de rutes del graf.
     * @param matriuAdj  Matriu d'adjacència amb les rutes.
     */
    public static void main(List<Lloc> llocsParam, List<Ruta> rutes, Ruta[][] matriuAdj) {
        Scanner input = new Scanner(System.in);

        System.out.println("Número total de llocs carregats: " + llocsParam.size());
        System.out.println("Número total de rutes carregades: " + rutes.size());

        int inici = obtenirIdentificadorValid(input, "Identificador del lloc origen: ", llocsParam);
        int fi = obtenirIdentificadorValid(input, "Identificador del lloc destí: ", llocsParam);
        String estacion = obtenirEstacioValid(input);

        Graph graf = new Graph(llocs, rutes);
        List<Integer> cami = dijkstra(graf, inici, fi, estacion);

        if (cami.isEmpty()) {
            System.out.println("No s’ha pogut trobar cap camí entre aquests llocs d’interès.");
        } else {
            int tempsTotal = calcularTempsTotal(cami, matriuAdj, llocsParam, estacion);
            mostrarRuta(cami, matriuAdj, llocsParam, estacion, tempsTotal);
        }
    }

    /**
     * Demana a l'usuari que introdueixi una estació de l'any vàlida.
     * <p>
     * Les estacions vàlides són SPRING, SUMMER, AUTUMN i WINTER.
     * </p>
     *
     * @param input Scanner per llegir la entrada.
     * @return L'estació introduïda en majúscules.
     */
    private static String obtenirEstacioValid(Scanner input) {
        String estacion;
        List<String> estacionesValidas = Arrays.asList("SPRING", "SUMMER", "AUTUMN", "WINTER");
        do {
            System.out.print("Estació de l’any (SPRING/SUMMER/AUTUMN/WINTER): ");
            estacion = input.next().toUpperCase();
            if (!estacionesValidas.contains(estacion)) {
                System.out.println("Estació no vàlida. Torna a provar.");
            }
        } while (!estacionesValidas.contains(estacion));
        return estacion;
    }

    /**
     * Calcula el temps total del trajecte recorregut en el camí.
     *
     * @param cami        Llista d'identificadors dels llocs que formen el camí.
     * @param matriuAdj   Matriu d'adjacència amb les rutes.
     * @param llocsParam  Llista de llocs.
     * @param estacion    Estació de l'any que afecta el temps de trajecte.
     * @return El temps total acumulat.
     */
    private static int calcularTempsTotal(List<Integer> cami, Ruta[][] matriuAdj, List<Lloc> llocsParam, String estacion) {
        int tempsTotal = 0;
        for (int i = 0; i < cami.size() - 1; i++) {
            int indexA = obtenirIndexPerId(llocsParam, cami.get(i));
            int indexB = obtenirIndexPerId(llocsParam, cami.get(i + 1));
            tempsTotal += calcularTempsTrajecte(matriuAdj, indexA, indexB, estacion);
        }
        return tempsTotal;
    }

    /**
     * Demana a l'usuari un identificador de lloc vàlid que existeixi a la llista.
     *
     * @param input      Scanner per llegir la entrada.
     * @param missatge   Missatge a mostrar.
     * @param llocsParam Llista de llocs.
     * @return L'identificador vàlid introduït.
     */
    private static int obtenirIdentificadorValid(Scanner input, String missatge, List<Lloc> llocsParam) {
        int id;
        boolean valid = false;
        do {
            System.out.print(missatge);
            if (input.hasNextInt()) {
                id = input.nextInt();
                for (Lloc lloc : llocsParam) {
                    if (lloc.getId() == id) {
                        valid = true;
                        return id;
                    }
                }
                System.out.println("L'identificador introduït no existeix. Torna a provar.");
            } else {
                System.out.println("Valor incorrecte. Ha de ser un número enter.");
                input.next();
            }
        } while (!valid);
        return -1;
    }

    /**
     * Mostra la ruta trobada per l'algorisme de Dijkstra, indicant el temps per cada trajecte i el temps total.
     *
     * @param cami       Llista d'identificadors dels llocs que formen el camí.
     * @param matriuAdj  Matriu d'adjacència amb les rutes.
     * @param llocsParam Llista de llocs.
     * @param estacion   Estació de l'any.
     * @param tempsTotal Temps total del trajecte.
     */
    private static void mostrarRuta(List<Integer> cami, Ruta[][] matriuAdj, List<Lloc> llocsParam, String estacion, int tempsTotal) {
        System.out.println("\nEl temps mínim per anar del lloc " + cami.get(0) + " al " + cami.get(cami.size() - 1) + " durant " + estacion + " són " + tempsTotal + " minuts.");
        System.out.println("\nCal seguir el següent camí:");

        for (int i = 0; i < cami.size() - 1; i++) {
            Lloc llocActual = trobarLlocPerId(llocsParam, cami.get(i));
            int indexA = obtenirIndexPerId(llocsParam, cami.get(i));
            int indexB = obtenirIndexPerId(llocsParam, cami.get(i + 1));

            int tempsViaje = calcularTempsTrajecte(matriuAdj, indexA, indexB, estacion);

            if (llocActual != null) {
                System.out.println(cami.get(i) + " – " + llocActual.getNom() + " (" + llocActual.getFaccio() + ")");
                System.out.println("::: +" + tempsViaje + " minuts :::");
            }
        }

        Lloc llocFinal = trobarLlocPerId(llocsParam, cami.get(cami.size() - 1));
        if (llocFinal != null) {
            System.out.println(cami.get(cami.size() - 1) + " – " + llocFinal.getNom() + " (" + llocFinal.getFaccio() + ")");
        }
    }

    /**
     * Calcula el temps de trajecte entre dos índexs de la matriu d'adjacència segons l'estació.
     *
     * @param matriuAdj Matriu d'adjacència amb les rutes.
     * @param indexA    Índex del lloc d'origen.
     * @param indexB    Índex del lloc destí.
     * @param estacion  Estació de l'any.
     * @return El temps de trajecte, o 0 si no es troba una ruta.
     */
    private static int calcularTempsTrajecte(Ruta[][] matriuAdj, int indexA, int indexB, String estacion) {
        if (indexA != -1 && indexB != -1 && indexA < matriuAdj.length && indexB < matriuAdj[0].length) {
            if (matriuAdj[indexA][indexB] != null) {
                return (estacion.equals("SPRING") || estacion.equals("AUTUMN"))
                        ? matriuAdj[indexA][indexB].getTempsAdvers()
                        : matriuAdj[indexA][indexB].getTempsIdeal();
            }
        }
        return 0;
    }

    /**
     * Retorna l'índex d'un lloc a la llista basat en el seu identificador.
     *
     * @param llocs Llista de llocs.
     * @param id    Identificador del lloc.
     * @return L'índex del lloc, o -1 si no es troba.
     */
    public static int obtenirIndexPerId(List<Lloc> llocs, int id) {
        for (int i = 0; i < llocs.size(); i++) {
            if (llocs.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna l'índex del primer element no visitat amb la distància mínima.
     *
     * @param dist     Array amb les distàncies.
     * @param visitats Array booleà que indica si un element ha estat visitat.
     * @param n        Nombre d'elements.
     * @return L'índex de l'element amb la distància mínima, o -1 si no n'hi ha cap.
     */
    private static int obtenirMinimNoVisitats(int[] dist, boolean[] visitats, int n) {
        int minim = Integer.MAX_VALUE;
        int vertexMinim = -1;

        for (int i = 0; i < n; i++) {
            if (!visitats[i] && dist[i] < minim) {
                minim = dist[i];
                vertexMinim = i;
            }
        }
        return vertexMinim;
    }

    /**
     * Troba un lloc a la llista a partir del seu identificador.
     *
     * @param llocs Llista de llocs.
     * @param id    Identificador del lloc.
     * @return El lloc corresponent, o null si no es troba.
     */
    public static Lloc trobarLlocPerId(List<Lloc> llocs, int id) {
        for (Lloc lloc : llocs) {
            if (lloc.getId() == id) {
                return lloc;
            }
        }
        return null;
    }

    /**
     * Calcula el camí mínim entre dos llocs en un graf utilitzant l'algorisme de Dijkstra.
     *
     * <p>
     * El mètode construeix una llista d'adjacència basada en els identificadors dels llocs i utilitza la funció
     * {@code calcularTemps} per determinar el temps de trajecte d'una ruta segons les condicions (clima i estació).
     * Si no es pot trobar cap camí vàlid, es retorna una llista buida.
     * </p>
     *
     * @param graf     El graf que conté la llista de llocs i rutes.
     * @param inici   Identificador del lloc d'origen.
     * @param fi       Identificador del lloc destí.
     * @param estacion Estació de l'any (SPRING, SUMMER, AUTUMN o WINTER) que afecta el temps de trajecte.
     * @return Una llista d'enters que representa el camí mínim (identificadors dels llocs) des del punt d'inici fins al destí.
     */
    public static List<Integer> dijkstra(Graph graf, int inici, int fi, String estacion) {
        int maxId = 0;
        for (Lloc lloc : graf.getLlocs()) {
            if (lloc.getId() > maxId) {
                maxId = lloc.getId();
            }
        }
        for (Ruta ruta : graf.getRutes()) {
            if (ruta.getLlocA().getId() > maxId) {
                maxId = ruta.getLlocA().getId();
            }
            if (ruta.getLlocB().getId() > maxId) {
                maxId = ruta.getLlocB().getId();
            }
        }

        List<List<Ruta>> adjacents = new ArrayList<>();
        for (int i = 0; i <= maxId; i++) {
            adjacents.add(new ArrayList<>());
        }

        for (Ruta ruta : graf.getRutes()) {
            adjacents.get(ruta.getLlocA().getId()).add(ruta);
        }

        if (inici >= maxId + 1 || fi >= maxId + 1 || inici < 0 || fi < 0) {
            System.out.println("");
            return new ArrayList<>();
        }

        boolean[] visitats = new boolean[maxId + 1];
        int[] dist = new int[maxId + 1];
        int[] camins = new int[maxId + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(camins, -1);

        dist[inici] = 0;
        camins[inici] = inici;

        while (!visitats[fi]) {
            int actual = obtenirMinimNoVisitats(dist, visitats, maxId + 1);
            if (actual == ERROR) {
                break;
            }

            visitats[actual] = true;

            for (Ruta ruta : adjacents.get(actual)) {
                if (visitats[ruta.getLlocB().getId()]) {
                    continue;
                }

                int novaDist = dist[actual] + calcularTemps(ruta, estacion);
                if (novaDist < dist[ruta.getLlocB().getId()]) {
                    dist[ruta.getLlocB().getId()] = novaDist;
                    camins[ruta.getLlocB().getId()] = actual;
                }
            }
        }
        return reconstrueix(camins, inici, fi);
    }

    /**
     * Calcula el temps de trajecte d'una ruta segons l'estació.
     * <p>
     * Si la ruta té clima "ARID" i l'estació és SUMMER, o si té clima "POLAR" i l'estació és WINTER,
     * es retorna -1 (indicant que no es pot transitar per aquesta ruta). En altres casos, es retorna
     * el temps advers o ideal segons la lògica definida.
     * </p>
     *
     * @param ruta     La ruta per la qual calcular el temps.
     * @param estacion Estació de l'any.
     * @return El temps de trajecte corresponent.
     */
    private static int calcularTemps(Ruta ruta, String estacion) {
        if (ruta.getClima().equals("ARID")) {
            if (estacion.equals("SUMMER")) {
                return -1;
            } else if (estacion.equals("SPRING")) {
                return ruta.getTempsAdvers();
            } else {
                return ruta.getTempsIdeal();
            }
        }

        if (ruta.getClima().equals("POLAR")) {
            if (estacion.equals("WINTER")) {
                return -1;
            } else if (estacion.equals("AUTUMN")) {
                return ruta.getTempsAdvers();
            } else {
                return ruta.getTempsIdeal();
            }
        }

        if (ruta.getClima().equals("TEMPERATE")) {
            if (estacion.equals("SUMMER") || estacion.equals("WINTER")) {
                return ruta.getTempsAdvers();
            } else {
                return ruta.getTempsIdeal();
            }
        }

        return ruta.getTempsIdeal();
    }

    /**
     * Reconstrueix el camí a partir de l'array de predecessors.
     * <p>
     * A partir de l'array que guarda el node predecessor de cada node (camins),
     * es construeix la llista d'identificadors que forma el camí des del node d'origen fins al destí.
     * </p>
     *
     * @param camins Array de predecessors.
     * @param inici  Identificador del node d'origen.
     * @param fi     Identificador del node destí.
     * @return Una llista d'enters que representa el camí reconstruït.
     */
    private static List<Integer> reconstrueix(int[] camins, int inici, int fi) {
        List<Integer> cami = new ArrayList<>();
        int actual = fi;
        while (actual != inici && actual != ERROR) {
            cami.add(actual);
            actual = camins[actual];
        }
        cami.add(inici);
        Collections.reverse(cami); // TODO: Quizás no es necesario, pensar si es.

        return cami;
    }
}