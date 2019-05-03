package operadores.aritmeticos;

//import operadores.Operador;
//import tiposDeDatos.Valor;

import operadores.OperadorCA;

public class Multiplicacion extends OperadorCA {

    public Multiplicacion(String nombreOperacion) {
        super(nombreOperacion);
    }

    @Override
    public Integer operar(Integer op1, Integer op2) {
        return op1 * op2;
    }
    
    @Override
    public Double operar(Double op1, Double op2) {
        return op1 * op2;
    }

    @Override
    public int getPrecedencia() {
        return 2;
    }

    @Override
    public String toString() {
        return "*";
    }
}
