package com.smartdev.iresource.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviseControllerException {

    @ExceptionHandler(value = RegistrationFailedException.class)
    public ResponseEntity<Object> handleRegistration(RegistrationFailedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                exception.getDevCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleRegistration(HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                1901,
                "Input type is invalid."
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleRegistration(HttpRequestMethodNotSupportedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                1902,
                "Request method " + exception.getMethod() + " not supported."
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<Object> handleRegistration(LoginException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                exception.getDevCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleRegistration(MissingServletRequestParameterException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                1903,
                exception.getMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = SomethingWrongException.class)
    public ResponseEntity<Object> handleSomethingWrongException(SomethingWrongException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException = new ResponseBodyException(
                false,
                httpStatus,
                1904,
                exception.getMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    /*@ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleRegistration(AccessDeniedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                1999,
                exception.getLocalizedMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleLogin(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBodyException responseBodyException =  new ResponseBodyException(
                false,
                HttpStatus.BAD_REQUEST,
                1999,
                exception.getLocalizedMessage()
        );
        return new ResponseEntity<>(responseBodyException, httpStatus);
    }*/
}
