package simbolos;
import analizadorSintactico.CustomToken;
import tiposDeDatos.TipoDeDato;

public class Literal extends Valor{

    public Literal(CustomToken token, Object dato, TipoDeDato tipoDeDato) {
        super(token,dato, tipoDeDato);
    }
}
