package tiposDeDatos;

import errores.SemanticError;
import operadores.Operador;
import operadores.OperadorUnario;

public class Booleano implements TipoDeDato<Boolean> {

    @Override
    public Boolean parseDato(String stringDato) {
        try {
            return Boolean.parseBoolean(stringDato);
        } catch (Error e){
            throw new SemanticError("Tipo de dato incompatible");
        }

    }

    @Override
    public Boolean operar(Operador op, Object op1, Object op2) throws Exception {
        return (Boolean)op.operar((Boolean)op1,(Boolean)op2);
    }

    @Override
    public Boolean operar(OperadorUnario op, Object op1) throws Exception {
        return (Boolean)op.operar((Boolean)op1);
    }

    @Override
    public TiposDeDatos getEnum() {
        return TiposDeDatos.BOOLEAN;
    }



    @Override
    public String toString() {
        return "Booleano";
    }
}
