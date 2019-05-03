package operadores.aritmeticos;

//import operadores.Operador;
//import tiposDeDatos.Valor;

import operadores.OperadorUnarioCA;
import simbolos.Valor;
import tiposDeDatos.TipoDeDato;

public class Negacion extends OperadorUnarioCA {

    public Negacion (String nombreOperacion) {
        super(nombreOperacion);
    }

    @Override
    public Object operar(Integer op1) {
        return -op1;
    }

    @Override
    public Object operar(Double op1) {
        return -op1;
    }

    @Override
    public String toString() {
        return "-";
    }
}
