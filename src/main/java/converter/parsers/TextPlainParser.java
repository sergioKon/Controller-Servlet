package converter.parsers;

public class TextPlainParser extends DataParser {
    public  TextPlainParser(){
        this.extension= ".XML";
        this.bufferSize= 1024;
    }

}