package simbolos;

import analizadorSintactico.CustomToken;
import tiposDeDatos.TipoDeDato;
import analizadorSintactico.Token;


public abstract class Identificador extends Valor{

    private String nombre;
    private String alcance;
    private CustomToken token;

    public Identificador(CustomToken token, String nombre, String alcance, Object dato, TipoDeDato tipoDeDato) {
        super(token, dato, tipoDeDato);
        this.nombre = nombre;
        this.token = token;
        this.alcance = alcance;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlcance() {
        return alcance;
    }

    public CustomToken getToken() {
        return token;
    }
}
