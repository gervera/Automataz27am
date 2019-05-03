package expresiones;

import operadores.OperadorUnario;
import simbolos.Identificador;
import simbolos.Valor;
import simbolos.Variable;

public class ContenidoOperadorUnario extends Contenido {

    private OperadorUnario operador;

    public ContenidoOperadorUnario(OperadorUnario operador){
        this.operador = operador;
    }

    @Override
    public Valor calcular(Valor izq) {
        ManejadorExpresiones.aumentarContador();
        String nombre = ManejadorExpresiones.construirNombreTemp();
        String nombreOperadorIzq = ((Identificador) izq).getNombre();
        System.out.println(nombre+" = "+operador+""+nombreOperadorIzq);
        Valor valor = operador.operar(izq);
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
