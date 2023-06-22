package EDA.PolicyService.Exceptions;

/**
 * Custom exception that occurs when an error happens during database access.
 */
public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}