package danila.sukhov.auth_service.api.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalException extends RuntimeException {
    HttpStatus httpStatus;

    public GlobalException(){
    }

    public GlobalException(String msg, HttpStatus httpStatus){
        super(msg);
        this.httpStatus = httpStatus;
    }

    public GlobalException(String msg, Throwable cause){
        super(msg, cause);
    }

    public GlobalException(Throwable cause){
        super(cause);
    }
}
