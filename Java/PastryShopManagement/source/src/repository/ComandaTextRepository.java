package repository;

import domain.Comanda;
import domain.Tort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComandaTextRepository extends TextFileRepository<Comanda> {
    private final Repository<Tort> tortRepo;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ComandaTextRepository(String filename, Repository<Tort> tortRepo) {
        super(filename);
        this.tortRepo = tortRepo;


        super.loadFromFile();
    }

    @Override
    protected Comanda readEntity(String line) {
        try {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);


            Date date = sdf.parse(parts[2]);

            List<Tort> torts = new ArrayList<>();
            if (parts[1] != null && !parts[1].isEmpty()) {
                String[] ids = parts[1].split(";");
                for (String sId : ids) {

                    Tort t = tortRepo.findById(Integer.parseInt(sId));
                    if (t != null) torts.add(t);
                }
            }
            return new Comanda(id, torts, date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String writeEntity(Comanda entity) {
        StringBuilder sb = new StringBuilder();
        for (Tort t : entity.getTorturi()) {
            if (!sb.isEmpty()) sb.append(";");
            sb.append(t.getID());
        }
        return entity.getID() + "," + sb + "," + sdf.format(entity.getData());
    }
}