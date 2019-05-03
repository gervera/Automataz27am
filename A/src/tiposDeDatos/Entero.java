package tiposDeDatos;

import errores.SemanticError;
import operadores.Operador;
import operadores.OperadorUnario;

public class Entero implements TipoDeDato<Integer> {

    @Override
    public Integer parseDato(String stringDato) {
        try {
            return Integer.parseInt(stringDato);
        } catch (Error e){
            throw new SemanticError("Tipo de dato incompatible");
        }
    }

    @Override
    public Integer operar(Operador op, Object op1, Object op2) throws Exception  {
        return (Integer)op.operar((Integer) op1,(Integer)op2);
    }

    @Override
    public Integer operar(OperadorUnario op, Object op1) throws Exception {
        return (Integer)op.operar((Integer)op1);
    }

    @Override
    public TiposDeDatos getEnum() {
        return TiposDeDatos.INT;
    }

    @Override
    public String toString() {
        return "Entero";
    }
}
