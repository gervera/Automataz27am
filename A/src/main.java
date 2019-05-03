import analizadorSemantico.AnalizadorSemantico;
import analizadorSintactico.AnalizadorSintactico;
import errores.ManejadorErrores;
import expresiones.ManejadorExpresiones;
import simbolos.TablaDeSimbolos;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class main {


    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
    {

        TablaDeSimbolos tablaDeSimbolos = new TablaDeSimbolos();
        FileInputStream fis;

        fis = new FileInputStream("src/archivosDeEntrada/prueba.txt");

        MarkableFileInputStream markableFIS = new MarkableFileInputStream(fis);
        markableFIS.mark(1);

        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(tablaDeSimbolos,markableFIS);
        AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(tablaDeSimbolos,markableFIS);



        try {
            analizadorSintactico.analizar();
        }catch(analizadorSintactico.ParseException e){
            System.out.println(e.toString());
        }

        System.out.println("\nTabla de simbolos");
        tablaDeSimbolos.print();
        System.out.println();

        markableFIS.reset();

        try {
            analizadorSemantico.analizar();
        }catch(analizadorSemantico.ParseException e){
            System.out.println("SEMANTICO");
            System.out.println(e.toString());
        }

        ManejadorExpresiones.print();

        Thread.sleep(100);
        ManejadorErrores.print();
        Thread.sleep(100);


        System.out.println("\nTabla de simbolos");
        tablaDeSimbolos.print();
    }

}
