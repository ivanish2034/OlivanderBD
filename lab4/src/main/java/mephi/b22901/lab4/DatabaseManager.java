/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

/**
 *
 * @author ivis2
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DatabaseManager {
    
    private static final String URL = "jdbc:postgresql://aws-0-eu-north-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.bxjtwyvflpkcbduxyeja";
    private static final String PASSWORD = "45UsRu*mos-me_ph46";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // ========== Добавление дефолтных значений для древесины и сердцевины в БД ==========
    public void initializeDefaultData() {
        try (Connection conn = getConnection()) {
            if (getAllWoods().isEmpty()) {
                addWood("Драконий Гребень", "Древесина из сердца драконьего дерева, даёт мощные заклинания");
                addWood("Лунный Ясень", "Древесина, собранная в полнолуние, усиливает защитные заклинания");
                addWood("Фениксовый Тис", "Редкая древесина, способная восстанавливаться после повреждений");
            }
            if (getAllCores().isEmpty()) {
                addCore("Перо феникса", "Даёт палочке способность к самовосстановлению");
                addCore("Сердце дракона", "Обеспечивает мощные атакующие заклинания");
                addCore("Волос вейлы", "Усиливает магию предсказаний и ясновидения");
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при инициализации начальных данных");
        }
    }
    
    private void addWood(String name, String description) {
        String sql = "INSERT INTO wood (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении вида древесины: " + name);
        }
    }

    private void addCore(String name, String description) {
        String sql = "INSERT INTO core (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении вида сердцевины: " + name);
        }
    }
    
    // ========== Древесина ==========
    public void addWood(Wood wood) {
        String sql = "INSERT INTO wood (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, wood.getName());
            stmt.setString(2, wood.getDescription());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    wood.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении древесины");
        }
    }
    
    public List<Wood> getAllWoods() {
        List<Wood> woods = new ArrayList<>();
        String sql = "SELECT id, name, description FROM wood";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Wood wood = new Wood();
                wood.setId(rs.getInt("id"));
                wood.setName(rs.getString("name"));
                wood.setDescription(rs.getString("description"));
                woods.add(wood);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении видов древесины");
        }
        return woods;
    }
    
    // ========== Сердцевины ==========
    public void addCore(Core core) {
        String sql = "INSERT INTO core (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, core.getName());
            stmt.setString(2, core.getDescription());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    core.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении сердцевины");
        }
    }
    
    public List<Core> getAllCores() {
        List<Core> cores = new ArrayList<>();
        String sql = "SELECT id, name, description FROM core";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Core core = new Core();
                core.setId(rs.getInt("id"));
                core.setName(rs.getString("name"));
                core.setDescription(rs.getString("description"));
                cores.add(core);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении видов сердцевин");
        }
        return cores;
    }
    
    // ========== ПОКУПАТЕЛИ ==========
    public boolean addBuyer(Buyer buyer) {
        String sql = "INSERT INTO buyer (first_name, last_name) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, buyer.getFirstName());
            stmt.setString(2, buyer.getLastName());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    buyer.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении покупателя");
            return false;
        }
    }

    public List<Buyer> getAllBuyers() {
        List<Buyer> buyers = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name FROM buyer ORDER BY last_name, first_name";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Buyer buyer = new Buyer();
                buyer.setId(rs.getInt("id"));
                buyer.setFirstName(rs.getString("first_name"));
                buyer.setLastName(rs.getString("last_name"));
                buyers.add(buyer);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении покупателей");
        }
        return buyers;
    }

    public Buyer getBuyerById(int id) {
        String sql = "SELECT id, first_name, last_name FROM buyer WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Buyer buyer = new Buyer();
                    buyer.setId(rs.getInt("id"));
                    buyer.setFirstName(rs.getString("first_name"));
                    buyer.setLastName(rs.getString("last_name"));
                    return buyer;
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении покупателя");
        }
        return null;
    }

    // ========== ПАЛОЧКИ ==========
    public boolean addWand(Wand wand) {
//        String sql = "INSERT INTO wand (wood_id, core_id, length, flexibility, status) " +
//                    "VALUES (?, ?, ?, ?, 'in_storage')";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setInt(1, wand.getWoodId());
//            stmt.setInt(2, wand.getCoreId());
        String sql = "INSERT INTO wand (wood_id, core_id, length, flexibility, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, wand.getWood().getId());
            stmt.setInt(2, wand.getCore().getId());
            stmt.setDouble(3, wand.getLength());
            stmt.setString(4, wand.getFlexibility());
            stmt.setString(5, wand.getStatus());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    wand.setId(rs.getInt(1));
                    wand.setStatus("in_storage");
                }
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении палочки");
            return false;
        }
    }
    
    public List<Wand> getAllWands() {
        List<Wand> wands = new ArrayList<>();
        String sql = "SELECT id, wood_id, core_id, length, flexibility, status FROM wand";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Wand wand = new Wand();
                wand.setId(rs.getInt("id"));
//                wand.setWoodId(rs.getInt("wood_id"));
//                wand.setCoreId(rs.getInt("core_id"));
                // Создаем объекты Wood и Core
                Wood wood = new Wood();
                wood.setId(rs.getInt("wood_id"));
//                wood.setName(rs.getString("wood_name"));
//                wood.setDescription(rs.getString("wood_desc"));
                
                Core core = new Core();
                core.setId(rs.getInt("core_id"));
//                core.setName(rs.getString("core_name"));
//                core.setDescription(rs.getString("core_desc"));
                
                wand.setWood(wood);
                wand.setCore(core);
                wand.setLength(rs.getDouble("length"));
                wand.setFlexibility(rs.getString("flexibility"));
                wand.setStatus(rs.getString("status"));
                wands.add(wand);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении палочек");
        }
        return wands;
    }

//    public List<Wand> getWandsByStatus(String status) {
//        List<Wand> wands = new ArrayList<>();
//        String sql = "SELECT * FROM wand WHERE status = ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, status);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Wand wand = new Wand();
//                wand.setId(rs.getInt("id"));
//                wand.setWoodId(rs.getInt("wood_id"));
//                wand.setCoreId(rs.getInt("core_id"));
//                wand.setLength(rs.getDouble("length"));
//                wand.setFlexibility(rs.getString("flexibility"));
//                wand.setStatus(rs.getString("status"));
//                wands.add(wand);
//            }
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при получении палочек по статусу");
//        }
//        return wands;
//    }
    public List<Wand> getWandsByStatus(String status) {
        List<Wand> wands = new ArrayList<>();
        String sql = "SELECT w.*, wd.name as wood_name, wd.description as wood_desc, " +
                    "c.name as core_name, c.description as core_desc " +
                    "FROM wand w " +
                    "JOIN wood wd ON w.wood_id = wd.id " +
                    "JOIN core c ON w.core_id = c.id " +
                    "WHERE w.status = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Wand wand = new Wand();
                wand.setId(rs.getInt("id"));
                
                Wood wood = new Wood();
                wood.setId(rs.getInt("wood_id"));
                wood.setName(rs.getString("wood_name"));
//                wood.setDescription(rs.getString("wood_desc"));
                
                Core core = new Core();
                core.setId(rs.getInt("core_id"));
                core.setName(rs.getString("core_name"));
//                core.setDescription(rs.getString("core_desc"));
                
                wand.setWood(wood);
                wand.setCore(core);
                wand.setLength(rs.getDouble("length"));
                wand.setFlexibility(rs.getString("flexibility"));
                wand.setStatus(rs.getString("status"));
                
                wands.add(wand);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении палочек по статусу");
        }
        return wands;
    }
    
    public List<Wand> getWandsInStorage() {
        return getWandsByStatus("in_storage");
    }
    
    public List<Wand> getWandsInShop() {
        return getWandsByStatus("in_shop");
    }
    
    public List<Wand> getSoldWands() {
        return getWandsByStatus("sold");
    }
    
//    public Wand getWandById(int id) {
//        String sql = "SELECT * FROM wand WHERE id = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                Wand wand = new Wand();
//                wand.setId(rs.getInt("id"));
//                wand.setWoodId(rs.getInt("wood_id"));
//                wand.setCoreId(rs.getInt("core_id"));
//                wand.setLength(rs.getDouble("length"));
//                wand.setFlexibility(rs.getString("flexibility"));
//                wand.setStatus(rs.getString("status"));
//                return wand;
//            }
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при получении палочки по ID");
//        }
//        return null;
//    }
    public Wand getWandById(int id) {
        String sql = "SELECT w.*, wd.name as wood_name, wd.description as wood_desc, " +
                     "c.name as core_name, c.description as core_desc " +
                     "FROM wand w " +
                     "JOIN wood wd ON w.wood_id = wd.id " +
                     "JOIN core c ON w.core_id = c.id " +
                     "WHERE w.id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Wand wand = new Wand();
                wand.setId(rs.getInt("id"));
                
                Wood wood = new Wood();
                wood.setId(rs.getInt("wood_id"));
//                wood.setName(rs.getString("wood_name"));
//                wood.setDescription(rs.getString("wood_desc"));
                
                Core core = new Core();
                core.setId(rs.getInt("core_id"));
//                core.setName(rs.getString("core_name"));
//                core.setDescription(rs.getString("core_desc"));
                
                wand.setWood(wood);
                wand.setCore(core);
                wand.setLength(rs.getDouble("length"));
                wand.setFlexibility(rs.getString("flexibility"));
                wand.setStatus(rs.getString("status"));
                
                return wand;
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении палочки по ID");
        }
        return null;
    }
    
    public boolean moveWandToShop(int wandId) {
        String sql = "UPDATE wand SET status = 'in_shop' WHERE id = ? AND status = 'in_storage'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wandId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при перемещении палочки в магазин");
            return false;
        }
    }
    
    public boolean sellWand(int wandId, int buyerId, Date saleDate) {
        String updateWandSql = "UPDATE wand SET status = 'sold' WHERE id = ? AND status = 'in_shop'";
        String insertSaleSql = "INSERT INTO sale (wand_id, buyer_id, sale_date) VALUES (?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateWandSql);
                 PreparedStatement saleStmt = conn.prepareStatement(insertSaleSql)) {

                updateStmt.setInt(1, wandId);
                int updated = updateStmt.executeUpdate();
                if (updated == 0) {
                    conn.rollback();
                    return false;
                }

                saleStmt.setInt(1, wandId);
                saleStmt.setInt(2, buyerId);
                saleStmt.setDate(3, new java.sql.Date(saleDate.getTime()));
                saleStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при продаже палочки");
            return false;
        }
    }
    
    public int getWandStockQuantity(int wandId, String location) {
        String sql = "SELECT quantity FROM wand_stock WHERE wand_id = ? AND location = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wandId);
            stmt.setString(2, location);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении количества палочек");
            return 0;
        }
    }
    
    public boolean updateWandStatus(int wandId, String newStatus) {
        String sql = "UPDATE wand SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, wandId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при обновлении статуса палочки");
            return false;
        }
    }

    // ========== ДРЕВЕСИНА И СЕРДЦЕВИНЫ ==========
//    public Map<Integer, String> getAllWoods() {
//        Map<Integer, String> woods = new HashMap<>();
//        String sql = "SELECT id, name FROM wood";
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                woods.put(rs.getInt("id"), rs.getString("name"));
//            }
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при получении видов древесины");
//        }
//        return woods;
//    }
//
//    public Map<Integer, String> getAllCores() {
//        Map<Integer, String> cores = new HashMap<>();
//        String sql = "SELECT id, name FROM core";
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                cores.put(rs.getInt("id"), rs.getString("name"));
//            }
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при получении видов сердцевин");
//        }
//        return cores;
//    }

    public int getWoodIdByName(String name) {
        return getMaterialIdByName("wood", name);
    }

    public int getCoreIdByName(String name) {
        return getMaterialIdByName("core", name);
    }

    private int getMaterialIdByName(String tableName, String name) {
        String sql = "SELECT id FROM " + tableName + " WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении ID материала");
        }
        return -1;
    }

    // ========== ПРОДАЖИ ==========
    public boolean addSale(Sale sale) {
        String sql = "INSERT INTO sale (wand_id, buyer_id, sale_date) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setInt(1, sale.getWandId());
//            stmt.setInt(2, sale.getBuyerId());
            stmt.setInt(1, sale.getWand().getId());
            stmt.setInt(2, sale.getBuyer().getId());
            stmt.setDate(3, new java.sql.Date(sale.getSaleDate().getTime()));
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sale.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении продажи");
            return false;
        }
    }
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT s.*, w.*, b.first_name, b.last_name, " +
                    "wd.name as wood_name, wd.description as wood_desc, " +
                    "c.name as core_name, c.description as core_desc " +
                    "FROM sale s " +
                    "JOIN wand w ON s.wand_id = w.id " +
                    "JOIN buyer b ON s.buyer_id = b.id " +
                    "JOIN wood wd ON w.wood_id = wd.id " +
                    "JOIN core c ON w.core_id = c.id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("id"));
                
                // Создаем объект Wand
                Wand wand = new Wand();
                wand.setId(rs.getInt("wand_id"));
                
                Wood wood = new Wood();
                wood.setId(rs.getInt("wood_id"));
                wood.setName(rs.getString("wood_name"));
                wood.setDescription(rs.getString("wood_desc"));
                
                Core core = new Core();
                core.setId(rs.getInt("core_id"));
                core.setName(rs.getString("core_name"));
                core.setDescription(rs.getString("core_desc"));
                
                wand.setWood(wood);
                wand.setCore(core);
                wand.setLength(rs.getDouble("length"));
                wand.setFlexibility(rs.getString("flexibility"));
                wand.setStatus(rs.getString("status"));
                
                // Создаем объект Buyer
                Buyer buyer = new Buyer();
                buyer.setId(rs.getInt("buyer_id"));
                buyer.setFirstName(rs.getString("first_name"));
                buyer.setLastName(rs.getString("last_name"));
                
                sale.setWand(wand);
                sale.setBuyer(buyer);
                sale.setSaleDate(rs.getDate("sale_date"));
                
                sales.add(sale);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении продаж");
        }
        return sales;
    }
//    public List<Sale> getAllSales() {
//        List<Sale> sales = new ArrayList<>();
//        String sql = "SELECT id, wand_id, buyer_id, sale_date FROM sale";
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Sale sale = new Sale();
//                sale.setId(rs.getInt("id"));
//                sale.setWandId(rs.getInt("wand_id"));
//                sale.setBuyerId(rs.getInt("buyer_id"));
//                sale.setSaleDate(rs.getDate("sale_date"));
//                sales.add(sale);
//            }
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при получении продаж");
//        }
//        return sales;
//    }

    // ========== ПОСТАВКИ ==========
    public boolean addSupply(Supply supply) {
        String sql = "INSERT INTO supply (wand_id, quantity, supply_date, supplier) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, supply.getWand().getId());
//            stmt.setInt(2, supply.getQuantity());
//            stmt.setDate(3, new java.sql.Date(supply.getSupplyDate().getTime()));
//            stmt.setString(4, supply.getSupplier());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    supply.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при добавлении поставки");
            return false;
        }
    }
//    public boolean addSupply(Supply supply) {
//        String sql = "INSERT INTO supply (wand_id, quantity, supply_date, supplier) VALUES (?, ?, ?, ?)";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setInt(1, supply.getWandId());
//            stmt.executeUpdate();
//
//            try (ResultSet rs = stmt.getGeneratedKeys()) {
//                if (rs.next()) {
//                    supply.setId(rs.getInt(1));
//                }
//            }
//            return true;
//        } catch (SQLException e) {
//            handleSQLException(e, "Ошибка при добавлении поставки");
//            return false;
//        }
//    }

    // ========== ОЧИСТКА ДАННЫХ ==========
    public boolean clearAllData() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SET session_replication_role = replica;");
            
            stmt.executeUpdate("TRUNCATE TABLE sale, supply, wand, buyer, wood, core RESTART IDENTITY CASCADE");
            
            stmt.execute("SET session_replication_role = DEFAULT;");
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при очистке данных");
            return false;
        }
    }

    // ========== ИНФОРМАЦИЯ О СКЛАДЕ ==========
    public Map<String, Integer> getStockInfo(int wandId) {
        Map<String, Integer> stock = new HashMap<>();
        String sql = "SELECT location, quantity FROM wand_stock WHERE wand_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wandId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stock.put(rs.getString("location"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при получении информации о запасах");
        }
        return stock;
    }

    // ========== ОБРАБОТКА ОШИБОК ==========
    private void handleSQLException(SQLException e, String message) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}