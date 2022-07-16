package sofka.reto.tour_backend.exception;

public class FullRidersOnTeamException extends Exception {

    public FullRidersOnTeamException(String message) {
        super(message);
    }

    public FullRidersOnTeamException(String message, Throwable cause) {
        super(message, cause);
    }
}
