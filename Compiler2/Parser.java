package Compiler2;

import java.util.Vector;
import org.apache.commons.lang3.ArrayUtils;

import SintacticTree.*;


public class Parser {
	
	Programx Px;
	private Scanner scanner;
	private Compiler compiler;
	private Vector tablaSimbolos = new Vector();
	private Vector St = new Vector();
	private boolean OK = true;
	String code;
	String[] variable;
	String[] tipo = null;
	final int classx = 1, booleanx = 2, intx = 3, whilex = 4, printx = 5, truex = 6, falsex = 7, lessThan = 8, sumx = 9, subx = 10, 
			  mulx = 11, semix = 12, beginx = 13, endx = 14, parenBegin = 15, parenEnd = 16, equalx=17, integerx=18,  idx=19; 
	String token, currentToken, log; 
	int codeToken, expectedToken; 
    
	public Parser(String codigo, Compiler compiler) {
		this.compiler = compiler;
		code = codigo;
		scanner = new Scanner(codigo, compiler);
        token = scanner.getToken(true);
        codeToken = scanner.stringToCode(token);
        Px = P();
		System.out.println("\nType\t\tToken\n-----------------------");
        int c = 0;
        scanner.typeToken[0] = "Reser. Word";
        for (String token : scanner.tokens)
        	System.out.println(scanner.typeToken[c++] + "\t"  + token);
		System.out.println("-----------------------");
        OK();
	}
	
	// Next Token
	public void advance() {
		token = scanner.getToken(true);
	    currentToken = scanner.getToken(false);
	    codeToken = scanner.stringToCode(token);
	} 
	
	// Expected Token
	public void eat(int t) {
		System.out.println(codeToken + " - " + t);
		expectedToken = t;
	    if (codeToken == t) {
	    	setLog("Token: " + token + "\n" + "Type:  "+ scanner.getTipoToken());
	        advance();
	    }
	    else {
	    	scanner.error(token, "Token Type: " + t);
	    	OK = false;
	    }
	    	 
	}
	
	// Programx
	public Programx P() {
		Declarax d;
		Statx s;

		if (codeToken == classx) {
			eat(classx);
			
			if (codeToken == idx) {
				eat(idx); d = D();
				// Here stayed a thing
				
				if (codeToken == beginx) {
					createTable();
					eat(beginx); s = S();  
					return new Programx(tablaSimbolos, s); // Sintactic Three
				}
				else {
					scanner.error(token, "( { )");
			    	OK = false;
					return null;
				}
			}
			else {
				scanner.error(token, "( Identifier )");
		    	OK = false;
				return null;
			}
		}
		else {
			scanner.error(token, "( class )");
	    	OK = false;
			return null;
		}
	}

	// Declarations
	public Declarax D() {
		String s;
		
		if(codeToken == intx || codeToken == booleanx) {
			Typex t = T();
			if (codeToken == idx) {
	          s = token; eat(idx); eat(semix); D();
	          tablaSimbolos.addElement(new Declarax(s, t)); // Symbol Table
	          return new Declarax(s, t);
	        }
	        else { return null; }
		}

	    else if (codeToken != idx) { return null; }
	    else {
	    	scanner.error(token, "( id )");
		    OK = false;
	        return null;
	    } 
	}
	
	// Type of var
	public Typex T() {
		
		if (codeToken == intx) {
            eat(intx);
            return new Typex("int");
        }
        else if (codeToken == booleanx) {
            eat(booleanx);
            return new Typex("boolean");
        }
        else {
            scanner.error(token, "( int | boolean )");
	    	OK = false;
            return null;
        }
	}
	
	// Statement
	public Statx S() {
		Expx e;
		Statx s;
		Idx id;
		
		if (codeToken == endx) {
			eat(endx);
			System.out.println("----------");
			return null;
		}
		
		switch (codeToken) {
			case whilex: eat(whilex); eat(parenBegin); e = E(); eat(parenEnd); s = S(); S(); St.addElement(new S(s, e, null,  1));
						 return new Whilex(e, s);

			case idx: id = new Idx(token); declarationCheck(token); eat(idx); eat(equalx); e = E(); eat(semix); S(); St.addElement(new S(null, e, id, 2));
						
						// Check types
						variable = new String[tablaSimbolos.size()];
				        tipo = new String[tablaSimbolos.size()];
				        
				        Declarax dx;
				        for (int i=0; i<tablaSimbolos.size(); i++) {
				            dx = (Declarax)tablaSimbolos.get(i);
				            variable[i] = dx.id;
				            tipo[i] = dx.type.getTypex();
				            if (id.getIdx().equals(variable[i])) {
				            	if (tipo[i].equals("int") && !isNumber(e)) {
				            		scanner.error(variable[i], "( A Integer assignment was expected! )");
				            		OK = false;
				            		
				            		// Determine position of var
				                    int line = 0, col = 0;
				                    String vec[] = code.split("\n");
				                    for (int j = 0; j < vec.length; j++) {
				                    	String chain = variable[i] + " =";
				                    	if (vec[j].indexOf(chain) != -1) {
				                    		line = j + 1;
				                    		col = vec[j].indexOf(chain);
				                    		break;
				                    	} 
				                    }
				                    Boolx box = (Boolx) e;
				                    System.out.println("Var: " + variable[i] + 
				                    				   "Type: " +  tipo[i] + 
				                    				   "Error: ( " + box.getBool() + " )" +  
				                    				   " - (Line." + line + " | Col." + col + ")");
				                    compiler.consoleArea.append("Var: " + variable[i] + 
				                    				   "\nType: " +  tipo[i] + 
				                    				   "\nError: ( " + box.getBool() + " )" +  
				                    				   " - (Line." + line + " | Col." + col + ")\n");
				            	}
				            	else if (tipo[i].equals("boolean") && !isBool(e)) {
				            		scanner.error(variable[i], "( A Boolean assignment was expected! )");
				            		OK = false;
				            		
				            		// Determine position of var
				                    int line = 0, col = 0;
				                    String vec[] = code.split("\n");
				                    for (int j = 0; j < vec.length; j++) {
				                    	String chain = variable[i] + " =";
				                    	if (vec[j].indexOf(chain) != -1) {
				                    		line = j + 1;
				                    		col = vec[j].indexOf(chain);
				                    		break;
				                    	} 
				                    }
				                    Intx inx = (Intx) e;
				                    System.out.println("Var: " + variable[i] + 
				                    				   "\nType: " +  tipo[i] + 
				                    				   "\nError: ( " + inx.entero + " )" +  
				                    				   " - (Line." + line + " | Col." + col + ")");
				                    compiler.consoleArea.append("Var: " + variable[i] + 
				                    				   "\nType: " +  tipo[i] + 
				                    				   "\nError: ( " + inx.entero + " )" +  
				                    				   " - (Line." + line + " | Col." + col + ")\n");
				            	}
				            }
				            System.out.println("Var " + variable[i] + " Type " + tipo[i]);
				        }
			
						return new Asignax(id, e);
                		
                		
			case printx: eat(printx); eat(parenBegin); e = E(); eat(parenEnd); eat(semix); S(); St.addElement(new S(null, e, null, 3));
                		 return new Printx(e);
                
			default: scanner.error(token, "( while | id | System.out.println )");
	    			 OK = false;
					 return null;
		}
		
	}
	
	// Expression
	public Expx E() {
		String a, b;
		
		switch (codeToken) {
			case truex: a = token; eat(truex);
						return new Boolx(a);
				
			case falsex: a = token; eat(falsex);
						 return new Boolx(a);
				
			case integerx: a = token; eat(integerx);
						  return new Intx(a);
			
			case idx: a = token; eat(idx);
					switch (codeToken) {
						case lessThan: eat(lessThan); b = token; eat(idx);
									   return new Oxpx(a, b, "<");
						
						case sumx: eat(sumx); b = token; eat(idx);
								   return new Oxpx(a, b,"+");
						
						case subx: eat(subx); b = token; eat(idx);
								   return new Oxpx(a, b,"-");
						
						case mulx: eat(mulx); b = token; eat(idx);
								   return new Oxpx(a, b,"*");
							
						default: return new Idx(a);
					}
			default: scanner.error(token, "( id | true | false | integer )");
	    			 OK = false;
					 return null;
		}	
	}
	
	// Identifier
	public Typex Id() {
		if (codeToken == intx) {
            eat(intx);
            return new Typex("int");
        }
		else {
            scanner.error(token, "(int)");
	    	OK = false;
            return null;
        }
	}
	
	// Everything is OK
	public void OK() {
		if (OK) {
			System.out.println("\n> Everything is OK!");
			compiler.consoleArea.append("\n> Everything is OK!");
	        new Triple(Px, tablaSimbolos, variable, tipo, St, compiler, code);
		}
	}
	
    // Travel of the Left Part Tree and Creation of the Symbol Table
    public void createTable() {
        variable = new String[tablaSimbolos.size()];
        tipo = new String[tablaSimbolos.size()];
        
        Declarax dx;
        for (int i=0; i<tablaSimbolos.size(); i++) {
            dx = (Declarax)tablaSimbolos.get(i);
            variable[i] = dx.id;
            tipo[i] = dx.type.getTypex();
        }
        ArrayUtils.reverse(variable);
        ArrayUtils.reverse(tipo);
        
        // Check if the variable had already been declared
        for (int i = variable.length-1; i >=0; i--) {
	        for (int j = 0; j < variable.length; j++) {
				if (j!=i && variable[j].equals(variable[i])) {
					scanner.error(variable[j], "( The variable had already been declared! )");
					OK = false;int line = 0, col = 0;
		            
		            // Determine position of var
		            String vec[] = code.split("\n");
		            for (int k = 0; k < vec.length; k++) {
		            	String chain = tipo[i] + " " + variable[i];
		            	if (vec[k].indexOf(chain) != -1) {
		            		line = k + 1;
		            		col = vec[k].indexOf(chain);
		            		break;
		            	} 
		            }
		            System.out.println(variable[i] + " : " +  tipo[i] + "\t(Line." + line + " | Col." + col + ")");
		            compiler.consoleArea.append(variable[i] + " : " + tipo[i] + "\t(Line." + line + " | Col." + col + ")\n");
		            		
					break;
				}
			}
			
		}
    }
    
	// Line Breaks
    public void setLog(String l) {
        if(log == null) 
            log = l + "\n \n";

        else
            log = log + l + "\n \n";
    }
    
    // Verification of Variable Declarations by Consulting the Table of Symbols
    public void declarationCheck(String s) {
        boolean valido = false;
        for (int i=0; i<tablaSimbolos.size(); i++) {
            if (s.equals(variable[i])) {
                valido = true;
                break;
            }
        }
        if (!valido) {
        	OK = false;
            System.out.println("\nHas ocurred an error!\nThe varible [ " + s + " ] isn't declared!\nThe execution was stopped!.");
            compiler.consoleArea.append("\nHas ocurred an error!\nThe varible [ " + s + " ] isn't declared!\nThe execution was stopped!.");
            
            // Determine position of var
            int line = 0, col = 0;
            String vec[] = code.split("\n");
            for (int j = 0; j < vec.length; j++) {
            	String chain = s + " ";
            	if (vec[j].indexOf(chain) != -1) {
            		line = j + 1;
            		col = vec[j].indexOf(chain);
            		break;
            	} 
            }
            System.out.println("\n" + s + " - (Line." + line + " | Col." + col + ")");
            compiler.consoleArea.append("\n" + s + " - (Line." + line + " | Col." + col + ")\n");
        }
    }


	//Check If It's a Boolean
	public boolean isBool(Expx exp) {
	    try {
	    	Boolx box = (Boolx) exp;
	        return true;
	    } 
	    catch (Exception excepcion) {
	        return false;
	    }
	}
	
	// Check If It's a Number
	public boolean isNumber(Expx exp) {
	    try {
	    	Intx inx = (Intx) exp;
	        return true;
	    } 
	    catch (Exception excepcion) {
	        return false;
	    }
	}
       
}

class S {
	public Statx Statement;
	public Expx Expression;
	public Idx Identifier;
	public int Type;
	
	public S(Statx s, Expx e, Idx id, int t) {
		Statement = s;
		Expression = e;
		Identifier = id;
		Type = t;
	}

}
