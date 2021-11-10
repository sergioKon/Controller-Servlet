package converters;

public class JSonParser extends DataParser {
    public  JSonParser(){
        this.extension= ".json";
        this.bufferSize= 1024;
    }
}
