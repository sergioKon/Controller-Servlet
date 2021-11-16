package httpHandlers;

import converter.readers.HttpStreamReader;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
public class XMLHandler extends HTTPAbstractHandler implements HttpStreamReader {

    @Getter
    private Object xmlData= null;
    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        log.debug("proceed started ");
        byte[] result = readStream(request.getInputStream());
        validate(result);
        log.debug(" xml is {}", xmlData);
    }

    public  XMLHandler(){

    }

    @Override
    protected void initMediaType() {
        super.mediaType= MediaType.APPLICATION_XML;
    }

    @Override
    public void validate(byte[] content) throws IOException {
        throw  new IOException(" not implemented ");
    }
}
