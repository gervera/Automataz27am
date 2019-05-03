package expresiones;

import simbolos.Valor;

public abstract class Contenido {

    public Valor calcular(Valor op1, Valor op2){return null;}
    public Valor calcular(Valor op1){return null;}
    public Valor calcular(){return null;}
    public abstract int getPrecedencia(int aumento);

}
