package expresiones;

import java.util.ArrayList;

public class ManejadorExpresiones {

    private static int contador = 0;
    public static ArrayList<ExpresionAsignacion> expresiones = new ArrayList<>();

    static public void agregarExpresion(ExpresionAsignacion expresion){
        expresiones.add(expresion);
    }


    static public void calcular(){
        for (int i = 0;i<expresiones.size();i++){
            ExpresionAsignacion expresion = expresiones.get(i);
            System.out.println("Expresion: \n");
            expresion.ejectuar();
            System.out.println();
        }
    }

    static public String construirNombreTemp(){
        return "Temporal "+(contador);
    }
    static public void aumentarContador(){
    	contador ++;
    }
    static public void resetContador(){
    	contador = 0;
    }

    static public void print(){
        calcular();
    }



}
