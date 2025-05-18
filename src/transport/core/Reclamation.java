package transport.core;

import java.time.LocalDate;

public class Reclamation {

    private static int compteur = 1;
    private int numero;
    private LocalDate date;
    private Personne personne;
    private TypeReclamation type;
    private Suspendable cible;
    private String description;

    public Reclamation(Personne personne, TypeReclamation type, Suspendable cible, String description, LocalDate date) {
        this.personne = personne;
        this.type = type;
        this.cible = cible;
        this.description = description;
        this.date = date;
        this.numero = compteur;
        compteur++;
    }

    public Suspendable getCible() {
        return cible;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getDate() {
        return date;
    }

    public Personne getPersonne() {
        return personne;
    }

    public String getDescription() {
        return description;
    }

    public TypeReclamation getType() {
        return type;
    }

    @Override

    public String toString() {
        return String.format("Date : %s | Type : %s | Cible : %s | Description : %s | Soumise par : %s",
                date.toString(),
                type.toString(),
                cible.toString(),
                description,
                personne.toString());
    }
}
