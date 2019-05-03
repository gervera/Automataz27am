package tiposDeDatos;


import operadores.Operador;
import operadores.OperadorUnario;

public interface TipoDeDato <T>{

    T parseDato(String stringDato);

    T operar(Operador op,Object op1, Object op2) throws Exception ;
    T operar(OperadorUnario op, Object op1) throws Exception ;

    TiposDeDatos getEnum();

}
