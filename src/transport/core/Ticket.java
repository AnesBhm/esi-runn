package transport.core;
import java.time.LocalDate;

public class Ticket extends TitreTransport {
    public Ticket() {
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
}