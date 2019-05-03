package expresiones;

import simbolos.Valor;

public class Nodo {

    public Contenido contenido;
    int precedenciaBase;
    public Nodo left;
    public Nodo right;
    public Nodo(int precedenciaBase, Contenido contenido) {
        this.precedenciaBase = precedenciaBase;
        this.contenido = contenido;
        this.left = null;
        this.right = null;
    }

    public Valor calcular(){
        if (this.right != null && this.left != null){
            return contenido.calcular(left.calcular(),right.calcular());
        } else if (this.left != null){
            return contenido.calcular(left.calcular());
        } else if (this.right != null) {
            return contenido.calcular(right.calcular());
        } else {
            return contenido.calcular();
        }
    }

    public int getPrecedencia(){
        return contenido.getPrecedencia(precedenciaBase);
    }
}
