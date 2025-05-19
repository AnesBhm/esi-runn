package transport.core;

import java.time.LocalDate;
import java.time.Period;

public class CartePersonnelle extends TitreTransport {
    private final Personne proprietaire;
    private TypeCarte type;
    private final LocalDate dateExpiration;

    public CartePersonnelle(Personne proprietaire) {
        this.id = generateId();
        this.proprietaire = proprietaire;
        this.dateAchat = LocalDate.now();
        this.dateExpiration = dateAchat.plusYears(1);
        calculerReduction();
    }

    public void calculerReduction() {
        double reduction = 0;
        int age = Period.between(proprietaire.getDateNaissance(), LocalDate.now()).getYears();

        if (proprietaire.estHandicape())
            reduction = 0.5;
        else if (proprietaire instanceof Employe)
            reduction = 0.4;
        else if (age < 25)
            reduction = 0.3;
        else if (age > 65)
            reduction = 0.25;

        this.prix = 5000 * (1 - reduction);
        determinerType(reduction);
    }

    private void determinerType(double reduction) {
        if (reduction == 0.5)
            type = TypeCarte.SOLIDARITE;
        else if (reduction == 0.4)
            type = TypeCarte.PARTENAIRE;
        else if (reduction == 0.3)
            type = TypeCarte.JUNIOR;
        else
            type = TypeCarte.SENIOR;
    }

    @Override
    public boolean estValide(LocalDate dateValidation) throws TitreNonValideException {
        if (dateValidation.isAfter(dateExpiration)) {
            throw new TitreNonValideException("Card expired: " + id);
        }
        return true;
    }

    public TypeCarte getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format(
                "[%d] %s for %s on %s — %.2f DA via %s",
                id,
                getClass().getSimpleName(),
                proprietaire.toString(),
                dateAchat,
                prix,
                getPaymentMethod());
    }
}