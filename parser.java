package lenguaje;

import java.util.LinkedList;
import java.util.Vector;
import org.apache.commons.lang3.ArrayUtils;

public class parser 
{
	
	static LinkedList<Token> tokens;
	
	AST arbolSintactico;

	static int Tclass=1;
	static int Twhile=2;
	static int Timprimir=3;
	static int Tint=4;
	static int Tboolean=5;
	static int Ttrue=6;
	static int Tfalse=7;
	static int Tllave=8;
	static int TllaveInv=9;
	static int Tparentesis=10;
	static int TparentesisInv=11;
	static int Tpyc=12;
	static int Tigual=13;
	static int Tsuma=14;
	static int Tresta=15;
	static int Tmultiplicacion=16;
	static int Tmenorque=17;
	static int Tnumero=18;
	static int Tidentificador=19;
	
	static int indice;
	static int tok;
	static boolean resultado;
	static String informe;
	
	static Vector tablaSimbolos = null;
    static String[] tipo = null;
    static String[] variable = null;
    
    //Sección de bytecode
    static int cntBC = 0; // Contador de lineas para el código bytecode
    static String bc; // String temporal de bytecode
    static int jmp1, jmp2, jmp3, jmp4, jmp5, jmp6, jmp7, jmp8, jmp9;
    static int aux1, aux2, aux3;
    static String pilaBC[] = new String[100];
    static String memoriaBC[] = new String[10];
    static String pilaIns[] = new String [50];
    static int retornos[]= new int[10];
    static int cntIns = 0;
	
	public parser(LinkedList<Token> token)
	{
		indice=0;
		resultado=true;
		informe="";
		tablaSimbolos = new Vector();
		
		this.tokens=token;		
		tok=tokens.get(indice).getToken();
		indice++;
		
		arbolSintactico=Program();
	}
	
	public void advance()
	{
		if(indice<tokens.size())
		{
			tok=tokens.get(indice).getToken();
			indice++;
		}
		else
		{
			
		}
	}
	
	public void eat(int t)
	{
		if(tok==t)
		{
			advance();
		}
		else
			error();
	}
	
	public void finaleat(int t)
	{
		if(tok==t)
		{
			//Aqui termina"
		}
		else
			error();
	}
	
	public AST Program()
	{	
		Id id=null;
		Vector<Declaracion> dec = new Vector<Declaracion>();;
		Statement st=null;
		
		eat(Tclass);		
		if(resultado){id=Identifier();}		
		while(tok!=Tllave && resultado) {Declaracion declaracion=VarDeclaration(); dec.addElement(declaracion); tablaSimbolos.addElement(declaracion);} 
		createTable();
		if(resultado){eat(Tllave);}		
		if(resultado){st=Statement();}		
		if(resultado){finaleat(TllaveInv);}
	
		return new AST(id, dec, st);
	}
	
	public Declaracion VarDeclaration()
	{
		Tipo t=Type();		
		Id id=Identifier(); 	
		eat(Tpyc);
		
		 return new Declaracion(t, id);
	}
	
	public Tipo Type()
	{
		String t="";
		switch (tok)
		{
			case 4: eat(Tint);		t=	tokens.get(indice-2).valor;		break;
			case 5: eat(Tboolean); 	t=	tokens.get(indice-2).valor;	 break;
			default: error(); break;
		}
		return new Tipo(t);
	}
	
	public Statement Statement()
	{
		switch (tok)
		{
			case 8: eat(Tllave);		Vector<Statement> s = new Vector<Statement>(); 		while(tok!=TllaveInv) {s.addElement(Statement());} 	eat(TllaveInv);		return new stat(s);
			case 2: eat(Twhile);	eat(Tparentesis);		Ex e=Expression("while");		eat(TparentesisInv);		Statement s2=Statement();		return new whileS(e, s2);
			case 3: eat(Timprimir); 		eat(Tparentesis);		Ex e2=Expression("System.out.println");		eat(TparentesisInv);		eat(Tpyc);		return new printS(e2);
			case 19: Id id=Identifier();	declarationCheck(id.id1);	eat(Tigual);		Ex e3=Expression(id.id1);		eat(Tpyc);	return new inicializarS(id, e3); 
			default: error(); return null;
		}
	}
	
	public Ex Expression(String id)
	{
		String sim="";
		switch (tok)
		{
			case 19: 
				Id id1=null;
				Id id2=null;
				
				id1=Identifier();		
				switch (tok)
				{
					case 14: eat(Tsuma);	sim="+"; break;
					case 15: eat(Tresta);	sim="-"; break;
					case 16: eat(Tmultiplicacion);	sim="*";	break;
					case 17: eat(Tmenorque);	sim="<"; break;
					default: declarationCheck(id1.id1); sim="id"; compatibilityCheck(id, id1.id1); byteCode(id, sim, id1.id1, ""); return (new IdEx(id1));
				}
				id2=Identifier();
				declarationCheck(id1.id1);
				declarationCheck(id2.id1);
				compatibilityCheck(id1.id1, id2.id1);
				compatibilityCheck(id, id1.id1);
				byteCode(id, sim, id1.id1, id2.id1);
				return (new compareEx(id1, sim, id2));	
			
			case 6: eat(Ttrue); compatibilityCheck(id, "boolean"); sim="true"; byteCode(id, sim, "", ""); return (new booleanEx("true"));
			case 7: eat(Tfalse); compatibilityCheck(id, "boolean"); sim="false"; byteCode(id, sim, "", ""); return (new booleanEx("false"));
			case 18: Num n=Integ(); compatibilityCheck(id, "int"); sim="int"; byteCode(id, sim, (""+n.num1), ""); return (new NumEx(n));
			default: error(); return null;
		}
	}
	
	public Id Identifier()
	{
		eat(Tidentificador);
		Id id = new Id(tokens.get(indice-2).valor); 
		return (id);
	}
	
	public Num Integ()
	{
		eat(Tnumero);
		return new Num(	Integer.parseInt(	tokens.get(indice-2).valor		)	);
	}
	
	public void error()
	{
		informe=informe+"Error Sintactico en linea " + tokens.get(indice-1).getLine() + " Valor: " + tokens.get(indice-1).getValue() +"\n";
		resultado=false;
	}
	
	 public void createTable() {
	        variable = new String[tablaSimbolos.size()];
	        tipo = new String[tablaSimbolos.size()];
	        
	        //Imprime tabla de símbolos
	        System.out.println("-----------------");
	        System.out.println("TABLA DE SÍMBOLOS");
	        System.out.println("-----------------");
	        for(int i=0; i<tablaSimbolos.size(); i++) {
	        	Declaracion dx;
	        	Tipo tx;
	            dx = (Declaracion)tablaSimbolos.get(i);
	            variable[i] = dx.identificador.id1;
	            tipo[i] = dx.tipo.s1;                  
	            System.out.println(variable[i] + ": "+ tipo[i]); //Imprime tabla de símbolos por consola.
	        }
	        
	        ArrayUtils.reverse(variable);
	        ArrayUtils.reverse(tipo);
	        
	        System.out.println("-----------------\n");
	    }
	 
	//Verifica las declaraciones de las variables consultando la tabla de símbolos
	    public void declarationCheck(String s) {
	        boolean valido = false;
	        for (int i=0; i<tablaSimbolos.size(); i++) {
	            if(s.equals(variable[i])) {
	                valido = true;
	                break;
	            }
	        }
	        if(!valido) {
	            System.out.println("La varible "+ s +  " no está declarada.\nSe detuvo la ejecución.");
	             javax.swing.JOptionPane.showMessageDialog(null, "La varible [" + s + "] no está declarada", "Error",
	                   javax.swing.JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    //Chequeo de tipos consultando la tabla de símbolos
	    public void compatibilityCheck(String s1, String s2)
	    {
	    	if(s1.equals("System.out.println"))
	    		return;
	    	
	    	if(s1.equals("while"))
	    	{
	    		if(s2.equals("true"))
	    			return;
	    		else if(s2.equals("false"))
	    			return;
	    		else if(s2.equals("int"))
	    		{
	    			javax.swing.JOptionPane.showMessageDialog(null, "No hay una exprecion booleana en while", "Error",
		                      javax.swing.JOptionPane.ERROR_MESSAGE);
	    			return;
	    		}
	    		else
	    		{
		    		Declaracion elementoCompara1;
			        System.out.println("CHECANDO COMPATIBILIDAD ENTRE TIPOS ("+s2+", boolean). ");
			        boolean termino = false;
			        for(int i=0; i<tablaSimbolos.size() ; i++) 
			        {
			          elementoCompara1 = (Declaracion) tablaSimbolos.elementAt(i);
			          if(s2.equals(elementoCompara1.identificador.id1)) 
			          {
			            System.out.println("Se encontró el primer elemento en la tabla de símbolos...");
			                if(tipo[i].equals("boolean")) 
			                {
			                  termino = true;
			                  break;
			                }else
			                {
			                  termino = true;
			                    javax.swing.JOptionPane.showMessageDialog(null, "Incompatibilidad de tipos: "+ elementoCompara1.identificador.id1 +" ("
			                      + elementoCompara1.tipo.s1 + "),  (  boolean ).", "Error",
			                      javax.swing.JOptionPane.ERROR_MESSAGE);
			                }
			                break;
			          }
			          if(termino) {
			            return;
			          }
			        }
	    		}
	    		return;
	    	}

	    	if(s2.equals("boolean"))
	    	{
	            Declaracion elementoCompara1;
		        System.out.println("CHECANDO COMPATIBILIDAD ENTRE TIPOS ("+s1+", "+s2+"). ");
		        boolean termino = false;
		        for(int i=0; i<tablaSimbolos.size() ; i++) 
		        {
		          elementoCompara1 = (Declaracion) tablaSimbolos.elementAt(i);
		          if(s1.equals(elementoCompara1.identificador.id1)) 
		          {
		            System.out.println("Se encontró el primer elemento en la tabla de símbolos...");
		                if(tipo[i].equals(s2)) 
		                {
		                  termino = true;
		                  break;
		                }else
		                {
		                  termino = true;
		                    javax.swing.JOptionPane.showMessageDialog(null, "Incompatibilidad de tipos: "+ elementoCompara1.identificador.id1 +" ("
		                      + elementoCompara1.tipo.s1 + "),  (" + s2 +").", "Error",
		                      javax.swing.JOptionPane.ERROR_MESSAGE);
		                }
		                break;
		          }
		          if(termino) {
		            return;
		          }
		        }
		        return;
	    	}

	    	
	    	if(s2.equals("int"))
	    	{
	    		Declaracion elementoCompara1;
		        System.out.println("CHECANDO COMPATIBILIDAD ENTRE TIPOS ("+s1+", "+s2+"). ");
		        boolean termino = false;
		        for(int i=0; i<tablaSimbolos.size() ; i++) 
		        {
		          elementoCompara1 = (Declaracion) tablaSimbolos.elementAt(i);
		          if(s1.equals(elementoCompara1.identificador.id1)) 
		          {
		            System.out.println("Se encontró el primer elemento en la tabla de símbolos...");
		                if(tipo[i].equals("int")) 
		                {
		                  termino = true;
		                  return;
		                }else
		                {
		                  termino = true;
		                    javax.swing.JOptionPane.showMessageDialog(null, "Incompatibilidad de tipos: "+ elementoCompara1.identificador.id1 +" ("
		                      + elementoCompara1.tipo.s1 + "),  (" + s2 +").", "Error",
		                      javax.swing.JOptionPane.ERROR_MESSAGE);
		                }
		          }
		        }
	    	}

	    	
	        Declaracion elementoCompara1;
	        Declaracion elementoCompara2;
	        System.out.println("CHECANDO COMPATIBILIDAD ENTRE TIPOS ("+s1+", "+s2+"). ");
	        boolean termino = false;
	        for(int i=0; i<tablaSimbolos.size() ; i++) 
	        {
	          elementoCompara1 = (Declaracion) tablaSimbolos.elementAt(i);
	          if(s1.equals(elementoCompara1.identificador.id1)) 
	          {
	            System.out.println("Se encontró el primer elemento en la tabla de símbolos...");
	            for(int j=0; j<tablaSimbolos.size() ; j++) 
	            {
	              elementoCompara2 = (Declaracion) tablaSimbolos.elementAt(j);
	              if(s2.equals(elementoCompara2.identificador.id1)) 
	              {
	                System.out.println("Se encontró el segundo elemento en la tabla de símbolos...");
	                if(tipo[i].equals(tipo[j])) 
	                {
	                  termino = true;
	                  break;
	                }else
	                {
	                  termino = true;
	                    javax.swing.JOptionPane.showMessageDialog(null, "Incompatibilidad de tipos: "+ elementoCompara1.identificador.id1 +" ("
	                      + elementoCompara1.tipo.s1 + "), "+elementoCompara2.identificador.id1 +" (" + elementoCompara2.tipo.s1
	                      +").", "Error",
	                      javax.swing.JOptionPane.ERROR_MESSAGE);
	                }
	                break;
	              }
	            }
	          }
	          if(termino) {
	            break;
	          }
	        }
	    }
	    
	    public void byteCode(String id, String tipo, String s1, String s2){
	        int pos1=-1, pos2=-1, pos3=-1;
	        
	        for(int i=0; i<variable.length; i++) 
	        {
	            if(id.equals(variable[i])) 
	            {
	                pos1 = i;
	            }
	            if(s1!=null && s1.equals(variable[i])) 
	            {
	                pos2 = i;
	            }
	            if(s2!=null && s2.equals(variable[i])) 
	            {
	                pos3 = i;
	            }
	        }
	        
	        if(id.equals("System.out.println"))
	        {
	        	ipbc(cntIns + ": ldc "+pos2);
	        	jmp9 = cntBC;
	        	return;
	        }
	        
	        switch(tipo) {
	          case "+":
	            ipbc(cntIns + ": iload_"+pos2);
	            ipbc(cntIns + ": iload_"+pos3);
	            ipbc(cntIns + ": iadd");
	            ipbc(cntIns + ": istore_"+pos1);
	            //ipbc(cntIns + ": ifne " + (cntIns+4));
	            jmp1 = cntBC;
	          break;

	          case "-":
		            ipbc(cntIns + ": iload_"+pos2);
		            ipbc(cntIns + ": iload_"+pos3);
		            ipbc(cntIns + ": isub");
		            ipbc(cntIns + ": istore_"+pos1);
	            jmp2 = cntBC;
	          break;
	          
	          case "*":
		            ipbc(cntIns + ": iload_"+pos2);
		            ipbc(cntIns + ": iload_"+pos3);
		            ipbc(cntIns + ": imul");
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp3 = cntBC;
		          break;
		          
	          case "<":
		            ipbc(cntIns + ": iload_"+pos1);
		            ipbc(cntIns + ": iload_"+pos2);
		            ipbc(cntIns + ": iadd");
		            jmp4 = cntBC;
		          break;
		          
	          case "id":
		            ipbc(cntIns + ": iload_"+pos2);
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp5 = cntBC;
		          break;
		      
	          case "int":
		            ipbc(cntIns + ": iconst_"+s1);
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp6 = cntBC;
		          break;
		       
	          case "true":
		            ipbc(cntIns + ": iconst_1");
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp7 = cntBC;
		          break;
		          
	          case "false":
		            ipbc(cntIns + ": iconst_0");
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp8 = cntBC;
		          break;
		          
	          case "print":
		            ipbc(cntIns + ": iconst_0");
		            ipbc(cntIns + ": istore_"+pos1);
		            jmp8 = cntBC;
		          break;
	        }
	    }
	    
	    
	    public void ipbc(String ins) {
	        while(pilaBC[cntBC] != null) {
	            cntBC++;
	        }
	        cntIns++;
	        pilaBC[cntBC] = ins;
	        cntBC++;
	    }
	    
	    public String getBytecode() {
	        String JBC = "";
	        for(int i=0; i<pilaBC.length; i++) {
	            if(pilaBC[i] != null){
	                JBC = JBC + pilaBC[i] + "\n";
	            }
	        }
	        return JBC;
	    }   
}
