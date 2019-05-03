package tiposDeDatos;

import errores.SemanticError;
import operadores.Operador;
import operadores.OperadorUnario;

public class Doble implements TipoDeDato<Double> {


    @Override
    public Double parseDato(String stringDato) {
        try {
            return Double.parseDouble(stringDato);
        } catch (Error e){
            throw new SemanticError("Tipo de dato incompatible");
        }
    }

    @Override
    public Double operar(Operador op, Object op1, Object op2) throws Exception  {
        return (Double)op.operar((Double)op1,(Double)op2);
    }

    @Override
    public Double operar(OperadorUnario op, Object op1) throws Exception {
        return (Double)op.operar((Double)op1);
    }

    @Override
    public TiposDeDatos getEnum() {
        return TiposDeDatos.DOUBLE;
    }

    @Override
    public String toString() {
        return "Doble";
    }
}
