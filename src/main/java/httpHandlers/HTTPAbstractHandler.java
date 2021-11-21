package httpHandlers;


import converter.parsers.DataParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class HTTPAbstractHandler {
     
	protected static final Logger log = LogManager.getLogger(HTTPAbstractHandler.class);

    protected MediaType mediaType;
    protected  DataParser dataParser = null;
    public  MediaType getMediaType(){
        return  mediaType;
    }

    public HTTPAbstractHandler() {
        log.debug(" you are handling {} client mime type ",this.getClass().getSimpleName());
        initMediaType();
    }

    protected abstract void initMediaType();

    abstract public  void proceed(HttpServletRequest request) throws IOException;
}
