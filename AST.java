package lenguaje;

import java.util.Vector;

public class AST
{
	Id identificador;
	Vector<Declaracion> declaraciones = new Vector<Declaracion>();
	Statement sta;
	public AST(Id i, Vector<Declaracion> d,	Statement s)
	{
		identificador=i;
		declaraciones=d;
		sta=s;
	}
	
}

class Declaracion
{
	Tipo tipo;
	Id identificador;
	public Declaracion(Tipo t, Id d)
	{
		tipo=t;
		identificador=d;
	}
}

abstract class Statement{}

class stat extends Statement
{
	Vector<Statement> statements = new Vector<Statement>();
	public stat(Vector<Statement> s)
	{
		statements=s;
	}
}

class whileS extends Statement
{
	Ex exprecion;
	Statement stat;
	public whileS(Ex E, Statement s)
	{
		exprecion=E;
		stat=s;
	}
}

class printS extends Statement
{
	Ex exprecion;
	public printS(Ex e)
	{
		exprecion=e;
	}
}

class inicializarS extends Statement
{
	Id identificador;
	Ex exprecion;
	public inicializarS(Id i, Ex e)
	{
		identificador=i;
		exprecion=e;
	}
}

class Tipo  
{
    String s1; 
    public Tipo(String st1)
    {
        s1=st1;  
    }
}

abstract class Ex {}

class compareEx extends Ex
{
	Id id1;
	String simbolo;
	Id id2;
	public compareEx(Id id3, String simbolo2, Id id4)
	{
		id1=id3;
		simbolo=simbolo2;
		id2=id4;
	}
}

class booleanEx extends Ex
{
	String bol;
	public booleanEx(String bol2)
	{
		bol=bol2;
	}
}

class IdEx extends Ex
{
	Id id;
	public IdEx(Id id2)
	{
		id=id2;
	}
}

class NumEx extends Ex
{
	Num n;
	public NumEx(Num n2)
	{
		n=n2;
	}
}

class Id
{
    String id1;
    Id(String id2) 
    {
        id1 = id2;
    }
}

class Num
{
    int num1;
    public Num(int num2) 
    {
        num1 = num2;
    }
}


