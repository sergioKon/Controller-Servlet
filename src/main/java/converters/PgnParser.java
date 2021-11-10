package converters;

public class PgnParser extends DataParser {

    public PgnParser(){
        this.extension= ".pgn";
        this.bufferSize= 1024;
    }
}
