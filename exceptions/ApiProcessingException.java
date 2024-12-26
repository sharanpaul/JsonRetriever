package api.exceptions;

public class ApiProcessingException extends RuntimeException{
    public ApiProcessingException(String message) {
        super(message);
    }
}
