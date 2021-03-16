package softuniBlog.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFound.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest webRequest){

        String bodyOfResponse = "No such entity in database";

        return handleExceptionInternal( ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(value = AccessDeniedEx.class)
    protected ResponseEntity<Object> accessDenied(Exception ex, WebRequest webRequest){

        String bodyOfResponse = "Access is Denied ! ";

        return handleExceptionInternal( ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, webRequest);
    }

    @ExceptionHandler(value = WrongPassword.class)
    protected ResponseEntity<Object> wrongPassword(RuntimeException ex, WebRequest webRequest){

        String bodyOfResponse = "Passwords dont match! ";

        return handleExceptionInternal( ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

    @ExceptionHandler(value = CheckForProfile.class)
    protected ResponseEntity<Object> checkProfiles(RuntimeException ex, WebRequest webRequest){

        String bodyOfResponse = "Only Admin can edit and delete articles !";

        return handleExceptionInternal( ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

    @ExceptionHandler(value = MatchedNames.class)
    protected ResponseEntity<Object> matchedNames(RuntimeException ex, WebRequest webRequest){

        String bodyOfResponse = "Try again with different name of category ! ";

        return handleExceptionInternal( ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

}
