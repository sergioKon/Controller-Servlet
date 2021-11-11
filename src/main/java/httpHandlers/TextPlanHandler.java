package httpHandlers;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

public class TextPlanHandler extends HTTPAbstractHandler {
    @Override
    public void proceed(HttpServletRequest request) {

    }

    public TextPlanHandler(){

    }

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.TEXT_PLAIN;
    }
}
