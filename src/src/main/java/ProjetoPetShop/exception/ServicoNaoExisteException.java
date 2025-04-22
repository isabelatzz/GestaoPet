package ProjetoPetShop.exception;

public class ServicoNaoExisteException extends RuntimeException {
    public ServicoNaoExisteException(String message) {
        super(message);
    }
}
