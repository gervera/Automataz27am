package expresiones;

import simbolos.Identificador;
import simbolos.Valor;
import simbolos.Variable;

public class ExpresionAsignacion {

    private Identificador destino;
    private Expresion expresion;

    public ExpresionAsignacion(Identificador destino,Expresion expresion){
        this.destino = destino;
        this.expresion = expresion;
    }

    public void ejectuar() {
        ManejadorExpresiones.resetContador();
        Valor result = expresion.obtenerArbol().calcular();
        System.out.println(destino.getNombre()+" = "+ManejadorExpresiones.construirNombreTemp());

        ((Variable)destino).setDato(result.getDato());
    }

}
