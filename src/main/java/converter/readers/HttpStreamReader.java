package converter.readers;


import java.io.IOException;
import java.io.InputStream;

 public interface HttpStreamReader {

     default byte[] readStream(InputStream inputStream ) {
       try {
		return  inputStream.readAllBytes();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
    }

    void validate(byte[] content) throws IOException;
}
