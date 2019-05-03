package operadores;

import errores.ManejadorErrores;
import errores.SemanticError;
import simbolos.Valor;

import tiposDeDatos.ObjetoError;
import tiposDeDatos.TipoError;

public abstract class OperadorCA implements  Operador {

    private String nombre;

    public OperadorCA(String nombreOperacion){
        this.nombre = nombreOperacion;
    }

    public Valor operar(Valor op1, Valor op2){
        if (!sonCompatibles(op1.getTipoDeDato(), op2.getTipoDeDato()) || (op1.getDato() instanceof ObjetoError)){
            String reason = "Los valores \""+op1.getToken().image+"\" y \""+op2.getToken().image +"\" no son compatibles";
            ManejadorErrores.agregarError( new SemanticError(
                            op1.getToken().beginLine,
                            op1.getToken().beginColumn,
                            reason)
            );
            return new Valor(op1.getToken(),TipoError.generarObjetoError("error"),new TipoError());
        } else {
            try {
                Object nuevoDato = op1.getTipoDeDato().operar(this, op1.getDato(), op2.getDato());
                return new Valor(op1.getToken(), nuevoDato, op1.getTipoDeDato());
            } catch (Exception e){
                String reason = e.getMessage();
                ManejadorErrores.agregarError( new SemanticError(
                        op1.getToken().beginLine,
                        op1.getToken().beginColumn,
                        reason)
                );
                return new Valor(op1.getToken(),TipoError.generarObjetoError("error"),new TipoError());
            }
        }
    }

    public boolean sonCompatibles(Object o1, Object o2){
        if (o1.getClass() == o2.getClass()){
            return true;
        }
        return false;
    }

    @Override
    public Object operar(Integer op1, Integer op2) throws Exception{
        errorOperacion("Entero");
        return null;
    }

    @Override
    public Object operar(Boolean op1, Boolean op2) throws Exception {
        errorOperacion("Booleano");
        return null;
    }

    @Override
    public Object operar(String op1, String op2) throws Exception {
        errorOperacion("Cadena");
        return null;
    }

    @Override
    public Object operar(Double op1, Double op2) throws Exception {
        errorOperacion("Doble");
        return null;
    }

    private void errorOperacion(String tipoDato) throws Exception{
        throw new Exception("No se pueden "+nombre+" valores de tipo "+ tipoDato);
    }
    public String getNombre(){
        return nombre;
    }
}
