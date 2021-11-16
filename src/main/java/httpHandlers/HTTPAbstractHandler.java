package httpHandlers;


import converter.parsers.DataParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public abstract class HTTPAbstractHandler {


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
