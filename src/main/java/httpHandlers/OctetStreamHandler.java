package httpHandlers;


import converter.parsers.StreamDataParser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;


@Log4j2
public class OctetStreamHandler extends HTTPAbstractHandler {


    @Override
    protected void initMediaType() {
        super.mediaType= MediaType.APPLICATION_OCTET_STREAM;
    }


    @Override
    @SneakyThrows
    public void proceed(HttpServletRequest request) {
        InputStream is= request.getInputStream();
        byte[] clientData = is.readAllBytes();
        dataParser = new StreamDataParser();
        dataParser.saveToFile(clientData);
    }
}


