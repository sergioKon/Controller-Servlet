package httpHandlers;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

public class JSONHandler  extends  HTTPAbstractHandler{

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.APPLICATION_JSON;
    }

    @Override
    public void proceed(HttpServletRequest request) {

    }
}
