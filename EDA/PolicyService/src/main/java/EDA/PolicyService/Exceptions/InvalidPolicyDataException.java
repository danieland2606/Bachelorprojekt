package EDA.PolicyService.Exceptions;

public class InvalidPolicyDataException extends RuntimeException {
    public InvalidPolicyDataException(String message) {
        super(message);
    }
}