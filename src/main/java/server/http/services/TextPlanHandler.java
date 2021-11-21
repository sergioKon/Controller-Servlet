package server.http.services;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

public class TextPlanHandler extends HTTPAbstractHandler {
    @Override
    public void proceed(HttpServletRequest request) {

    }

    @Override
    protected void initMediaType() {
        super.mediaType= MediaType.TEXT_PLAIN;
    }
}
