package com.cp.georgianasas.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:recipes.db";
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                createTableAndInitData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void createTableAndInitData() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS recipes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                cooking_time INTEGER NOT NULL,
                ingredients TEXT NOT NULL
            );
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery("SELECT count(*) AS count FROM recipes");
            if (rs.next() && rs.getInt("count") == 0) {
                insertInitialData();
            }
        }
    }

    private static void insertInitialData() throws SQLException {
        String insertSQL = "INSERT INTO recipes (name, cooking_time, ingredients) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            addBatch(pstmt, "Chiftelute de linte", 50, "linte; ulei de masline; salota; usturoi; oua; condimente");
            addBatch(pstmt, "Quesadilla", 35, "tortilla; carne de pui; porumb; sos tzatziki; ceapa; condimente");
            addBatch(pstmt, "Supa de legume", 40, "mix de legume; usturoi; telina; ceapa; apa; rosii");
            addBatch(pstmt, "Pizza", 45, "faina; drojdie; apa; ulei de masline; sare; sos de rosii; mozzarella");
            addBatch(pstmt, "Tarta Lorraine", 60, "oua; unt; faina; apa; smantana; lapte; Emmentaler");
            pstmt.executeBatch();
        }
    }

    private static void addBatch(PreparedStatement pstmt, String name, int time, String ingr) throws SQLException {
        pstmt.setString(1, name);
        pstmt.setInt(2, time);
        pstmt.setString(3, ingr);
        pstmt.addBatch();
    }
}