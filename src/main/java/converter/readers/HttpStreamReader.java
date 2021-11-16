package converter.readers;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;

 public interface HttpStreamReader {
     @SneakyThrows
     default byte[] readStream(InputStream inputStream ) {
       return  inputStream.readAllBytes();
    }

    void validate(byte[] content) throws IOException;
}
