package domain.exceptions;

public class MissingInformation extends RuntimeException {
    public MissingInformation(String message) {
        super(message);
    }
}
