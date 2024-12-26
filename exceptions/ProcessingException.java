package api.exceptions;

public class ProcessingException extends ApiProcessingException{
    public ProcessingException(String message) {
        super("Error During Post Processing: "+message);
    }
}
