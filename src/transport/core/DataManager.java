package transport.core;
import java.io.*;
import java.util.*;

public class DataManager {
    private static final String DATA_FILE = "data/state.dat";

    public static void saveState(List<Personne> users, 
                               List<TitreTransport> fareMedia,
                               List<Reclamation> complaints) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
            oos.writeObject(fareMedia);
            oos.writeObject(complaints);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadState(List<Personne> users,
                                List<TitreTransport> fareMedia,
                                List<Reclamation> complaints) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(DATA_FILE))) {
            users.addAll((List<Personne>) ois.readObject());
            fareMedia.addAll((List<TitreTransport>) ois.readObject());
            complaints.addAll((List<Reclamation>) ois.readObject());
        }
    }
}