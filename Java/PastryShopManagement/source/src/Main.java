import domain.Comanda;
import domain.Tort;
import repository.*;
import service.ComandaService;
import service.TortService;
import ui.Console;
import ui.gui.HelloApplication;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("settings.properties"));
        } catch (IOException e) {
            System.out.println("Nu s-a găsit fișierul settings.properties");
            return;
        }

        String repoType = props.getProperty("Repository", "memory");
        String uiType = props.getProperty("UI", "console");
        String tortFile = props.getProperty("TortFile", "torturi.txt");
        String comandaFile = props.getProperty("ComandaFile", "comenzi.txt");

        Repository<Tort> tortRepo;
        Repository<Comanda> comandaRepo;

        if ("database".equalsIgnoreCase(repoType)) {
            tortRepo = new HibernateRepository<>(Tort.class);
            comandaRepo = new HibernateRepository<>(Comanda.class);
        } else if ("text".equalsIgnoreCase(repoType)) {
            tortRepo = new TextFileRepository<>(tortFile) {
                @Override protected Tort readEntity(String line) {
                    String[] parts = line.split(",");
                    return new Tort(Integer.parseInt(parts[0]), parts[1]);
                }
                @Override protected String writeEntity(Tort entity) {
                    return entity.getID() + "," + entity.getTipTortului();
                }
            };
            comandaRepo = new ComandaTextRepository(comandaFile, tortRepo);
        } else if ("binary".equalsIgnoreCase(repoType)) {
            tortRepo = new BinaryFileRepository<>(tortFile);
            comandaRepo = new BinaryFileRepository<>(comandaFile);
        } else {
            tortRepo = new InMemoryRepository<>();
            comandaRepo = new InMemoryRepository<>();
        }

        TortService tortService = new TortService(tortRepo);
        ComandaService comandaService = new ComandaService(comandaRepo, tortRepo);

        if ("database".equalsIgnoreCase(repoType) && tortRepo.findAll().isEmpty()) {
            generateRandomData(tortService, comandaService);
        }

        if ("gui".equalsIgnoreCase(uiType)) {
            HelloApplication.setServices(tortService, comandaService);
            javafx.application.Application.launch(HelloApplication.class, args);
        } else {
            Console console = new Console(tortService, comandaService);
            console.run();
        }
    }

    private static void generateRandomData(TortService ts, ComandaService cs) {
        System.out.println("Generating 100 random entities...");
        String[] types = {"Ciocolata", "Vanilie", "Fructe", "Caramel", "Tiramisu", "Red Velvet"};
        Random rand = new Random();
        List<Integer> tortIds = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String type = types[rand.nextInt(types.length)] + " Deluxe";
            ts.add(new Tort(i, type));
            tortIds.add(i);
        }

        for (int i = 1; i <= 100; i++) {
            List<Integer> orderContents = new ArrayList<>();
            int count = rand.nextInt(3) + 1;
            for (int k = 0; k < count; k++) {
                orderContents.add(tortIds.get(rand.nextInt(tortIds.size())));
            }
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -rand.nextInt(365));
            cs.add(i, orderContents, cal.getTime());
        }
        System.out.println("Done.");
    }
}