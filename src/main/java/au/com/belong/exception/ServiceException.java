package au.com.belong.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceException extends RuntimeException {
    private HttpStatus status;
    private String msg;
    private String errorCode;

    public ServiceException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.status = status;
        this.msg = message;
        this.errorCode = errorCode;
    }

}
