package transport.core;
import java.time.LocalDate;

public class Usager extends Personne {
    private static int idCounter = 0;
    private final int idUsager;

    public Usager(String nom, String prenom, LocalDate dateNaissance, boolean estHandicape) {
        super(nom, prenom, dateNaissance, estHandicape);
        this.idUsager = ++idCounter;
    }

    public int getIdUsager() { return idUsager; }
}