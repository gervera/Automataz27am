package errores;

import java.util.ArrayList;

public class ManejadorErrores {

    public static ArrayList<Error> errores = new ArrayList<>();
    public static ArrayList<Exception> exceptions = new ArrayList<>();

    static public void agregarError(Error e){
        errores.add(e);
    }

    static public void agregarException(Exception e){
        exceptions.add(e);
    }


    static public void printErrors(){
        for (int i = 0;i<errores.size();i++){
            Error e = errores.get(i);
            System.err.println();
            e.printStackTrace();
            System.err.println();
        }
    }

    static public void printExceptions(){

        for (int i = 0;i<exceptions.size();i++){
            Exception e = exceptions.get(i);
            System.err.println();
            e.printStackTrace();
            System.err.println();
        }
    }

    static public void print(){
        printErrors();
        printExceptions();
    }


}
