package simbolos;

import analizadorSintactico.CustomToken;
import tiposDeDatos.TipoDeDato;

public class Valor {

    private TipoDeDato tipoDeDato;
    protected Object dato;
    private CustomToken token;

    public Valor(CustomToken token, Object dato, TipoDeDato tipoDeDato){
        this.dato = dato;
        this.tipoDeDato = tipoDeDato;
        this.token = token;
    }


    public TipoDeDato getTipoDeDato() {
        return tipoDeDato;
    }

    public Object getDato() {
        return dato;
    }

    public CustomToken getToken() {
        return token;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
