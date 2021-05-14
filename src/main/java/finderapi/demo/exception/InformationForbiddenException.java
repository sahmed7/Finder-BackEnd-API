package finderapi.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InformationForbiddenException extends RuntimeException{
    public InformationForbiddenException(String message){
        super(message);
    }
}
