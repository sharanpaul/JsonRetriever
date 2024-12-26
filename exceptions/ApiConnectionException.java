package api.exceptions;

public class ApiConnectionException extends ApiProcessingException {
    public ApiConnectionException(String s) {
        super("API Connection Failed: "+ s);
    }
}
