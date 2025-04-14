package Fase1.Grafs.ExAssentamentRegional;


import java.util.*;

import Fase1.Grafs.Lloc;
import Fase1.Grafs.Ruta;



public class AssentamentRegional {


    public static void main(Ruta[][] matriuAdj) {
        System.out.println("Facció (GRAPHADIA/ETREELIA/RECTANIA/HASHPERIA) a consultar: ");
        Scanner input = new Scanner(System.in);
        String faccio = input.nextLine();


        Regio regio = TrobarAssentaments(faccio, matriuAdj);

        System.out.println("La regio mes gran que controlen esta formada pels següents " + regio.getTamany() + " llocs");
        for (int i = 0; i < regio.getLlocs().size(); i++) {
            Lloc actual = regio.getLlocs().get(i);
            System.out.println(actual.getId() + "-" + actual.getNom() + "(" + actual.getFaccio() + ")");
        }

    }

    public static Regio TrobarAssentaments(String faccio, Ruta[][] matriuAdj) {
        Long tempsInicialDFS ;
        Long tempsInicialBFS ;
        Long tempsFinalDFS ;
        Long tempsFinalBFS ;

        int numMax = 0;
        int numLLocs = matriuAdj.length;
        Regio regioMax = new Regio(0, new ArrayList<>());


        for (int i = 0; i < numLLocs; i++) {//DFS POR FACCION
            for (int j = 0; j < numLLocs; j++) {
                Ruta actual = matriuAdj[i][j];
                if (actual != null) {
                    Lloc llocA = actual.getLlocA();
                    Lloc llocB = actual.getLlocB();
                    if ((!llocA.getVisitat() || !llocB.getVisitat()) && llocA.getFaccio().equals(faccio) && llocB.getFaccio().equals(faccio)) {
                        Regio regio = new Regio(0, new ArrayList<>());
                        if (!llocA.getVisitat()) {
                            int indice_actual = i;
                            tempsInicialDFS = System.nanoTime();
                            DFSFaccio(llocA, matriuAdj, regio, faccio, i);
                            tempsFinalDFS = System.nanoTime();
                            tempsInicialBFS = System.nanoTime();
                            BFSFaccio(llocA, matriuAdj, regio, faccio);
                            tempsFinalBFS = System.nanoTime();
                            System.out.println("El temps que ha trigat el  DFS es : " + (tempsFinalDFS - tempsInicialDFS) + " nanosegons");
                            System.out.println("El temps que ha trigat el  BFS es : " + (tempsFinalBFS - tempsInicialBFS) + " nanosegons");
                        }
                        if (!llocB.getVisitat()) {
                            int indice_actual = j;
                            tempsInicialDFS = System.nanoTime();
                            DFSFaccio(llocB, matriuAdj, regio, faccio, j);
                            tempsFinalDFS = System.nanoTime();
                            tempsInicialBFS = System.nanoTime();
                            BFSFaccio(llocB, matriuAdj, regio, faccio);
                            tempsFinalBFS = System.nanoTime();
                            System.out.println("El temps que ha trigat el  DFS es : " + (tempsFinalDFS - tempsInicialDFS) + " nanosegons");
                            System.out.println("El temps que ha trigat el  BFS es : " + (tempsFinalBFS - tempsInicialBFS) + " nanosegons");
                        }
                        if (regio.getTamany() > numMax) {
                            numMax = regio.getTamany();
                            regioMax = regio;
                        }
                    }
                }
            }
        }

        return regioMax;
    }

    public static void DFSFaccio(Lloc actual, Ruta[][] matriuAdj, Regio regio, String faccio, int indice_actual) {
        actual.setVisitat(true);
        regio.addLloc(actual);
        for (int j = 0; j < matriuAdj.length; j++) {
            if (matriuAdj[indice_actual][j] != null) {
                Ruta seguent = matriuAdj[indice_actual][j];
                if (seguent != null) {
                    Lloc llocA = seguent.getLlocA();
                    Lloc llocB = seguent.getLlocB();
                    Lloc seguentLloc = null;
                    if (llocA.getNom().equals(actual.getNom())) {
                        seguentLloc = llocB;
                    } else {
                        seguentLloc = llocA;
                    }
                    if (!seguentLloc.getVisitat() && seguentLloc.getFaccio().equals(faccio)) {
                        DFSFaccio(seguentLloc, matriuAdj, regio, faccio, j);
                    }
                }
            }
        }
    }

    public static void BFSFaccio(Lloc principi, Ruta[][] matriuAdj, Regio regio, String faccio) {
        Queue<Lloc> cua = new LinkedList<>();
        principi.setVisitat(true);
        cua.add(principi);
        regio.addLloc(principi);

        while (!cua.isEmpty()) {
            Lloc actual = cua.poll();
            List<Lloc> adjacents = llocsAdjacents(actual, matriuAdj);
            for(Lloc adjacent : adjacents) {
                if (!adjacent.getVisitat() && adjacent.getFaccio().equals(faccio)) {
                    adjacent.setVisitat(true);
                    cua.add(adjacent);
                    regio.addLloc(adjacent);
                }
            }

        }

    }

    public static List<Lloc> llocsAdjacents(Lloc actual, Ruta[][] matriuAdj) {
        List<Lloc> adjacents = new ArrayList<>();
        for (int i = 0; i < matriuAdj.length; i++) {
            for (int j = 0; j < matriuAdj[i].length; j++) {
                if (matriuAdj[i][j] != null) {
                    Ruta actualRuta = matriuAdj[i][j];
                    Lloc llocA = actualRuta.getLlocA();
                    Lloc llocB = actualRuta.getLlocB();
                    if (llocA.getNom().equals(actual.getNom())) {
                        adjacents.add(llocB);
                    } else if (llocB.getNom().equals(actual.getNom())) {
                        adjacents.add(llocA);
                    }
                }
            }
        }
        return adjacents;
    }
}
