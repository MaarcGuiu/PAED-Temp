package Fase1.Grafs.ExMapaSimplificat;
import java.util.ArrayList;
import java.util.List;

import Fase1.Grafs.Lloc;
import Fase1.Grafs.Ruta;




public class MapaSimplificat {

    public static void main(List <Lloc> llocs, List <Ruta> rutes,Ruta[][] matriuAdj) {
        System.out.println("El mapa simplificat està format per les següents rutes:");
        int SPAMING_TREE =llocs.size()-1;
       List <Ruta> rutesSimplificades=KruskalMapaSimplificat(rutes,matriuAdj,rutes.size(),SPAMING_TREE);
        for (int i = 0; i < rutesSimplificades.size(); i++) {
                    Ruta ruta = rutesSimplificades.get(i);
                    Lloc llocA = ruta.getLlocA();
                    Lloc llocB = ruta.getLlocB();
                    System.out.println("Ruta "+llocA.getId()+"-"+llocB.getId()+":"+llocA.getNom()+"-"+llocB.getNom()+"("+ruta.getDistancia()+" km )");
        }
    }

    public static List<Ruta>  KruskalMapaSimplificat(List <Ruta> rutes, Ruta[][] matriuAdj, int numRutes, int SPAMING_TREE){
      List <Ruta> MST = new ArrayList<>();
        rutes.sort((Ruta r1, Ruta r2) -> Double.compare(r1.getDistancia(), r2.getDistancia()));

        List <Ruta> rutesAuxiliars = new ArrayList<>();

        int h = 0;

        while(h < rutes.size()) {
            rutesAuxiliars.add(rutes.get(h));
            h++;
        }

        boolean [][] visitat= new boolean[matriuAdj.length][matriuAdj.length];
        int arestesMST=0;
        while (!rutesAuxiliars.isEmpty() && arestesMST<=SPAMING_TREE){
            Ruta actual=rutesAuxiliars.remove(0);
            int indexA=IndexLloc(actual.getLlocA(),actual.getLlocB(),matriuAdj,true);
            int indexB=IndexLloc(actual.getLlocA(),actual.getLlocB(),matriuAdj,false );
            Lloc llocA=matriuAdj[indexA][indexB].getLlocA();
            Lloc llocB=matriuAdj[indexA][indexB].getLlocB();

            if(!NoConectat(indexA,indexB,visitat,llocA,llocB)){
                matriuAdj[indexA][indexB].getLlocA().setVisitat(true);
                matriuAdj[indexA][indexB].getLlocB().setVisitat(true);
                visitat[indexA][indexB] = true;
                visitat[indexB][indexA] = true;
                MST.add(actual);
                arestesMST++;
            }
        }
        return MST;
    }

    public static boolean NoConectat(int indexA,int indexB,boolean [][] visitats,Lloc llocA,Lloc llocB){
        if(visitats[indexA][indexB] && visitats[indexB][indexA]){
            if(llocA.getVisitat() && llocB.getVisitat()){
                return true;
            }else{
                return false;
            }
        }else{
            if(llocA.getVisitat() && llocB.getVisitat()){
                return true;
            }else {
                return false;
            }

        }
    }

    public static int IndexLloc(Lloc LlocA,Lloc llocB,Ruta[] [] matriuAdj,boolean isI) {
        for(int i=0;i<matriuAdj.length;i++){
            for(int j=0;j<matriuAdj.length;j++){
                if(matriuAdj[i][j]!=null){
                    if(isI){
                        if(matriuAdj[i][j].getLlocA().equals(LlocA) && matriuAdj[i][j].getLlocB().equals(llocB)){
                            return i;
                        }
                    }else{
                        if(matriuAdj[i][j].getLlocA().equals(LlocA) && matriuAdj[i][j].getLlocB().equals(llocB)){
                            return j;
                        }
                    }
                }
            }
        }
        return -1;
    }

}
