package tiposDeDatos;

public class ObjetoError {

    String valor;

    public ObjetoError(String str){
        valor = str;
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
