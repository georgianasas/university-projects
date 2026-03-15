package service;

import domain.Comanda;
import domain.Tort;
import exceptions.AppException;
import repository.Repository;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ComandaService {
    private final Repository<Comanda> comenziRepo;
    private final Repository<Tort> tortRepo;

    public ComandaService(Repository<Comanda> comenziRepo, Repository<Tort> tortRepo) {
        this.comenziRepo = comenziRepo;
        this.tortRepo = tortRepo;
    }

    private void validate(Comanda c) {
        if (c.getTorturi() == null || c.getTorturi().isEmpty())
            throw new AppException("Comanda trebuie să conțină cel puțin un tort.");
        if (c.getData() == null)
            throw new AppException("Data comenzii este obligatorie.");
    }

    public void add(int id, List<Integer> tortIds, Date data) {
        List<Tort> torts = new ArrayList<>();
        for (int tid : tortIds) {
            Tort t = tortRepo.findById(tid);
            if (t == null) throw new AppException("Tort id " + tid + " nu exista.");
            torts.add(t);
        }
        Comanda c = new Comanda(id, torts, data);
        validate(c);
        comenziRepo.add(c);
    }

    public void update(int id, List<Integer> tortIds, Date data) {
        List<Tort> torts = new ArrayList<>();
        for (int tid : tortIds) {
            Tort t = tortRepo.findById(tid);
            if (t != null) torts.add(t);
        }
        Comanda c = new Comanda(id, torts, data);
        validate(c);
        comenziRepo.update(c);
    }

    public void delete(int id) {
        comenziRepo.delete(id);
    }

    public List<Comanda> all() {
        return comenziRepo.findAll();
    }


    public Map<String, Long> getTorturiPerZi() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return all().stream()
                .filter(c -> !c.getTorturi().isEmpty())
                .collect(Collectors.groupingBy(
                        c -> dtf.format(c.getData().toInstant().atZone(ZoneId.systemDefault())),
                        Collectors.summingLong(c -> c.getTorturi().size())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    public Map<String, Long> getTorturiPerLuna() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        return all().stream()
                .filter(c -> !c.getTorturi().isEmpty())
                .collect(Collectors.groupingBy(
                        c -> dtf.format(c.getData().toInstant().atZone(ZoneId.systemDefault())),
                        Collectors.summingLong(c -> c.getTorturi().size())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    public List<String> getCeleMaiComandateTorturi() {
        Map<Tort, Long> counts = all().stream()
                .flatMap(c -> c.getTorturi().stream())
                .collect(Collectors.groupingBy(t -> t, Collectors.counting()));

        return counts.entrySet().stream()
                .sorted(Map.Entry.<Tort, Long>comparingByValue().reversed())
                .map(e -> e.getKey().toString() + " -> " + e.getValue() + " comenzi")
                .collect(Collectors.toList());
    }
}