package sofka.reto.tour_backend.exception;

public class EntityByIdAlreadyExists extends Exception {

    public EntityByIdAlreadyExists(String message) {
        super(message);
    }

    public EntityByIdAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
