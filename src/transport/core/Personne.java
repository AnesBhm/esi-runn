package transport.core;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Personne implements Serializable {
    protected String nom;
    protected String prenom;
    protected LocalDate dateNaissance;
    protected boolean estHandicape;

    public Personne(String nom, String prenom, LocalDate dateNaissance, boolean estHandicape) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.estHandicape = estHandicape;
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public boolean estHandicape() { return estHandicape; }
    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}