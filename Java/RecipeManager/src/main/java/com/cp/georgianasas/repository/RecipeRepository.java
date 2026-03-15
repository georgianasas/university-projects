package com.cp.georgianasas.repository;

import com.cp.georgianasas.domain.Recipe;
import com.cp.georgianasas.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    public List<Recipe> getAll() {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("cooking_time"),
                        rs.getString("ingredients")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void add(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipes(name, cooking_time, ingredients) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, recipe.getName());
            pstmt.setInt(2, recipe.getCookingTime());
            pstmt.setString(3, recipe.getIngredients());
            pstmt.executeUpdate();
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT count(*) FROM recipes WHERE lower(name) = lower(?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}