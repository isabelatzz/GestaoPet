package ProjetoPetShop.exception;

public class AnimalNaoExisteException extends RuntimeException {
    public AnimalNaoExisteException(String message) {
        super(message);
    }
}
