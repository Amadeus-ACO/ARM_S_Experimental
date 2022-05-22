package exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(HttpStatus httpStatus) {
        super("Не удалось авторизоваться, " + httpStatus);
    }
}
