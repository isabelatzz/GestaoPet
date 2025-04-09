package ProjetoPetShop.exception;

public class AnimalNaoExiste extends RuntimeException {
    public AnimalNaoExiste(String message) {
        super(message);
    }
}
