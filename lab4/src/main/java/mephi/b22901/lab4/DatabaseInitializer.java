/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

/**
 *
 * @author ivis2
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//public class DatabaseInitializer {
//    public static void initializeDatabase(Connection conn) throws SQLException {
//        try (Statement stmt = conn.createStatement()) {
//            stmt.execute("DROP TABLE IF EXISTS sale CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS supply CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS wand_stock CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS wand CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS buyer CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS wood CASCADE");
//            stmt.execute("DROP TABLE IF EXISTS core CASCADE");
//
//            stmt.execute("CREATE TABLE IF NOT EXISTS buyer (" +
//                "id SERIAL PRIMARY KEY, " +
//                "first_name VARCHAR(100) NOT NULL, " +
//                "last_name VARCHAR(100) NOT NULL)");
//
//            stmt.execute("CREATE TABLE IF NOT EXISTS wood (" +
//                "id SERIAL PRIMARY KEY, " +
//                "name VARCHAR(100) NOT NULL UNIQUE, " +
//                "description TEXT)");
//
//            stmt.execute("CREATE TABLE IF NOT EXISTS core (" +
//                "id SERIAL PRIMARY KEY, " +
//                "name VARCHAR(100) NOT NULL UNIQUE, " +
//                "description TEXT)");
//
//            stmt.execute("CREATE TABLE IF NOT EXISTS wand (" +
//                "id SERIAL PRIMARY KEY, " +
//                "wood_id INTEGER REFERENCES wood(id), " +
//                "core_id INTEGER REFERENCES core(id), " +
//                "length DECIMAL(4,2) NOT NULL CHECK (length > 0), " +
//                "flexibility VARCHAR(50) NOT NULL, " +
//                "status VARCHAR(20) NOT NULL DEFAULT 'in_storage' " +
//                "  CHECK (status IN ('in_storage', 'in_shop', 'sold')), " +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
//            
//            stmt.execute("CREATE TABLE IF NOT EXISTS sale (" +
//                "id SERIAL PRIMARY KEY, " +
//                "wand_id INTEGER REFERENCES wand(id), " +
//                "buyer_id INTEGER REFERENCES buyer(id), " +
//                "sale_date DATE NOT NULL, ");
//
//            stmt.execute("CREATE TABLE IF NOT EXISTS supply (" +
//                "id SERIAL PRIMARY KEY, " +
//                "wand_id INTEGER REFERENCES wand(id) " +
//                    ")");
//
//            addDefaultWoods(conn);
//            addDefaultCores(conn);
//        }
//    }
//   
//    private static void addDefaultWoods(Connection conn) throws SQLException {
//        String checkSql = "SELECT COUNT(*) FROM wood";
//        try (Statement checkStmt = conn.createStatement();
//             ResultSet rs = checkStmt.executeQuery(checkSql)) {
//            if (rs.next() && rs.getInt(1) == 0) {
//                String[][] woods = {
//                    {"Драконий Гребень", "Древесина из сердца драконьего дерева, даёт мощные заклинания"},
//                    {"Лунный Ясень", "Древесина, собранная в полнолуние, усиливает защитные заклинания"},
//                    {"Фениксовый Тис", "Редкая древесина, способная восстанавливаться после повреждений"}
//                };
//
//                try (PreparedStatement insertStmt = conn.prepareStatement(
//                        "INSERT INTO wood (name, description) VALUES (?, ?)")) {
//                    for (String[] wood : woods) {
//                        insertStmt.setString(1, wood[0]);
//                        insertStmt.setString(2, wood[1]);
//                        insertStmt.executeUpdate();
//                    }
//                }
//            }
//        }
//    }
//
//    private static void addDefaultCores(Connection conn) throws SQLException {
//        String checkSql = "SELECT COUNT(*) FROM core";
//        try (Statement checkStmt = conn.createStatement();
//             ResultSet rs = checkStmt.executeQuery(checkSql)) {
//            if (rs.next() && rs.getInt(1) == 0) {
//                String[][] cores = {
//                    {"Перо феникса", "Даёт палочке способность к самовосстановлению"},
//                    {"Сердце дракона", "Обеспечивает мощные атакующие заклинания"},
//                    {"Волос вейлы", "Усиливает магию предсказаний и ясновидения"}
//                };
//
//                try (PreparedStatement insertStmt = conn.prepareStatement(
//                        "INSERT INTO core (name, description) VALUES (?, ?)")) {
//                    for (String[] core : cores) {
//                        insertStmt.setString(1, core[0]);
//                        insertStmt.setString(2, core[1]);
//                        insertStmt.executeUpdate();
//                    }
//                }
//            }
//        }
//    }
//}