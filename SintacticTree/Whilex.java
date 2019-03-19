package SintacticTree;

public class Whilex extends Statx {
	
	Expx e;
	Statx s;
	
	public Whilex(Expx a,Statx b) {
		e = a;
		s = b;
	}
	
    public Object[] getVariables() {
        Object obj[] = new Object[2];
        obj[0] = e;
        obj[1] = s;
        return obj;
    }
    
}
 