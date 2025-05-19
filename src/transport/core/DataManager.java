package transport.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataManager {
    // Define the directory and file paths
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path DATA_FILE = DATA_DIR.resolve("state.dat");

    public static void saveState(List<Personne> users,
                                 List<TitreTransport> fareMedia,
                                 List<Reclamation> complaints) throws IOException {
        // Ensure the data directory exists
        if (Files.notExists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }

        // Write objects to the state file
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE.toFile()))) {
            oos.writeObject(users);
            oos.writeObject(fareMedia);
            oos.writeObject(complaints);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadState(List<Personne> users,
                                 List<TitreTransport> fareMedia,
                                 List<Reclamation> complaints) throws IOException, ClassNotFoundException {
        // Ensure the data directory exists
        if (Files.notExists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }

        // If the state file does not exist yet, start with empty lists
        if (Files.notExists(DATA_FILE)) {
            System.out.println("No existing state file; starting fresh.");
            return;
        }

        // Read objects from the state file
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE.toFile()))) {
            users.addAll((List<Personne>) ois.readObject());
            fareMedia.addAll((List<TitreTransport>) ois.readObject());
            complaints.addAll((List<Reclamation>) ois.readObject());
        }
    }
}
