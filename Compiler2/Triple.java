package Compiler2;

import java.util.Vector;

import SintacticTree.Asignax;
import SintacticTree.Boolx;
import SintacticTree.Declarax;
import SintacticTree.Expx;
import SintacticTree.Idx;
import SintacticTree.Intx;
import SintacticTree.Oxpx;
import SintacticTree.Printx;
import SintacticTree.Programx;
import SintacticTree.Statx;
import SintacticTree.Typex;
import SintacticTree.Whilex;

public class Triple {
	private Programx Px; 
	private Vector ST; 
	private Vector Statements;
	private String code;
	private String[] var; 
	private String[] type;
	private Compiler compiler;
	
	public Triple(Programx px, Vector ts, String[] v, String[] t, Vector st, Compiler c, String codigo) {		
		Px = px;
		ST = ts;
		var = v;
		type = t;
		Statements = st;
		compiler = c;
		code = codigo;

        // Print the Symbol Table
        System.out.println("\n+++++ Symbol Table +++++\n------------------------");
        compiler.consoleArea.append("\n\n+++++ Symbol Table +++++\n--------------------------------------\n");
        for (int i=ST.size()-1; i>=0; i--) {
            Declarax dx = (Declarax) ST.get(i);
            var[i] = dx.id;
            type[i] = dx.type.getTypex();
            int line = 0, col = 0;
            
            // Determine position of var
            String vec[] = code.split("\n");
            for (int j = 0; j < vec.length; j++) {
            	String chain = type[i] + " " + var[i];
            	if (vec[j].indexOf(chain) != -1) {
            		line = j + 1;
            		col = vec[j].indexOf(chain);
            		break;
            	} 
            }
            System.out.println(var[i] + " : " +  type[i] + "\t(Line." + line + " | Col." + col + ")");
            compiler.consoleArea.append(var[i] + " : " + type[i] + "\t(Line." + line + " | Col." + col + ")\n");
        }
        System.out.println("------------------------\n");
        compiler.consoleArea.append("--------------------------------------\n");
        production();
	}
	
	// Triples
	private void production() {
		Vector<T> result = new Vector<T>();
		int PC = 1, line = 0;
		Boolean entry = false, cyclic = false;
		T t;
		for (int i=Statements.size(); i>0; i--) {
			S s = (S) Statements.elementAt(i-1);
			
			// In Case Of While
			if (s.Type == 1) {
				entry = true;
				if (isBool(s.Expression)) {
					Boolx oxx = (Boolx) s.Expression;
					if (!Boolean.parseBoolean(oxx.bool)) {
						System.out.println("\n> Error: You Can't Create a Cycle Based on a False!");
						compiler.consoleArea.append("\n> Error: You Can't Create a Cycle Based on a False!\n");
						break;
					}
					t =  new T(PC++, " Jpos\t" + oxx.bool + "\t");
					result.addElement(t);
					line = PC - 2;
					cyclic = true;
					
				}
				else {
					Oxpx oxx = (Oxpx) s.Expression;
					t = new T(PC++, " -\t" + oxx.id1 + "\t" + oxx.id2);
					result.addElement(t);
					t =  new T(PC++, " Jpos\t(" + (PC-2) + ")\t");
					result.addElement(t);
					line = PC - 2;
				}
			}
			
			// In Case Of Asignation
			else if (s.Type == 2) {
				Idx idx = s.Identifier;
				Expx exp = s.Expression;
				if (isNumber(exp)) {
					Intx inx = (Intx) exp;
					t = new T(PC++, " =\t" + (inx.entero) + "\t" + (idx.getIdx()));
					result.addElement(t);
				}
				else {
					Boolx box = (Boolx) exp;
					t = new T(PC++, " =\t" + (box.bool) + "\t" + (idx.getIdx()));
					result.addElement(t);
				}	
			}
			
			// In Case Of Print
			else if (s.Type == 3) {
				Expx exp = s.Expression;
				if (isNumber(exp)) {
					Intx inx = (Intx) exp;
					t =  new T(PC++, " Print\t" + inx.entero);
					result.addElement(t);
				}
				else {
					Oxpx oxx = (Oxpx) exp;
					t = new T(PC++, " " + oxx.op + "\t" + oxx.id1 + "\t" + oxx.id2);
					result.addElement(t);
					t = new T(PC++, " Print\t(" + (PC-2) + ")");
					result.addElement(t);
				}
				
				if (entry) {
					t = new T(PC++, " Jp\t(" + (cyclic==true?line+1:line) + ")");
					result.addElement(t);
					t = result.elementAt(line);
					t = new T(t.PC, t.I + "(" + PC + ")");
					result.setElementAt(t, line);
					entry = false;
					cyclic = false;
				}
			}
		}
		t = new T(PC++, " ...");
		result.addElement(t);
		/*
		// Show Triples Result
		System.out.println("\n+++++ Triples +++++\n-----------------------------");
		compiler.consoleArea.append("\n+++++ Triples +++++\n----------------------------------------------------\n");
		for (int i=0; i<result.size(); i++) {
			t = result.elementAt(i);
			System.out.println("(" + t.PC + ") " + " " + t.I);
			compiler.consoleArea.append("(" + t.PC + ") " + " " + t.I + "\n");
		}
		System.out.println("-----------------------------");
		compiler.consoleArea.append("----------------------------------------------------\n");*/
	}

	// Check If It's a Boolean
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
	
	// Check If It's WhileX
	private boolean checkWhileX(Statx sx) {
		try {
			Whilex wx = (Whilex) sx;
			System.out.println(">>>>>>>>>>>>>>>>>> It's WhileX!");
			return true;
		}
		catch (Exception e) {
			System.out.println(">>>>>>>>>>>>>>>>>> It's not WhileX!");
			return false;
		}
	}

	// Check If It's PrintX
	private boolean checkPrintX(Statx sx) {
		try {
			Printx prx = (Printx) sx;
			System.out.println(">>>>>>>>>>>>>>>>>> It's PrintX!");
			return true;
		}
		catch (Exception e) {
			System.out.println(">>>>>>>>>>>>>>>>>> It's not PrintX!");
			return false;
		}
	}

	// Check If It's AsignaX
	private boolean checkAsignaX(Statx sx) {
		try {
			Asignax ax = (Asignax) sx;
			System.out.println(">>>>>>>>>>>>>>>>>> It's AsignaX!");
			return true;
		}
		catch (Exception e) {
			System.out.println(">>>>>>>>>>>>>>>>>> It's not AsignaX!");
			return false;
		}
	}
		
}

class T {
	public int PC;
	public String I;
	
	public T(int p, String i) {
		PC = p;
		I = i;
	}
}
