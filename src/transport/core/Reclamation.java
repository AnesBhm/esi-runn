package transport.core;

import java.io.Serializable;
import java.time.LocalDate;

public class Reclamation implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int compteur = 1;
    private int numero;
    private LocalDate date;
    private Personne personne;        // already implements Serializable
    private TypeReclamation type;     // enum, inherently Serializable
    private Suspendable cible;        // interface: implementations must also be Serializable
    private String description;       // String is Serializable

    public Reclamation(Personne personne,
                       TypeReclamation type,
                       Suspendable cible,
                       String description,
                       LocalDate date) {
        this.personne    = personne;
        this.type        = type;
        this.cible       = cible;
        this.description = description;
        this.date        = date;
        this.numero      = compteur++;
    }

    // … getters …
    public int getNumero() {
        return numero;
    }
    public LocalDate getDate() {
        return date;
    }
    public Personne getPersonne() {
        return personne;
    }
    public TypeReclamation getType() {
        return type;
    }
    public Suspendable getCible() {
        return cible;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(
          "Date: %s | Type: %s | Cible: %s | Desc: %s | By: %s",
          date, type, cible, description, personne);
    }
}
