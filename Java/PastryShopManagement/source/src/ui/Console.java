package ui;

import domain.Comanda;
import domain.Tort;
import exceptions.AppException;
import service.ComandaService;
import service.TortService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Console {
    private final TortService tortService;
    private final ComandaService comandaService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Console(TortService tortService, ComandaService comandaService) {
        this.tortService = tortService;
        this.comandaService = comandaService;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("alege: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1":
                        listTorturi();
                        break;
                    case "2":
                        addTort(sc);
                        break;
                    case "3":
                        updateTort(sc);
                        break;
                    case "4":
                        deleteTort(sc);
                        break;
                    case "5":
                        listComenzi();
                        break;
                    case "6":
                        addComanda(sc);
                        break;
                    case "7":
                        updateComanda(sc);
                        break;
                    case "8":
                        deleteComanda(sc);
                        break;
                    case "0":
                        System.out.println("la revedere!");
                        return;
                    default:
                        System.out.println("optiune invalida.");
                }
            } catch (RuntimeException e) {
                System.out.println("eroare: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== meniu comenzi torturi ===");
        System.out.println("1. lista torturi");
        System.out.println("2. adauga tort");
        System.out.println("3. actualizeaza tort");
        System.out.println("4. sterge tort");
        System.out.println("5. lista comenzi");
        System.out.println("6. adauga comanda");
        System.out.println("7. actualizeaza comanda");
        System.out.println("8. sterge comanda");
        System.out.println("0. iesire");
    }

    private void listTorturi() {
        List<Tort> l = tortService.all();
        if (l.isEmpty()) System.out.println("(fara torturi)");
        for (Tort t : l) System.out.println(t);
    }

    private void addTort(Scanner sc) {
        int id = readId(sc);
        System.out.print("tip tort: ");
        String tip = sc.nextLine();
        tortService.add(new Tort(id, tip));
        System.out.println("tort adaugat.");
    }

    private void updateTort(Scanner sc) {
        int id = readId(sc);
        System.out.print("tip nou: ");
        String tip = sc.nextLine();
        tortService.update(new Tort(id, tip));
        System.out.println("tort actualizat.");
    }

    private void deleteTort(Scanner sc) {
        int id = readId(sc);
        tortService.delete(id);
        System.out.println("tort sters.");
    }

    private void listComenzi() {
        List<Comanda> l = comandaService.all();
        if (l.isEmpty()) System.out.println("(fara comenzi)");
        for (Comanda c : l) System.out.println(c);
    }

    private void addComanda(Scanner sc) {
        int id = readId(sc);
        List<Integer> tortIds = readTortIds(sc);
        Date data = readDate(sc);
        comandaService.add(id, tortIds, data);
        System.out.println("comanda adaugata.");
    }

    private void updateComanda(Scanner sc) {
        int id = readId(sc);
        List<Integer> tortIds = readTortIds(sc);
        Date data = readDate(sc);
        comandaService.update(id, tortIds, data);
        System.out.println("comanda actualizata.");
    }

    private void deleteComanda(Scanner sc) {
        int id = readId(sc);
        comandaService.delete(id);
        System.out.println("comanda stearsa.");
    }

    private int readId(Scanner sc) {
        System.out.print("id: ");
        String s = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            if (id <= 0) throw new AppException("id-ul trebuie sa fie pozitiv (>= 1).");
            return id;
        } catch (NumberFormatException ex) {
            throw new AppException("id invalid (trebuie numar intreg).");
        }
    }

    private List<Integer> readTortIds(Scanner sc) {
        System.out.println("id-urile torturilor (separate prin spatiu): ");
        String line = sc.nextLine().trim();
        String[] parts = line.split("\\s+");
        List<Integer> ids = new ArrayList<>();
        for (String p : parts) if (!p.isBlank()) ids.add(Integer.parseInt(p));
        return ids;
    }

    private Date readDate(Scanner sc) {
        System.out.print("data (yyyy-mm-dd): ");
        String s = sc.nextLine().trim();
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            throw new AppException("format de data invalid.");
        }
    }
}
