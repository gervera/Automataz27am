package lenguaje;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class scanner 
{
	static Vector<String> Partes;
    static LinkedList<Token> tokens;
    static boolean resultado;
    static String informe;
    
    public scanner(File archivo)
    {
    	informe="";
    	resultado=true;
    	tokens = new LinkedList<>();
    	
    	Partes = LeerTXT(archivo);
    	resultado=AnalizadorLexico();
    }
    
    public Vector<String> LeerTXT(File archivo)
    {
        Vector<String> vector = new Vector<>();
        String line;
        String[] partes;
        try 
        {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            int ln = 1;
            while((line = br.readLine()) != null)
            {
                partes = line.split(" ");
                for(int i = 0; i < partes.length; i++)
                {
                    partes[i] = ln + " " + partes[i];
                }
                ln++;
                vector.addAll(Arrays.asList(partes));
            }
        }
        catch (IOException e)
        {

        }
        
        return vector;
    }
    
    public boolean AnalizadorLexico()
    {
        for (String token : Partes)
        {
            if(token.split(" ").length == 1)
            	continue;
            String s = token.split(" ")[1];
            int linea = Integer.parseInt(token.split(" ")[0]);
            String tipo;
            
            if(palabrasC(s)) 
            {
                tipo = "Palabra_Clave";
                if("class".equals(s))
                	tokens.add(new Token(s, tipo, linea, 1));
                else if("while".equals(s))
                	tokens.add(new Token(s, tipo, linea, 2));
                else if("System.out.println".equals(s))
                	tokens.add(new Token(s, tipo, linea, 3));
            }
            else if(tipo(s)) 
            {
                tipo = "Tipo";
                if("int".equals(s))
                	tokens.add(new Token(s, tipo, linea, 4));
                else
                	tokens.add(new Token(s, tipo, linea, 5));
            }
            else if(Boleanos(s)) 
            {
                tipo = "Booleano";
                if("true".equals(s))
                	tokens.add(new Token(s, tipo, linea, 6));
                else
                	tokens.add(new Token(s, tipo, linea, 7));
            }
            else if(simbolos(s)) 
            {
                tipo = "Simbolo";
                if("{".equals(s))
                	tokens.add(new Token(s, tipo, linea, 8));
                else if("}".equals(s))
                	tokens.add(new Token(s, tipo, linea, 9));
                else if("(".equals(s))
                	tokens.add(new Token(s, tipo, linea, 10));
                else if(")".equals(s))
                	tokens.add(new Token(s, tipo, linea, 11));
                else if(";".equals(s))
                	tokens.add(new Token(s, tipo, linea, 12));
            }
            else if(operador(s)) 
            {
                tipo = "Operador";
                if("=".equals(s))
                	tokens.add(new Token(s, tipo, linea, 13));
                else if("+".equals(s))
                	tokens.add(new Token(s, tipo, linea, 14));
                else if("-".equals(s))
                	tokens.add(new Token(s, tipo, linea, 15));
                else if("*".equals(s))
                	tokens.add(new Token(s, tipo, linea, 16));
                else if("<".equals(s))
                	tokens.add(new Token(s, tipo, linea, 17));
            }
            else if(entero(s))
            {
                tipo = "Numero";
                tokens.add(new Token(s, tipo, linea, 18));
            }
            else if(identificador(s)) 
            {
                tipo = "Identificador";
                tokens.add(new Token(s, tipo, linea, 19));
            }
            else
            {
            	otros(s, linea);
            }
        }
        boolean b = true;
        int c = 0;
        String s="";
        for(Token t : tokens)
        {
            if(t.getType().equals("NULL")) 
            {
                b = false;
                c++;
                s=s+"Error Lexico en linea " + t.getLine() + " Valor: " + t.getValue() +"\n";
            }
        }
        if(b)
        	informe=informe+"Parte lexica del programa correcta"+"\n";
        else
        	informe=informe+"Se detectó que hay "+ c + ((c == 1)? " error Lexico" : " errores Lexicos") +"\n"+s;
        
       
        return b;
    }
    
    private boolean palabrasC(String s)
    {
        String[] keywords = {"class", "while","System.out.println"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean tipo(String s)
    {
        String[] keywords = {"int", "boolean"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean Boleanos(String s)
    {
        String[] keywords = {"true", "false"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean simbolos(String s)
    {
        String[] keywords = {"{", "}", "(", ")", ";"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean operador(String s)
    {
        String[] keywords = {"=", "+", "-", "*", "<"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean entero(String s)
    {
        try
        {
            return Integer.parseInt(s) >= 0;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    private boolean identificador(String s)
    {
        char firstChar = s.charAt(0);
        if(!(Character.isLetter(firstChar))) return false;
        for(int i = 1; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(!(Character.isLetter(c) || Character.isDigit(c) )) return false;
        }
        return true;
    }

    private void otros(String s, int line)
    {
        if(s.equals("")) return;
        String rest;
        char c = s.charAt(0);

        if(simbolos(c + ""))
        {
        	String ss=c + "";
        	if("{".equals(ss))
            	tokens.add(new Token(c+"", "Simbolo", line, 8));
            else if("}".equals(ss))
            	tokens.add(new Token(c+"", "Simbolo", line, 9));
            else if("(".equals(ss))
            	tokens.add(new Token(c+"", "Simbolo", line, 10));
            else if(")".equals(ss))
            	tokens.add(new Token(c+"", "Simbolo", line, 11));
            else if(";".equals(ss))
            	tokens.add(new Token(c+"", "Simbolo", line, 12));
            rest = s.substring(1, s.length());
        }
        else if(Character.isDigit(c))
        {
            String temp = c + "";
            for(int i = 1; i < s.length(); i++)
            {
                String newChar = s.charAt(i) + "";
                if(simbolos(newChar) || operador(newChar)) break;
                temp += newChar;
            }
            if(entero(temp))
                tokens.add(new Token(temp, "Numero", line, 18));
            else
                tokens.add(new Token(temp, "NULL", line, 0));
            rest = s.substring(temp.length(), s.length());
        }
        else if (Character.isLetter(c) || c == '_')
        {
            String temp = c + "";
            for(int i = 1; i < s.length(); i++)
            {
                String newChar = s.charAt(i) + "";
                if(simbolos(newChar) || operador(newChar)) break;
                temp += newChar;
            }
            if(palabrasC(temp))
            {
            	if("class".equals(temp))
                	tokens.add(new Token(temp, "Palabra_Clave", line, 1));
                else if("while".equals(temp))
                	tokens.add(new Token(temp, "Palabra_Clave", line, 2));
                else if("System.out.println".equals(temp))
                	tokens.add(new Token(temp, "Palabra_Clave", line, 3));
            }
            else if(tipo(temp))
            {
            	if("int".equals(temp))
                	tokens.add(new Token(temp, "Tipo", line, 4));
                else
                	tokens.add(new Token(temp, "Tipo", line, 5));
            }
            else if(Boleanos(temp))
            {
            	if("true".equals(temp))
                	tokens.add(new Token(temp, "Booleano", line, 6));
                else
                	tokens.add(new Token(temp, "Booleano", line, 7));
            }
            else if(identificador(temp))
                tokens.add(new Token(temp, "Identificador", line, 19));
            else
                tokens.add(new Token(temp, "NULL", line, 0));
            rest = s.substring(temp.length(), s.length());
        }
        else if(operador(c+""))
        {
            String temp = c + "";
            if(s.length() > 1 && operador(s.substring(0, 2)))
                temp += s.charAt(1);  
            if("=".equals(temp))
            	tokens.add(new Token(temp, "Operador", line, 13));
            else if("+".equals(temp))
            	tokens.add(new Token(temp, "Operador", line, 14));
            else if("-".equals(temp))
            	tokens.add(new Token(temp, "Operador", line, 15));
            else if("*".equals(temp))
            	tokens.add(new Token(temp, "Operador", line, 16));
            else if("<".equals(temp))
            	tokens.add(new Token(temp, "Operador", line, 17));
            rest = s.substring(temp.length(), s.length());
        }
        else
        {
            tokens.add(new Token(c+"", "NULL", line, 0));
            rest = s.substring(1, s.length());
        }

        otros(rest, line);
    }


}
