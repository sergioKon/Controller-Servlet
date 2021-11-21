package server.http.services;


import common.converter.parsers.StreamDataParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;


@Log4j2
public class OctetStreamHandler extends HTTPAbstractHandler {


    @Override
    protected void initMediaType() {
        super.mediaType= MediaType.APPLICATION_OCTET_STREAM;
    }


    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        InputStream is= request.getInputStream();
        byte[] clientData = is.readAllBytes();
        dataParser = new StreamDataParser();
        dataParser.saveToFile(clientData);
    }
}


