package simbolos;

import analizadorSintactico.CustomToken;
import errores.ManejadorErrores;
import errores.SemanticError;
import tiposDeDatos.TipoError;

import java.util.HashMap;

public class TablaDeSimbolos {

    HashMap<String,Identificador> simbolos = new HashMap();

    public void agregarIdentificador(Identificador identificador){
        if (!simbolos.containsKey(identificador.getToken().image)) {
            simbolos.put(identificador.getToken().image, identificador);
        } else {
            String reason = "La variable \""+identificador.getToken().image+"\" ya esta declarada";
            ManejadorErrores.agregarError( new SemanticError(
                    identificador.getToken().beginLine,
                    identificador.getToken().beginColumn,
                    reason)
            );
        }
    }

    public Identificador obtenerIdentificador(CustomToken token){
        try {
            return obtenerIdentificador(token.image);
        } catch (Error e){
            String reason = "La variable \""+token.image+"\" no existe";
            ManejadorErrores.agregarError( new SemanticError(
                    token.beginLine,
                    token.beginColumn,
                    reason)
            );
            Variable error = new Variable(token,"error","error",new TipoError());
            return error;
        }

    }

    private Identificador obtenerIdentificador(String image) throws Error{
        if (simbolos.containsKey(image)){
            return simbolos.get(image);
        } else {
            throw new Error("No se encontro el identificador \""+image+"\"");
        }
    }

    public void print(){
        String format = "%-10s %-10s %-25s %-15s %s\n";
        System.out.printf(format,"Alcance","Tipo","Posicion","Nombre","Valor");
        System.out.println();

        for (String key: simbolos.keySet()){
            Variable var = (Variable) simbolos.get(key);
            String tipoDeDato = var.getTipoDeDato().toString();
            String alcance = var.getAlcance();
            String posicion = "linea "+var.getToken().beginLine + ", Columna "+var.getToken().beginColumn;
            String valor;
            try {
                valor = var.getDato().toString();
            }catch (NullPointerException e){
                valor = "null";
            }
            System.out.printf(format,alcance,tipoDeDato,posicion,key,valor);
        }
    }
    
}
