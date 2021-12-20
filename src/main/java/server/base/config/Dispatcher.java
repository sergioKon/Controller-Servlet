package server.base.config;

import http.Handlers.custom.HTTPAbstractHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {


    @Autowired
    Environment environment;
    static class Singleton {
        private static  final Dispatcher  instance = new Dispatcher();
    }
  
     @Getter
     private final Map<MediaType, HTTPAbstractHandler>  mapServices = new HashMap<>();

    public static Dispatcher getInstance() {
        return Singleton.instance;
    }

    @SneakyThrows
    private Dispatcher()  {
         setAllTypeHandlers();
     }

    /***
     * read files from base location ( class   HTTPAbstractHandler) by reflection
     * and put them to the map for a future usage ( command pattern )
     */


    protected void  setAllTypeHandlers() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
   //baseHandlerPackage
         String path;
      //   String rootPackage= environment.getProperty("http.baseHandler.root");
         // String rootPackage= "http.baseHandler.root";

           Class<?> baseClassPath = HTTPAbstractHandler.class;
           URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
           String relativePackage = baseClassPath.getPackageName();
           String relativeLocation = relativePackage.replace('.','/');
           path = rootLocation.getPath() + relativeLocation;

           File[] fileHandlers= new File(path).listFiles();
           if(fileHandlers ==null)  {
               throw  new IllegalAccessException(" folder for file is empty ");
           }
           for (File file: fileHandlers) {
               int iEnd = file.getName().indexOf(".class");
               Class<?> clazz = Class.forName(relativePackage + '.' + file.getName().substring(0, iEnd));

             //  Class<?> clazz = Class.forName(path + '.' + file.getName().substring(0, file.getName().length() - 6));
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
