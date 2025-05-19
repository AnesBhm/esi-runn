package transport.core;
import java.time.LocalDate;

public class Usager extends Personne {
    private final int idUsager;

    public Usager(String nom, String prenom, LocalDate dateNaissance, boolean estHandicape) {
        super(nom, prenom, dateNaissance, estHandicape);
        this.idUsager = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    public int getIdUsager() { return idUsager; }
}