package operadores.aritmeticos;

//import operadores.Operador;
//import tiposDeDatos.Valor;

import operadores.OperadorCA;
import simbolos.Valor;
import tiposDeDatos.TipoDeDato;

public class Suma extends OperadorCA {

    public Suma(String nombreOperacion) {
        super(nombreOperacion);
    }

    @Override
    public Integer operar(Integer op1, Integer op2) {
        return op1 + op2;
    }

    @Override
    public String operar(String op1, String op2) {
        return op1 + op2;
    }

    @Override
    public Double operar(Double op1, Double op2) {
        return op1 + op2;
    }

    @Override
    public int getPrecedencia() {
        return 1;
    }

    @Override
    public String toString() {
        return "+";
    }
}
