package transport.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;

public class ServiceReclamation {

    private final int SEUIL = 3;
    private final Map<TypeReclamation, TreeSet<Reclamation>> reclamationsParType = new TreeMap<>();
    private final Map<Personne, TreeSet<Reclamation>> reclamationsParPersonne = new HashMap<>();
    private final Map<Suspendable, TreeSet<Reclamation>> reclamationsParSuspendable = new HashMap<>();

    public Optional<String> soumettre(Reclamation reclamation) {
    // Add to reclamationsParType, sorted by numero
    addToMap(reclamationsParType, reclamation.getType(), reclamation,
        new Comparator<Reclamation>() {
            @Override
            public int compare(Reclamation r1, Reclamation r2) {
                return Integer.compare(r1.getNumero(), r2.getNumero());
            }
        }
    );

    // Add to reclamationsParPersonne, sorted by type then numero
    addToMap(reclamationsParPersonne, reclamation.getPersonne(), reclamation,
        new Comparator<Reclamation>() {
            @Override
            public int compare(Reclamation r1, Reclamation r2) {
                int typeCompare = r1.getType().compareTo(r2.getType());
                if (typeCompare != 0) {
                    return typeCompare;
                }
                return Integer.compare(r1.getNumero(), r2.getNumero());
            }
        }
    );

    // Add to reclamationsParSuspendable, sorted by type then numero
    addToMap(reclamationsParSuspendable, reclamation.getCible(), reclamation,
        new Comparator<Reclamation>() {
            @Override
            public int compare(Reclamation r1, Reclamation r2) {
                int typeCompare = r1.getType().compareTo(r2.getType());
                if (typeCompare != 0) {
                    return typeCompare;
                }
                return Integer.compare(r1.getNumero(), r2.getNumero());
            }
        }
    );

    // Check if the cible should be suspended
    checkSuspension(reclamation.getCible());
    if (reclamation.getCible().estSuspendu()) {
            return Optional.of(
                String.format("%s a été suspendu après %d réclamations.",
                              reclamation.getCible(),
                              reclamationsParSuspendable.get(reclamation.getCible()).size()));
        }
        return Optional.empty();
}

    private <K> void addToMap(Map<K, TreeSet<Reclamation>> map, K key, Reclamation rec, Comparator<Reclamation> comp) {
        if (!map.containsKey(key)) {
            map.put(key, new TreeSet<>(comp));
        }
        map.get(key).add(rec);
    }

    private void checkSuspension(Suspendable cible) {
        int count = reclamationsParSuspendable.getOrDefault(cible, new TreeSet<>()).size();
        if (count >= SEUIL) {
            cible.suspendre(); 
        }else {
            cible.reactiver();
        }
    }

    public void resoudre(Reclamation reclamation) {
        removeFromMap(reclamationsParType, reclamation.getType(), reclamation);
        removeFromMap(reclamationsParPersonne, reclamation.getPersonne(), reclamation);
        removeFromMap(reclamationsParSuspendable, reclamation.getCible(), reclamation);
        checkSuspension(reclamation.getCible());
        checkSuspension(reclamation.getCible());
    }

    private <K> void removeFromMap(Map<K, TreeSet<Reclamation>> map, K key, Reclamation rec) {
        if (map.containsKey(key)) {
            map.get(key).remove(rec);
            if (map.get(key).isEmpty()) {
                map.remove(key);
            }
        }
    }

    // Display methods
    public void afficherReclamations() {
        reclamationsParType.forEach((type, set) -> {
            System.out.println("\n--- Reclamations de type " + type);
            for (Reclamation rec : set) {
                System.out.println("Reclamation #" + rec.getNumero() + " : ");
                System.out.println(rec);
            }
        });
    }

    //overload
    public void afficherReclamations(Personne p) {
        System.out.println("\nReclamations soumises par " + p);
        TreeSet<Reclamation> reclamations = reclamationsParPersonne.get(p);
        if (reclamations != null) {
            for (Reclamation rec : reclamations) {
                System.out.println("Reclamation #" + rec.getNumero() + " : ");
                System.out.println(rec);
            }
        }
    }

    //overload
    public void afficherReclamations(Suspendable cible) {
        TreeSet<Reclamation> reclamations = reclamationsParSuspendable.get(cible);
        if (reclamations != null) {
            for (Reclamation rec : reclamations) {
                System.out.println("Reclamation #" + rec.getNumero() + " : ");
                System.out.println(rec);
            }
        }
    }
     public List<Reclamation> getAllReclamations() {
        return reclamationsParType.values().stream()
                  .flatMap(Set::stream)
                  .sorted(Comparator.comparingInt(Reclamation::getNumero))
                  .collect(Collectors.toList());
    }

}
