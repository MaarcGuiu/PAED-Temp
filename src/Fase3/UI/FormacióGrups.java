package Fase3.UI;

import Fase3.RTree.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;

public class FormacióGrups {
    //https://stackoverflow.com/questions/45816632/nearest-neighbor-algorithm-in-r-tree
    //https://www.ibm.com/es-es/think/topics/knn
    private static PriorityQueue<Figura> jugadoresAptos;
    private static List<Jugador> jugadoresParaEquipos;
    private static PriorityQueue<Figura> opciones;
    private static int x_elegido;
    private static int y_elegido;
    private static int numJugadorsElegidos;
    private static List<Jugador> equipo1;
    private static List<Jugador> equipo2;



    public static void FormarGrups(RTree rTree) {


        Scanner input = new Scanner(System.in);
        System.out.println("Introdueix el nombre de batalles realitzades: ");
        x_elegido = input.nextInt();
        System.out.println("Introdueix el nombre de batalles guanyades: ");
        y_elegido = input.nextInt();
        System.out.println("Introdueix el nombre de jugadors a formar: ");
        numJugadorsElegidos = input.nextInt();

        long startTime = System.nanoTime();



        jugadoresParaEquipos= new ArrayList<>();
        equipo1 = new ArrayList<>();
        equipo2 = new ArrayList<>();

        InicializarColas();

        BuscarGrups(rTree.getRaiz());

        if (jugadoresAptos.size() < numJugadorsElegidos) {
            System.out.println("No hi ha suficients jugadors amb PVP activados.");
            return;
        }else {
            for (int i = 0; i < numJugadorsElegidos; i++) {
                Jugador jugador = (Jugador) jugadoresAptos.poll();
                jugadoresParaEquipos.add(jugador);
            }

            if (jugadoresParaEquipos.size() % 2 == 0 && jugadoresParaEquipos.size() > 5) {
                for (int i = 0; i < jugadoresParaEquipos.size(); i++) {
                    if (i % 2 == 0) {
                        equipo1.add(jugadoresParaEquipos.get(i));
                    } else {
                        equipo2.add(jugadoresParaEquipos.get(i));
                    }
                }
                String colorMedio1 = calcularColorMedio(equipo1);
                String colorMedio2 = calcularColorMedio(equipo2);
                String reset = "\u001B[0m";

                Color color1 = hex2Rgb(colorMedio1);
                String ansi1 = String.format("\u001B[38;2;%d;%d;%dm", color1.getRed(), color1.getGreen(), color1.getBlue());

                System.out.println(ansi1 + "---- Grup 1: -------" + reset);
                for (Jugador jugador : equipo1) {
                    System.out.println(ansi1 + jugador.getName() + reset);
                }

                Color color2 = hex2Rgb(colorMedio2);
                String ansi2 = String.format("\u001B[38;2;%d;%d;%dm", color2.getRed(), color2.getGreen(), color2.getBlue());

                System.out.println(ansi2 + "---- Grup 2: -------" + reset);
                for (Jugador jugador : equipo2) {
                    System.out.println(ansi2 + jugador.getName() + reset);
                }
            } else {
                System.out.println("Els " + numJugadorsElegidos + " mes propers son :\n");
                for (Jugador j : jugadoresParaEquipos) {
                    if (j.isPvp())
                        System.out.println("\t* " + j.getName() + " (" + j.getId() + " - " + j.getBattlesWon() + "/" + j.getBattlesDone() + " - PvP activat)");
                    else
                        System.out.println("\t* " + j.getName() + " (" + j.getId() + " - " + j.getBattlesWon() + "/" + j.getBattlesDone() + " - PvP desactivat)");
                }
            }
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Duración: " + duration  + " ns");
        System.out.println("\n");

    }
    public static void  BuscarGrups(NodoRTree raiz) {
        if(raiz==null){
            return;
        }else{
            opciones.add(raiz);

            while(!opciones.isEmpty()){ //Hacemos al igual que hicimos en el semeStre pasamos una especie de BRANCH ANB BOUND
                Figura figura = opciones.poll();
                if(figura instanceof Jugador){
                    Jugador jugador = (Jugador) figura;
                    if(jugador.isPvp()){
                        jugadoresAptos.add(jugador);
                    }
                }else if(figura instanceof NodoRTree){ //Si no es un jugador compruebo sus hijos para ver si ellos pueden ser jugadores que cumplen ese requisito o no
                    NodoRTree nodo = (NodoRTree) figura;
                    for(Figura hijo : nodo.getHijos()){
                        opciones.add(hijo); //La añado como opcion para continuar buscando las futuras posibles opciones
                    }
                }
            }

        }
    }
    private static int calcularDistancia(Rectangulo rectangulo) {

        return abs(rectangulo.getMinHechas() - x_elegido) + abs(rectangulo.getMinGanadas() - y_elegido);
    }
    private static void InicializarColas() {
        opciones= new PriorityQueue<>(new Comparator<Figura>(){ //Para que asi se vaya ordenando todo el rato
            @Override
            public int compare(Figura o1, Figura o2) {
                return Double.compare(calcularDistancia(o1.getMBR()), calcularDistancia(o2.getMBR()));
            }
        });
        jugadoresAptos = new PriorityQueue<>(new Comparator<Figura>(){ //Para que asi se vaya ordenando todo el rato
            @Override
            public int compare(Figura o1, Figura o2) {
                return Double.compare(calcularDistancia(o1.getMBR()), calcularDistancia(o2.getMBR()));
            }
        });

    }
    private static String  calcularColorMedio(List<Jugador> equipo) {
        //https://stackoverflow.com/questions/4129666/how-to-convert-hex-to-rgb-using-java
       int sumaTotalR=0, sumaTotalG=0, sumaTotalB=0;

        for (Jugador j : equipo) {
            Color color = hex2Rgb(j.getColor());
            sumaTotalR += color.getRed();
            sumaTotalG += color.getGreen();
            sumaTotalB += color.getBlue();
        }
        int redMedio=sumaTotalR/equipo.size();
        int greenMedio=sumaTotalG/equipo.size();
        int blueMedio=sumaTotalB/equipo.size();

        return String.format("#%02X%02X%02X", redMedio, greenMedio, blueMedio);
    }
    public static Color hex2Rgb(String colorStr) {
       int r= Integer.valueOf(colorStr.substring(1, 3), 16);
       int g= Integer.valueOf(colorStr.substring(3, 5), 16);
       int b= Integer.valueOf(colorStr.substring(5, 7), 16);

       return  new Color(r, g, b);
    }
    public static int contarJugadorsPvp(NodoRTree nodo) {
        if (nodo == null) return 0;
        int count = 0;
        for (Figura f : nodo.getHijos()) {
            if (f instanceof Jugador) {
                if (((Jugador) f).isPvp()) count++;
            } else if (f instanceof NodoRTree) {
                count += contarJugadorsPvp((NodoRTree) f);
            }
        }
        return count;
    }
}