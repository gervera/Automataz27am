package tiposDeDatos;

import java.util.Map;
import java.util.HashMap;

public enum TiposDeDatos {
    BOOLEAN,
    INT,
    DOUBLE,
    ERROR,
    STRING;

    private static final Map<TiposDeDatos, TipoDeDato> mapaDeTipoDeDatos;
    static {
        mapaDeTipoDeDatos = new HashMap<>();
        mapaDeTipoDeDatos.put(BOOLEAN,new Booleano());
        mapaDeTipoDeDatos.put(DOUBLE,new Doble());
        mapaDeTipoDeDatos.put(STRING,new Cadena());
        mapaDeTipoDeDatos.put(INT, new Entero());
        mapaDeTipoDeDatos.put(ERROR, new TipoError());
    }

    public static TipoDeDato getTipoDeDato(String tipoDeDatoString){
        return mapaDeTipoDeDatos.get(getEnumerator(tipoDeDatoString));
    }


    public static TiposDeDatos getEnumerator(String tipoDeDatoString){
        return TiposDeDatos.valueOf(tipoDeDatoString.toUpperCase());
    }

}
