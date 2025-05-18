package transport.core;
import java.time.LocalDate;

public class Employe extends Personne {
    private final String matricule;
    private final TypeFonction fonction;

    public Employe(String nom, String prenom, LocalDate dateNaissance, boolean estHandicape, 
                  String matricule, TypeFonction fonction) {
        super(nom, prenom, dateNaissance, estHandicape);
        this.matricule = matricule;
        this.fonction = fonction;
    }

    // Getters
    public String getMatricule() { return matricule; }
    public TypeFonction getFonction() { return fonction; }
}