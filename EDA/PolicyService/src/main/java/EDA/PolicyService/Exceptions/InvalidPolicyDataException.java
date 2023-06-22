package EDA.PolicyService.Exceptions;

/**
 * A custom Exception that occurs when a policy that should be created has invalid data
 */
public class InvalidPolicyDataException extends RuntimeException {
    public InvalidPolicyDataException(String message) {
        super(message);
    }
}