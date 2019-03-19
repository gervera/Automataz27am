package Compiler2;

public class Scanner {
	
	private Compiler compiler;
    
	// Declarations
    private int k;
    public final String[] tokens;
    public final String[] typeToken;
    private String tipoToken;
    private String token;
    private final String[] reservedWords = {"class", "{","}","(",")", "boolean", "int", "while", "System.out.println", "true","false"};
    private final String[] operators = {"<", "+", "-" , "*","="};
    private final String delimiter = ";";
    
    // Constructor
    public Scanner(String codigo, Compiler compiler) {    
    	this.compiler = compiler;
        tokens = codigo.split("\\s+");
        typeToken = new String[tokens.length];
        k = 0;
        token = "";
        
        System.out.println("++++---> Logs <---++++\n\ntok | t\n----------");
    }
    
    // It return valid tokens for the parser
    public String getToken(boolean b) {
        boolean tokenValido = false;
        token = tokens[k];
        if (b) 
        	if (k < tokens.length-1) 
        		k++;
        
        // Lexic verification
        // Reserved Words
        for (String reserved : reservedWords) 
            if (token.equalsIgnoreCase(reserved)) {
                tokenValido = true;
                typeToken[k] = "Reser. Word";
                setTipoToken("Reser. Word", b);
                break;
            }
        
        // Operators:
        if (!tokenValido) 
            for (String operator : operators) {
                if (token.equals(operator)) {
                    tokenValido = true;
                    typeToken[k] = "Operator";
                    setTipoToken("Operator", b);
                    break;
                }
            }
        
        // Delimiter:
        if (!tokenValido) 
            if (token.equals(delimiter)) {
                tokenValido = true;
                typeToken[k] = "Delimiter";
                setTipoToken("Delimiter", b);
            }
        
        
        // Identifier:
        if (!tokenValido) 
            if (isIdentifier(token)) {
                tokenValido = true;
                typeToken[k] = "Identifier";
                setTipoToken("Identifier", b);
            }
        
        // Number:
        if (!tokenValido)
        	if (isNumber(token)) {
        		tokenValido = true;
        		typeToken[k] = "Is Number";
        		setTipoToken("Is Number", b);
        	}
        		
        // Error:
        if (!tokenValido) {
            error(token, "( error! )");
            typeToken[k] = "Invalid Token!";
            return "Invalid Token!";
        }
        
        return token;
    }
    
    // Check if it's a identifier
    public boolean isIdentifier(String t) {
        boolean isToken = false;
        char[] charArray;
        charArray = t.toCharArray();
        int i=0;
        
       // Validation of the first character:
        if((charArray[i]>='a' && charArray[i]<='z') || 
                (charArray[i] >= 'A' && charArray[i] <= 'Z') ||
                (charArray[i]=='_') || (charArray[i]=='-')){
            isToken = true;
        }
        
        // Validation of the rest of the token (If its length is more than 1):
        if(t.length() > 1 && isToken) 
            for(int j=1 ; j<charArray.length ; j++) {
                if((charArray[j]>='a' && charArray[j]<='z') || 
                (charArray[j] >= 'A' && charArray[j] <= 'Z') ||
                (charArray[j]=='_') || (charArray[j]=='-') || (charArray[j]>='0' && charArray[j]<='9')){
                    isToken = true;
                }
            }
        
        else if(t.length() > 1 && isToken) 
            isToken = false;
        
        return isToken;
    }
      
    // Check if it's a number
    public boolean isNumber(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } 
        catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
        
    public void setTipoToken(String tipo, boolean b) {
        if (b) tipoToken = tipo;       
    }
    
    public String getTipoToken() {
        return tipoToken;
    }    

    public String checkNextToken() {
        return tokens[k];
    }
    
    // Convert String-Token to Int-Token
    public int stringToCode(String t) {
        int code = 0;

        switch(t) {
            case "class": 				code = 1; break;    
            case "boolean": 			code = 2; break;
            case "int": 				code = 3; break;
            case "while": 				code = 4; break;
            case "System.out.println": 	code = 5; break;
            case "true": 				code = 6; break;
            case "false": 				code = 7; break;
            case "<": 					code = 8; break;
            case "+": 					code = 9; break;
            case "-": 					code = 10; break;
            case "*": 					code = 11; break;
            case ";": 					code = 12; break;
            case "{": 					code = 13; break;
            case "}": 					code = 14; break;
            case "(": 					code = 15; break;
            case ")": 					code = 16; break;
            case "=": 					code = 17; break;
            default:  
    			if (isNumber(t)) code = 18;
    			else if (isIdentifier(t)) code = 19; 
    			else code = 20;
    			break;
    		}
    		return code;
    }

	// It shows an error
	public void error(String token, String t) {
		System.out.println("Has ocurred an error! Error:\n"
                        + "The token: ( "+ token + " ) doesn't match the grammar!\n"
                        + "Was expected: " + t + ".\n");
		
		compiler.consoleArea.append("\n\n" 
                        + "Has ocurred an error! Error:\n"
                        + "The token: ( "+ token + " ) doesn't match the grammar!\n"
                        + "Was expected: " + t + ".\n");
	}
    
}
