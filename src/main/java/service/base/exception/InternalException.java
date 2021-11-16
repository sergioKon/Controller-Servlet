package service.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class InternalException extends RuntimeException {

    public InternalException(String message) {
        super(message);
    }
}