package lenguaje;

public class Token 
{
	String valor;
    String tipo;
    int linea;
    int token;

    Token (String valor, String tipo, int linea, int token)
    {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.token = token;
    }

    public String getValue() 
    {
        return valor;
    }

    public String getType() 
    {
        return tipo;
    }

    public int getLine() 
    {
        return linea;
    }

    public int getToken()
    {
    	return token;
    }
    
    public String toString() 
    {
        return "Linea = " + linea + "\t\t\t Tipo = '" + tipo + '\'' +
                "\t\t\t\t Valor = '" + valor + '\''+ "\t\t\t token = '" + token + '\'';
    }

}
