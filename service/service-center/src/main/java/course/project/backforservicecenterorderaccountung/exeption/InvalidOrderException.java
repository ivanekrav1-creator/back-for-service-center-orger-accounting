package course.project.backforservicecenterorderaccountung.exeption;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
