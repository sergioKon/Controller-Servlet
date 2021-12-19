package server.base.rest;

import http.Handlers.custom.HTTPAbstractHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.base.config.ServiceDispatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@RestController
@Log4j2
public class CommonController {

    @RequestMapping("/")
    public String home() {
        return "Welcome to home!";
    }

    @RequestMapping(value = "/anyTypeClient")
    public HttpStatus readData( HttpServletRequest request) {
        String contentType=  request.getContentType();
        log.debug(" content type = {}", contentType);
        if(contentType== null) {
          List<String> clientParams =  Collections.list( request.getParameterNames());
          if(clientParams.isEmpty()){
             return HttpStatus.NO_CONTENT;
          }
          for(String param : clientParams){
              log.debug(" param = {}  value = {}", param, request.getParameter(param));
          }
          return HttpStatus.OK;
        }
        MediaType mediaType =   MediaType.valueOf(contentType);
        HTTPAbstractHandler handler= ServiceDispatcher.getInstance().getMapServices().get(mediaType);
        handler.proceed(request);
        return HttpStatus.OK;
    }
}
