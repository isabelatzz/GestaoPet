package ProjetoPetShop.exception;

public class ServicoJaExisteException extends RuntimeException {
    public ServicoJaExisteException(String message) {
        super(message);
    }
}
