package server.base.config;

import httpHandlers.HTTPAbstractHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher {

    private static final Singleton instance = new Singleton();

    static class Singleton {
        private static  final ServiceDispatcher  instance = new ServiceDispatcher();
    }

     @Getter
     private Map<MediaType, HTTPAbstractHandler>  mapServices = new HashMap<>();
     protected String baseHandlerPackage="httpHandlers";



    public static ServiceDispatcher getInstance() {
        return Singleton.instance;
    }

    @SneakyThrows
    private ServiceDispatcher()  {
         setAllTypeHandlers();
     }



    protected void  setAllTypeHandlers() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

           Class<?> baseClassPath= HTTPAbstractHandler.class;
           URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
           String relativeLocation=  baseClassPath.getPackageName();
           String path= rootLocation.getPath()+relativeLocation;

           File[] fileHandlers= new File(path).listFiles();
           if(fileHandlers ==null)  {
               throw  new IllegalAccessException(" folder for file is empty ");
           }
           for (File f: fileHandlers) {

               Class<?> clazz = Class.forName(baseHandlerPackage + '.' + f.getName().substring(0, f.getName().length() - 6));
               if(clazz.getName().contains("Abstract")) {
                   continue;
               }
               HTTPAbstractHandler httpHandler = (HTTPAbstractHandler) clazz.getDeclaredConstructor().newInstance();
               mapServices.put(httpHandler.getMediaType(), httpHandler);
            }
        }

      public HTTPAbstractHandler getService(MediaType key){
         return mapServices.get(key);
      }
}
