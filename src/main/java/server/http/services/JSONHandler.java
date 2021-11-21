package server.http.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import common.converter.readers.HttpStreamReader;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

@Log4j2
public class JSONHandler  extends  HTTPAbstractHandler implements HttpStreamReader {

    @Override
    protected void initMediaType() {
        super.mediaType = MediaType.APPLICATION_JSON;
    }
    @Getter
    private Object jsonData= null;
    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        log.debug("proceed started ");
        byte[] result = readStream(request.getInputStream());
        validate(result);
        log.debug(" json is {}", jsonData);
    }

    @Override
    public void validate(byte[] content) throws IOException {
        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        StringReader reader = new StringReader(new String(content));
        Object jsonData=  gson.fromJson(reader,Object.class);
        try {
            JsonNode json = objectMapper.readTree(content);
            Iterator<String> fieldNames= json.fieldNames();
            while(fieldNames.hasNext()){
                log.debug(" json contains field {}", fieldNames.next());
            }

        } catch (IOException e) {
            throw new  IOException(" the contents is not valid json " );
        }
    }
}

