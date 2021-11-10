package converters;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StreamDataParser extends DataParser {
    @Override
    public void saveToFile(byte[] bytes) {
        bufferSize=bytes.length;
        extension=".bin";
        Path root = Paths.get("");// .toAbsolutePath().toFile().getName();
        rootLocation = root.toAbsolutePath().toFile().getName();
        File directory = new File(rootLocation +"/streamFiles/");
        super.saveToFile(bytes);
    }
}
