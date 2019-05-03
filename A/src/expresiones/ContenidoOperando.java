package expresiones;

import simbolos.Valor;

public class ContenidoOperando extends Contenido {

    private Valor valor;

    public ContenidoOperando(Valor valor){
        this.valor = valor;
    }

    @Override
    public Valor calcular() {
        return valor;
    }

    @Override
    public int getPrecedencia(int aumento) {
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return ""+valor.getDato();
    }
}
