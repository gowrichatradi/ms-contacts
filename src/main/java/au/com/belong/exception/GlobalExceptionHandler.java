package au.com.belong.exception;

import au.com.belong.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode(), exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse("RJCT", exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
