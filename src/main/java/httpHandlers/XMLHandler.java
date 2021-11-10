package httpHandlers;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

public class XMLHandler extends HTTPAbstractHandler {
    @Override
    public void proceed(HttpServletRequest request) {

    }

    public  XMLHandler(){

    }

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.APPLICATION_XML;
    }
}
