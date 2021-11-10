package httpHandlers;


import converters.StreamDataParser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class OctetStreamHandler extends HTTPAbstractHandler {


    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.APPLICATION_OCTET_STREAM;
    }

    @SneakyThrows
    @Override
    public void proceed(HttpServletRequest request)  {

        byte[] clientData= request.getInputStream().readAllBytes();
        dataParser = new StreamDataParser();
        dataParser.saveToFile(clientData);
    }
}


