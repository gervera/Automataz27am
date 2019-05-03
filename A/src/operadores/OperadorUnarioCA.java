package operadores;

import errores.ManejadorErrores;
import errores.SemanticError;
import simbolos.Valor;

import tiposDeDatos.ObjetoError;
import tiposDeDatos.TipoError;

public abstract class OperadorUnarioCA implements  OperadorUnario {

    private String nombre;

    public OperadorUnarioCA(String nombreOperacion){
        this.nombre = nombreOperacion;
    }

    public Valor operar(Valor op1){
        if (op1.getDato() instanceof ObjetoError){
            String reason = "No se puede operar con errores";
            ManejadorErrores.agregarError( new SemanticError(
                    op1.getToken().beginLine,
                    op1.getToken().beginColumn,
                    reason)
            );
            return new Valor(op1.getToken(),TipoError.generarObjetoError("error"),new TipoError());
        } else {
            try {
                Object nuevoDato = op1.getTipoDeDato().operar(this, op1.getDato());
                return new Valor(op1.getToken(), nuevoDato, op1.getTipoDeDato());
            } catch (Exception e) {
                String reason = e.getMessage();
                ManejadorErrores.agregarError(new SemanticError(
                        op1.getToken().beginLine,
                        op1.getToken().beginColumn,
                        reason)
                );
                return new Valor(op1.getToken(), TipoError.generarObjetoError("error"), new TipoError());
            }
        }
    }

    @Override
    public Object operar(Integer op1) throws Exception{
        errorOperacion("Entero");
        return null;
    }

    @Override
    public Object operar(Boolean op1) throws Exception {
        errorOperacion("Booleano");
        return null;
    }

    @Override
    public Object operar(String op1) throws Exception {
        errorOperacion("Cadena");
        return null;
    }

    @Override
    public Object operar(Double op1) throws Exception {
        errorOperacion("Doble");
        return null;
    }

    @Override
    public int getPrecedencia() {
        return 100; //mayor que cualquier operador binario pero menor que un operando
    }

    private void errorOperacion(String tipoDato) throws Exception{
        throw new Exception("No se pueden "+nombre+" valores de tipo "+ tipoDato);
    }
    public String getNombre(){
        return nombre;
    }
}
