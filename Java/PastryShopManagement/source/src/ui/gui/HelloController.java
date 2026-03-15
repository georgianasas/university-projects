package ui.gui;

import domain.Comanda;
import domain.Tort;
import service.ComandaService;
import service.TortService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    private final TortService tortService;
    private final ComandaService comandaService;

    // --- Torts Tab ---
    @FXML private TableView<Tort> tableTort;
    @FXML private TableColumn<Tort, Integer> colTortId;
    @FXML private TableColumn<Tort, String> colTortTip;
    @FXML private TextField txtTortId, txtTortTip;

    // --- Comenzi Tab ---
    @FXML private TableView<Comanda> tableComanda;
    @FXML private TableColumn<Comanda, Integer> colCmdId;
    @FXML private TableColumn<Comanda, String> colCmdData;
    @FXML private TableColumn<Comanda, String> colCmdTorturi;
    @FXML private TextField txtCmdId, txtCmdTortIds;

    // --- Reports ---
    @FXML private TextArea txtReports;

    private ObservableList<Tort> tortList;
    private ObservableList<Comanda> cmdList;

    public HelloController(TortService ts, ComandaService cs) {
        this.tortService = ts;
        this.comandaService = cs;
    }

    @FXML
    public void initialize() {
        setupTortTable();
        setupComandaTable();
    }

    private void setupTortTable() {
        tortList = FXCollections.observableArrayList(tortService.all());
        colTortId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getID()).asObject());
        colTortTip.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipTortului()));
        tableTort.setItems(tortList);

        // Auto-fill fields when selecting a row
        tableTort.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtTortId.setText(String.valueOf(newVal.getID()));
                txtTortTip.setText(newVal.getTipTortului());
            }
        });
    }

    private void setupComandaTable() {
        cmdList = FXCollections.observableArrayList(comandaService.all());
        colCmdId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getID()).asObject());
        colCmdData.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getData().toString()));
        colCmdTorturi.setCellValueFactory(c -> {
            String names = c.getValue().getTorturi().stream()
                    .map(Tort::getTipTortului)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(names);
        });
        tableComanda.setItems(cmdList);

        // Auto-fill fields when selecting a row
        tableComanda.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtCmdId.setText(String.valueOf(newVal.getID()));
                String ids = newVal.getTorturi().stream()
                        .map(t -> String.valueOf(t.getID()))
                        .collect(Collectors.joining(","));
                txtCmdTortIds.setText(ids);
            }
        });
    }

    // --- TORT ACTIONS ---

    @FXML
    private void onAddTort() {
        try {
            int id = Integer.parseInt(txtTortId.getText());
            String tip = txtTortTip.getText();
            Tort t = new Tort(id, tip);
            tortService.add(t);
            tortList.add(t); // Update UI list immediately
            clearTortFields();
        } catch (Exception e) { showAlert(e.getMessage()); }
    }

    @FXML
    private void onUpdateTort() {
        try {
            int id = Integer.parseInt(txtTortId.getText());
            String tip = txtTortTip.getText();
            Tort t = new Tort(id, tip);
            tortService.update(t);

            // Refresh list
            tortList.setAll(tortService.all());
            tableTort.refresh();
            clearTortFields();
        } catch (Exception e) { showAlert(e.getMessage()); }
    }

    @FXML
    private void onDeleteTort() {
        Tort t = tableTort.getSelectionModel().getSelectedItem();
        if (t != null) {
            try {
                tortService.delete(t.getID());
                tortList.remove(t);
                clearTortFields();
            } catch (Exception e) { showAlert(e.getMessage()); }
        } else {
            showAlert("Selectează un tort din tabel pentru a șterge.");
        }
    }

    // --- COMANDA ACTIONS ---

    @FXML
    private void onAddComanda() {
        try {
            int id = Integer.parseInt(txtCmdId.getText());
            List<Integer> ids = parseIds(txtCmdTortIds.getText());
            comandaService.add(id, ids, new Date());
            cmdList.setAll(comandaService.all());
            clearCmdFields();
        } catch (Exception e) { showAlert(e.getMessage()); }
    }

    @FXML
    private void onUpdateComanda() {
        try {
            int id = Integer.parseInt(txtCmdId.getText());
            List<Integer> ids = parseIds(txtCmdTortIds.getText());
            // Update updates the content but keeps the original date (or updates date to now)
            comandaService.update(id, ids, new Date());

            cmdList.setAll(comandaService.all());
            tableComanda.refresh();
            clearCmdFields();
        } catch (Exception e) { showAlert(e.getMessage()); }
    }

    @FXML
    private void onDeleteComanda() {
        Comanda c = tableComanda.getSelectionModel().getSelectedItem();
        if (c != null) {
            try {
                comandaService.delete(c.getID());
                cmdList.remove(c);
                clearCmdFields();
            } catch (Exception e) { showAlert(e.getMessage()); }
        } else {
            showAlert("Selectează o comandă din tabel pentru a șterge.");
        }
    }

    // --- REPORT & HELPER ---

    @FXML
    private void onGenerateReports() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Torturi pe Zi ===\n");
            comandaService.getTorturiPerZi().forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));

            sb.append("\n=== Torturi pe Luna ===\n");
            comandaService.getTorturiPerLuna().forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));

            sb.append("\n=== Top Torturi ===\n");
            comandaService.getCeleMaiComandateTorturi().forEach(s -> sb.append(s).append("\n"));

            txtReports.setText(sb.toString());
        } catch (Exception e) {
            showAlert("Eroare generare rapoarte: " + e.getMessage());
        }
    }

    private List<Integer> parseIds(String input) {
        List<Integer> ids = new ArrayList<>();
        if (input.trim().isEmpty()) return ids;
        for (String s : input.split(",")) {
            try {
                ids.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                throw new RuntimeException("ID invalid: " + s);
            }
        }
        return ids;
    }

    private void clearTortFields() { txtTortId.clear(); txtTortTip.clear(); }
    private void clearCmdFields() { txtCmdId.clear(); txtCmdTortIds.clear(); }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }
}