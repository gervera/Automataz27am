
package operadores;

import simbolos.Valor;

public interface OperadorUnario{

  Valor operar(Valor op1);
  Object operar(Integer op1) throws Exception;
  Object operar(Boolean op1) throws Exception;
  Object operar(String op1) throws Exception;
  Object operar(Double op1) throws Exception;
  int getPrecedencia();
  String getNombre();
}
