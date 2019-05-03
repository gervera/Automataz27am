package tiposDeDatos;


import operadores.Operador;
import operadores.OperadorUnario;

public class Cadena implements TipoDeDato<String> {

    @Override
    public String parseDato(String stringDato) {
        String regexParaRemoverComillas = "(?<!\\\\)[\"]";
        return stringDato.replaceAll(regexParaRemoverComillas,"");
    }


    @Override
    public String operar(Operador op, Object op1, Object op2) throws Exception {
        return (String)op.operar((String) op1,(String) op2);
    }

    @Override
    public String operar(OperadorUnario op, Object op1) throws Exception {
        return (String)op.operar((String)op1);
    }

    @Override
    public TiposDeDatos getEnum() {
        return TiposDeDatos.STRING;
    }


    @Override
    public String toString() {
        return "Cadena";
    }
}
