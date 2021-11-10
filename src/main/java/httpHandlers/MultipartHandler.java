package httpHandlers;

import converters.*;
import javassist.tools.web.BadHttpRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2

public class MultipartHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.MULTIPART_FORM_DATA;
    }

    @SneakyThrows
    @Override
    public void proceed(HttpServletRequest request) {
           MultiValueMap<String, MultipartFile> clientFiles=  ((MultipartHttpServletRequest) request).getMultiFileMap();
           for(String  name : clientFiles.keySet()){
               List<MultipartFile> files=  clientFiles.get(name);
               for(MultipartFile file : files) {
                   if(file.getContentType()== null) {
                       throw new BadHttpRequest();
                   }
                   switch (file.getContentType())   {
                      case MediaType.APPLICATION_XML_VALUE:
                          dataParser= new XMLParser();
                          break;
                      case  MediaType.APPLICATION_JSON_VALUE:
                          dataParser= new JSonParser();
                          break;
                      case MediaType.APPLICATION_CBOR_VALUE:

                          break;
                      case MediaType.IMAGE_GIF_VALUE:
                          dataParser= new GifParser();
                          break;
                      case  MediaType.IMAGE_JPEG_VALUE:
                          dataParser= new JpegParser();
                          break;
                      case MediaType.IMAGE_PNG_VALUE:
                          dataParser= new PgnParser();
                      default:
                          throw new IllegalStateException("Unexpected value: " + file.getContentType());
                  }
                  dataParser.saveToFile(file.getBytes());
               }
           }
    }
}
