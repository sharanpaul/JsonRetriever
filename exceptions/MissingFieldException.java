package api.exceptions;

public class MissingFieldException extends ApiProcessingException{
    public MissingFieldException(String s) {
        super("Missing required fields in post data: " + s);
    }
}
