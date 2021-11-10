package server.base.rest;

import httpHandlers.HTTPAbstractHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.base.config.ServiceDispatcher;

import javax.servlet.http.HttpServletRequest;


@SpringBootApplication
@RestController
public class CommonController {

    protected final Logger log = LogManager.getLogger(getClass());
    
    @RequestMapping("/")
    public String home() {
        return "Welcome to home!";
    }

    @RequestMapping(value = "/anyTypeClient")
    public String readData( HttpServletRequest request) {
      //  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String contentType=  request.getContentType();
        log.debug(" content type = {}", contentType);
        MediaType mediaType =   MediaType.valueOf(contentType);
        HTTPAbstractHandler handler= ServiceDispatcher.getInstance().getMapServices().get(mediaType);
        handler.proceed(request);
        return "success";
    }
}
