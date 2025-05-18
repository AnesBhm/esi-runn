package transport.core;

public class ReductionImpossibleException extends Exception {
    public ReductionImpossibleException() {
        super("Vous n'avez droit à aucune réduction.");
    }
}