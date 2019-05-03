package simbolos;
import analizadorSintactico.CustomToken;
import analizadorSintactico.Token;
import tiposDeDatos.TipoDeDato;

public class Variable extends Identificador{

    public Variable(CustomToken token, String nombre, String alcance, TipoDeDato tipoDeDato) {
        super(token, nombre, alcance, null, tipoDeDato);
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }
}
