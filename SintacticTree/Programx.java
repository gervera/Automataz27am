package SintacticTree;

import java.util.Vector;

public class Programx {
    private Vector d;
    private Statx s;
    
    public Programx(Vector d1, Statx s1) {
        d = d1;
        s = s1;
    }
    
    public Vector getDeclaration() { 
    	return d;
    }
    
    public Statx getStatement() {
    	return s;
    }

}
