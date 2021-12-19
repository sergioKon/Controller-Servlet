package http.Handlers.custom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import server.base.config.ServiceDispatcher;

import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceDispatcherTest {

    @Test
    void  getServiceListTest ()  {

       ServiceDispatcher serviceDispatcher= ServiceDispatcher.getInstance();
       Map<MediaType, HTTPAbstractHandler> handlers = serviceDispatcher.getMapServices();
       for (Map.Entry<MediaType, HTTPAbstractHandler> entry : handlers.entrySet()) {
           Class<?> clazz =   entry.getValue().getClass();
           switch (entry.getKey().toString()) {
               case MediaType.APPLICATION_OCTET_STREAM_VALUE -> assertEquals(clazz, OctetStreamHandler.class);
               case MediaType.MULTIPART_FORM_DATA_VALUE -> assertEquals(clazz, MultipartHandler.class);
               case MediaType.APPLICATION_XML_VALUE -> assertEquals(clazz, XMLHandler.class);
               case MediaType.APPLICATION_JSON_VALUE -> assertEquals(clazz, JSONHandler.class);
           }
        }
    }

    @Value("${http.baseHandler.root}")
    private String rootPackage;

    @Test
    void  getCustomHandlersLocationTest ()  {
       String path = null;
       if (rootPackage==null) {  //check it
            Class<?> baseClassPath = HTTPAbstractHandler.class;
            URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
            String relativeLocation = baseClassPath.getPackageName();
            path = rootLocation.getPath() + relativeLocation;
        }
        else {
            path = rootPackage;
        }
    }
}
