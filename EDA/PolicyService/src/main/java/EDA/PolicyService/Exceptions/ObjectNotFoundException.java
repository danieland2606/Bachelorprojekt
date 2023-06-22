package EDA.PolicyService.Exceptions;

/**
 * Custom exception that occurs when an object could not be found.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}