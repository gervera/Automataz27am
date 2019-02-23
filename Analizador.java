package lenguaje;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

/*public class Analizador 
{
    private Vector<String> Partes = LeerTXT();
    private LinkedList<Token> tokens = new LinkedList<>();
    
    public static void main(String[] args)
    {
    	Analizador m = new Analizador();
        if(m.AnalizadorLexico())
        {
            if (m.AnalizaSintactico())
                System.out.println("No hay errores sintacticos");
            else
            	System.out.println("Existen errores Sintacticos");
        }
        else
        {
            if (m.AnalizaSintactico())
                System.out.println("No hay errores sintacticos");
            else
            	System.out.println("Existen errores Sintacticos");
        }
    }

    public Vector<String> LeerTXT()
    {
        Vector<String> vector = new Vector<>();
        String line;
        String[] partes;
        try 
        {
            FileReader fr = new FileReader("C:\\Users\\Angilberto\\gramati.txt");
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
           System.out.println("Ha ocurrido un error al leer el archivo TXT, porfavo verifique la existencia del archivo");
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
                tokens.add(new Token(s, tipo, linea));
            }
            else if(Boleanos(s)) 
            {
                tipo = "Booleano";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(tipo(s)) 
            {
                tipo = "Tipo";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(modificador(s)) 
            {
                tipo = "Modificador";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(simbolos(s)) 
            {
                tipo = "Simbolo";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(operador(s)) 
            {
                tipo = "Operador";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(entero(s))
            {
                tipo = "Numero";
                tokens.add(new Token(s, tipo, linea));
            }
            else if(identificador(s)) 
            {
                tipo = "Identificador";
                tokens.add(new Token(s, tipo, linea));
            }
            else
            {
            	otros(s, linea);
            }
        }
        boolean b = true;
        int c = 0;
        for(Token t : tokens)
        {
            if(t.getType().equals("NULL")) 
            {
                b = false;
                c++;
                System.out.println("Error en linea " + t.getLine() + " Valor: " + t.getValue());
            }
            else
            	System.out.println(t);
        }
        if(b)
        	System.out.println("Parte lexica del programa correcta");
        else
        	System.out.println("Se detectó que hay "+ c + ((c == 1)? " error Lexico" : " errores Lexicos") );
        
       
        return b;
    }

    
    private boolean palabrasC(String s)
    {
        String[] keywords = {"class", "if"};
        for(String temp : keywords)
        {
            if(temp.equals(s)) return true;
        }
        return false;
    }

    private boolean modificador(String s)
    {
        String[] keywords = {"public", "private"};
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
        String[] keywords = {"=", "+", "-", "<=", ">=", "<", ">", "==", "!="};
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
            return Integer.parseInt(s) > 0;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    private boolean identificador(String s)
    {
        char firstChar = s.charAt(0);
        if(!(Character.isLetter(firstChar) || firstChar == '_')) return false;
        for(int i = 1; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(!(Character.isLetter(c))) return false;
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
            tokens.add(new Token(c+"", "Simbolo", line));
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
                tokens.add(new Token(temp, "Numero", line));
            else
                tokens.add(new Token(temp, "NULL", line));
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
                tokens.add(new Token(temp, "Palabra_Clave", line));
            else if(Boleanos(temp))
                tokens.add(new Token(temp, "Booleano", line));
            else if(tipo(temp))
                tokens.add(new Token(temp, "Tipo", line));
            else if(modificador(temp))
                tokens.add(new Token(temp, "Modificador", line));
            else if(identificador(temp))
                tokens.add(new Token(temp, "Identificador", line));
            else
                tokens.add(new Token(temp, "NULL", line));
            rest = s.substring(temp.length(), s.length());
        }
        else if(operador(c+""))
        {
            String temp = c + "";
            if(s.length() > 1 && operador(s.substring(0, 2)))
                temp += s.charAt(1);
            tokens.add(new Token(temp, "Operador", line));
            rest = s.substring(temp.length(), s.length());
        }
        else
        {
            tokens.add(new Token(c+"", "NULL", line));
            rest = s.substring(1, s.length());
        }

        otros(rest, line);
    }


    private boolean AnalizaSintactico()
    {
        int paso = 0;
        int stment = 0;
        boolean b = true;
        boolean ret=true;
        Token marked = null;
        int lin=0;
        for(int i = 0; i < tokens.size(); i++)
        {
        	//if(marked!=null) {lin=marked.getLine();}
            if(b==false) 
            { 
            	//b=true;
            	//ret=false;
            	//System.out.println("Problemema Sintactico en la linea " + marked.getLine() + " con el token: " + marked.getValue());
            	//paso=4;
            	break;
            }

        	Token current = tokens.get(i);
            marked = current;
            
            String val = current.getValue();
            String type = current.getType();
            switch (paso)
            {
                case 0:
                    if(type.equals("Modificador"))
                    {
                        break;
                    }
                    paso++;
                case 1:
                    b = val.equals("class");break;
                case 2:
                    b = type.equals("Identificador"); break;
                case 3:
                    b = val.equals("{");break;
                case 4:
                    if(type.equals("Modificador") || type.equals("Tipo")) 
                    {
                        paso = 10; i--; continue;
                    }
                    else if(val.equals("if")) 
                    {
                        paso = 20; i--;
                        continue;
                    }
                case 5:
                    b = val.equals("}");break;
                case 6:
                    b = false; break;              
                case 10:
                    if(!type.equals("Modificador"))
                        i--;
                    break;
                case 11:
                    b = type.equals("Tipo"); break;
                case 12:
                    b = type.equals("Identificador"); break;
                case 13:
                    b = val.equals("="); break;
                case 14:
                    b = type.equals("Numero") || type.equals("Booleano");break;
                case 15:
                    b = val.equals(";");break;
                case 16:
                    if(val.equals("if")) 
                    {
                        paso = 20;
                        i--;
                        continue;
                    }
                    else b = val.equals("}");break;
                case 17:
                    b = false;break;
                case 20:
                    b = val.equals("if");break;
                case 21:
                    b = val.equals("(");break;
                case 22:
                    b = type.equals("Numero") || type.equals("Identificador");break;
                case 23:
                    b = val.equals(">") || val.equals("<") || val.equals(">=") || val.equals("<=") || val.equals("==") || val.equals("!=");break;
                case 24:
                    b = type.equals("Numero") || type.equals("Identificador");break;
                case 25:
                    b = val.equals(")"); paso = 29; break;
                case 30:
                    b = val.equals("{");
                    stment++;
                    break;
                case 31:
                    if(type.equals("Identificador"))
                    {
                        paso = 40;
                        i--;
                        continue;
                    }
                    else if(val.equals("{"))
                    {
                        paso = 30;
                        i--;
                        continue;
                    }
                    else if(val.equals("}"))
                    {
                        paso ++;
                        i--;
                        continue;
                    }
                    else b = false; break;
                case 32:
                    if(val.equals("}") && i != tokens.size()-1)
                        stment--;
                    else
                        b = false;
                    break;
                case 33:
                    if(stment > 0) 
                    {
                        paso = 32;
                        i--;
                        continue;
                    }
                    else 
                    {
                        paso = 5;i--;continue;
                    }
                case 34: b = false;break;
                case 40:
                    b = type.equals("Identificador");break;
                case 41:
                    b = val.equals("=");break;
                case 42:
                    b = type.equals("Numero");break;
                case 43:
                    b = val.equals("+") || val.equals("-");break;
                case 44:
                    b = type.equals("Numero");break;
                case 45:
                    b = val.equals(";");break;
                case 46:
                    if(val.equals("{"))
                    {
                        paso = 30;
                        i--;
                        continue;
                    }
                    else if(val.equals("}"))
                    {
                        paso = 32;
                        i--;
                        continue;
                    }
                    else b = false; break;
            }
            paso ++;
        }
        if(b)
        	System.out.println("Codigo Correcto");
        else
        	System.out.println("Problemema Sintactico en la linea " + marked.getLine() + " con el token: " + marked.getValue());

        return b;
    }

}*/
