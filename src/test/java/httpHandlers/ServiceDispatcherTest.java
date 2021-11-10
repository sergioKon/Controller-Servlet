package httpHandlers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import server.base.config.ServiceDispatcher;

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
}
