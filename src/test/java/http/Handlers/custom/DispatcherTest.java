package http.Handlers.custom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import server.base.config.Dispatcher;

import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DispatcherTest {

    @Test
    void  getServiceListTest ()  {

       Dispatcher dispatcher = Dispatcher.getInstance();
       Map<MediaType, HTTPAbstractHandler> handlers = dispatcher.getMapServices();
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


    @Test
    void  getCustomHandlersLocationTest ()  {
       String path;
            Class<?> baseClassPath = HTTPAbstractHandler.class;
            URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
            String relativeLocation = baseClassPath.getPackageName();
            path = rootLocation.getPath() + relativeLocation;
        Assertions.assertNotNull(path);
    }
}
