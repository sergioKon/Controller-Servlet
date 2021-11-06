package com.htakemoto.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@SpringBootApplication
@RestController
public class HomeController {
    
    protected final Logger log = LogManager.getLogger(getClass());
    
    @RequestMapping("/")
    public String home() {
        return "Welcome to home!";
    }

    @RequestMapping(value = "/anyTypeClient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean readData() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String contentType=  request.getContentType();
        log.debug(" content type = {}", contentType);
        MediaType mediaType =   MediaType.valueOf(contentType);

        switch ( mediaType.toString() ){
            case MediaType.APPLICATION_JSON_VALUE:
              break;

            case  MediaType.MULTIPART_FORM_DATA_VALUE:
                 break;
            case MediaType.APPLICATION_OCTET_STREAM_VALUE:
                break;
        }

        return true;
    }
}
