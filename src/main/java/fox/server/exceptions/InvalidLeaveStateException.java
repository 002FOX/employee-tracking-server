package fox.server.exceptions;

public class InvalidLeaveStateException extends RuntimeException{
    public InvalidLeaveStateException(String message) {
        super(message);
    }

}