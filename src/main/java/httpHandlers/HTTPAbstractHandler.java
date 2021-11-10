package httpHandlers;


import converters.DataParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class HTTPAbstractHandler {


    protected MediaType mediaType;
    protected  DataParser dataParser = null;
    public  MediaType getMediaType(){
        return  mediaType;
    }

    public HTTPAbstractHandler() {
        log.debug(" you are handling {} client mime type ",this.getClass().getSimpleName());
        InitMediaType();
    }

    protected abstract void InitMediaType();

    abstract public  void proceed(HttpServletRequest request);
}
