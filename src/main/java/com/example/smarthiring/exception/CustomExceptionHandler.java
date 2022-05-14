package com.smartdev.iresource.project.exception;

import com.smartdev.iresource.project.common.ExceptionResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    public ExceptionResponse getErrorResponse(
            Exception exception) {

        return new ExceptionResponse(
                exception.getMessage()
        );
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<ExceptionResponse> feignFail(FeignException feignException) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = getErrorResponse(feignException);

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> somethingWentWrong(NotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = getErrorResponse(
                exception
        );

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(SomethingWrongException.class)
    public final ResponseEntity<ExceptionResponse> somethingWentWrong(SomethingWrongException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = getErrorResponse(
                exception
        );

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(RequestFailedException.class)
    public final ResponseEntity<ExceptionResponse> requestFailed(RequestFailedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = getErrorResponse(
                exception
        );

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(ValidateCreateProjectException.class)
    public final ResponseEntity<ExceptionResponse> validateCreateProjectExceptionHandler(ValidateCreateProjectException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = getErrorResponse(
                exception
        );

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ExceptionResponse> fileStorageExceptionHandler(FileStorageException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = getErrorResponse(
                exception
        );

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public final ResponseEntity<ExceptionResponse> maxUploadSizeExceptionHandler() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse("size of file is invalid");

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
