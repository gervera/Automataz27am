package SintacticTree;

public class Declarax {
    public String id;
    public Typex type;    
    
    public Declarax(String st1, Typex st2) {
        id = st1;
        type = st2;
    }

	public String getId() {
		return id;
	}

	public Typex getType() {
		return type;
	}

}
