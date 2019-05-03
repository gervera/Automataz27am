package analizadorSintactico;

public class CustomToken {
    /** The line number of the first character of this Token. */
    public int beginLine;
    /** The column number of the first character of this Token. */
    public int beginColumn;
    /** The line number of the last character of this Token. */
    public int endLine;
    /** The column number of the last character of this Token. */
    public int endColumn;

    /**
     * The string image of the token.
     */
    public String image;

    public CustomToken(String image,int beginLine,int beginColumn,int endLine,int endColumn){
        this.image = image;
        this.beginColumn = beginColumn;
        this.beginLine = beginLine;
        this.endColumn = endColumn;
        this.endLine = endLine;
    }

}
