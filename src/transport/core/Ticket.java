package transport.core;

import java.time.LocalDate;

public class Ticket extends TitreTransport {

    private final Personne proprietaire;

    public Ticket(Personne proprietaire) {
        this.proprietaire = proprietaire;
        this.id = generateId();
        this.dateAchat = LocalDate.now();
        this.prix = 50.0;
    }

    @Override
    public boolean estValide(LocalDate dateValidation) throws TitreNonValideException {
        if (!dateAchat.isEqual(dateValidation)) {
            throw new TitreNonValideException("Ticket expired. Valid only on: " + dateAchat);
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s for %s on %s — %.2f DA via %s",
                this.getClass().getSimpleName(),
                proprietaire,
                dateAchat,
                prix,
                getPaymentMethod());
    }
}