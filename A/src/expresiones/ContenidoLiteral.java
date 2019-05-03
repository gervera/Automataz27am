package expresiones;

import simbolos.Valor;
import simbolos.Variable;

public class ContenidoLiteral extends Contenido {

    private Valor valor;

    public ContenidoLiteral(Valor valor){
        this.valor = valor;
    }

    @Override
    public Valor calcular() {
        ManejadorExpresiones.aumentarContador();
        String nombre = ManejadorExpresiones.construirNombreTemp();
        System.out.println(nombre+" = "+valor.getDato());
        Variable tempVar = new Variable(valor.getToken(),nombre,"temporal",valor.getTipoDeDato());
        tempVar.setDato(valor.getDato());
        return (tempVar);
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
