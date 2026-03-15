package com.cp.georgianasas;

import com.cp.georgianasas.domain.Recipe;
import com.cp.georgianasas.service.RecipeService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HelloApplication extends Application {

    private RecipeService service = new RecipeService();
    private TableView<Recipe> table = new TableView<>();
    private ObservableList<Recipe> masterData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gestionare Retete ");

        TableColumn<Recipe, String> nameCol = new TableColumn<>("Nume");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(150);

        TableColumn<Recipe, Integer> timeCol = new TableColumn<>("Timp (min)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("cookingTime"));

        TableColumn<Recipe, String> ingrCol = new TableColumn<>("Ingrediente");
        ingrCol.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        ingrCol.setMinWidth(300);

        table.getColumns().addAll(nameCol, timeCol, ingrCol);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TextField filterField = new TextField();
        filterField.setPromptText("Filtreaza dupa ingrediente (ex: oua;faina)...");
        filterField.setMinWidth(300);

        Button filterBtn = new Button("Filtreaza");
        filterBtn.setOnAction(e -> updateTable(service.filterRecipes(filterField.getText())));

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            filterField.clear();
            updateTable(service.getAllSorted());
        });

        HBox filterBox = new HBox(10, new Label("Filtru:"), filterField, filterBtn, resetBtn);

        TextField addName = new TextField(); addName.setPromptText("Nume reteta");
        TextField addTime = new TextField(); addTime.setPromptText("Timp (min)");
        TextField addIngr = new TextField(); addIngr.setPromptText("Ingrediente (;)");

        Button addBtn = new Button("Adauga Reteta");
        addBtn.setOnAction(e -> {
            try {
                String name = addName.getText().trim();
                String timeStr = addTime.getText().trim();
                String ingr = addIngr.getText().trim();

                if (name.isEmpty() || timeStr.isEmpty() || ingr.isEmpty()) {
                    showAlert("Eroare", "Toate campurile sunt obligatorii!");
                    return;
                }

                int time = Integer.parseInt(timeStr);
                service.addRecipe(name, time, ingr);

                updateTable(service.getAllSorted());
                addName.clear(); addTime.clear(); addIngr.clear();
            } catch (NumberFormatException ex) {
                showAlert("Eroare", "Durata trebuie sa fie numar intreg!");
            } catch (Exception ex) {
                showAlert("Eroare", ex.getMessage());
            }
        });

        HBox addBox = new HBox(10, addName, addTime, addIngr, addBtn);

        Button shoppingListBtn = new Button("Creare Lista Cumparaturi");
        ListView<String> shoppingListView = new ListView<>();
        shoppingListView.setMaxHeight(150);

        shoppingListBtn.setOnAction(e -> {
            List<Recipe> selected = table.getSelectionModel().getSelectedItems();
            if (selected.isEmpty()) {
                showAlert("Info", "Selecteaza cel putin o reteta!");
            } else {
                shoppingListView.getItems().setAll(service.generateShoppingList(selected));
            }
        });

        VBox root = new VBox(15, filterBox, table, new Separator(), new Label("Adaugare:"), addBox, new Separator(), shoppingListBtn, shoppingListView);
        root.setPadding(new Insets(15));

        updateTable(service.getAllSorted());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void updateTable(List<Recipe> list) {
        masterData.setAll(list);
        table.setItems(masterData);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
