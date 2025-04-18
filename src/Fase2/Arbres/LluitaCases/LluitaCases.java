package Fase2.Arbres.LluitaCases;

import Fase2.Arbres.Arbre;
import Fase2.Arbres.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LluitaCases {
    public static void accioLluitaCases(Arbre arbre) {
        Scanner input = new Scanner(System.in);
        double poder1;
        double poder2;

        try {
            System.out.println("Nivell del primer combatent:");
            poder1= Double.parseDouble(input.nextLine().trim());

            System.out.println("Nivell del segon combatent:");
             poder2 = Double.parseDouble(input.nextLine().trim());

        } catch (NumberFormatException e) {
           System.out.println("Introdueix el numero double corresponent");
            return;
        }

        //Opcio 1 que vaig plantejar

        /*Node node1 = searchHeroiRecursive.searchHeroiRecursive(arbre.getArrel(), poder1);
        //Node node2 = searchHeroiRecursive.searchHeroiRecursive(arbre.getArrel(), poder2);
        //Node node1 = searchHeroiRecursive.searchHeroiRecursive(arbre.getArrel(), poder1);
        //int nivelNode1 = encontrarNivelNodoRecursiu(node1,0);
        //int nivelNode2 = encontrarNivelNodoRecursiu(node2,0);

        Node mentorComu = null;
        /*if (node1 != null && node2 != null) {
            mentorComu = PareComuCercaRecursiva(node1, node2, nivelNode1, nivelNode2);
        }

         */



        //Opcio 2 millor plantejada
        List <Node> cami1 = new ArrayList<>();
        List <Node> cami2 = new ArrayList<>();
        String casa1=buscarCamiRecursiu(arbre.getArrel(),cami1,poder1);
        String casa2=buscarCamiRecursiu(arbre.getArrel(),cami2,poder2);
       boolean PareTrobat=false; //Boolean que ens indicara si hem trobar el node on  els camins cambiaven ,serveix per controlar posibles situacions on s'ha trobat un mentor pero aquest no compleix amb les condicions de ser mentor comu
        Node mentorComu = null;
        mentorComu=TrobarMentorComuRecursiu(cami1,cami2,PareTrobat,0,casa1,casa2);

        if (mentorComu == null) {
            System.out.println("No hi ha mentor comu");
        } else {
            System.out.println("El combat entre " + cami1.get(cami1.size() - 1).getNom() + " i " + cami2.get(cami2.size() - 1).getNom() + " hauria de ser jutjat per " + mentorComu.getNom());

        }

    }
    public static Node  TrobarMentorComuRecursiu(List <Node> cami1, List <Node> cami2,boolean PareTrobat,int i,String casa1,String casa2){
        if (i < 0){
            return null;
        }
        Node pare= cami1.get(i).getPare();
        if (pare!=null && pare.getCasa()!=casa1 && pare.getCasa()!=casa2 && PareTrobat){
            return pare;
        }
        if (cami1.get(i).getId()==cami2.get(i).getId()) {
            return TrobarMentorComuRecursiu(cami1, cami2, false, i + 1, casa1, casa2);
        } else {
            if (pare!=null && pare.getCasa()!=casa1 && pare.getCasa()!=casa2){
                return pare;
            } else {
                 return TrobarMentorComuRecursiu(cami1, cami2, true, i - 1, casa1, casa2);
            }
        }
    }
    //Funció que busca y torna el cami que s'ha fet fins a un heroi
    public static String buscarCamiRecursiu(Node actual,List <Node> cami,double poder){
        if(actual==null){
            return null;
        }
        String casa=null;

        if(actual.getPoder()==poder) {
            cami.add(actual);
            return actual.getCasa();
        }
        if(actual.getDreta()!=null && actual.getPoder()<poder){
            cami.add(actual);
            casa=buscarCamiRecursiu(actual.getDreta(),cami,poder);
            if(casa!=null){
                cami.remove(actual);
            }

        }else{
            if(actual.getEsquerra()!=null && actual.getPoder()>poder){
                cami.add(actual);
                casa= buscarCamiRecursiu(actual.getEsquerra(),cami,poder);
                if(casa!=null){
                    cami.remove(actual);
                }

            }

        }
        return null;
    }


   /*
    public static Node PareComuCercaRecursiva(Node node1, Node node2, int nivelNode1, int nivelNode2) {
        if(node1.getPare()==null || node2.getPare()==null){
            return null;
        }
        if(nivelNode1 > nivelNode2) {
            return PareComuCercaRecursiva(node1.getPare(), node2, nivelNode1 - 1, nivelNode2);
        }else{
            if(nivelNode1 < nivelNode2) {
                return PareComuCercaRecursiva(node1, node2.getPare(), nivelNode1, nivelNode2 - 1);
            }else{
                if(node1.getPare() == node2.getPare()) {
                    if(node1.getPare().getCasa() != node1.getCasa() && node1.getPare().getCasa() != node2.getCasa()) {
                        return node1.getPare();
                    }else{
                        return PareComuCercaRecursiva(node1.getPare(), node2.getPare(), nivelNode1 - 1, nivelNode2 - 1);
                    }
                }
            }
        }
        return null;
    }


    public static int encontrarNivelNodoRecursiu(Node actual,int nivel) {
        if(actual.getPare() == null) {
            return nivel;
        }else{
            nivel++;
            return encontrarNivelNodoRecursiu(actual.getPare(),nivel);
        }
    }*/
}

