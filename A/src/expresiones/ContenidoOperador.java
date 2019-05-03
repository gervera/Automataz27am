package expresiones;

import operadores.Operador;
import simbolos.Identificador;
import simbolos.Valor;
import simbolos.Variable;

public class ContenidoOperador extends Contenido {

    private Operador operador;

    public ContenidoOperador(Operador operador){
        this.operador = operador;
    }

    @Override
    public Valor calcular(Valor izq, Valor der) {
        ManejadorExpresiones.aumentarContador();
        String nombre = ManejadorExpresiones.construirNombreTemp();
        String nombreOperadorIzq = ((Identificador) izq).getNombre();
        String nombreOperadorDer = ((Identificador) der).getNombre();
        System.out.println(nombre+" = "+nombreOperadorIzq+" "+operador+" "+nombreOperadorDer);
        Valor valor = operador.operar(izq,der);
        Variable varTemp = new Variable(izq.getToken(),nombre,"temporal",valor.getTipoDeDato());
        varTemp.setDato(valor.getDato());
        return varTemp;
    }

    @Override
    public int getPrecedencia(int aumento) {
        return operador.getPrecedencia()+aumento;
    }

    @Override
    public String toString() {
        return operador.getNombre();
    }
}
