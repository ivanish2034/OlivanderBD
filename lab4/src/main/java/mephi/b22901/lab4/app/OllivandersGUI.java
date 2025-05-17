/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

/**
 *
 * @author ivis2
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import mephi.b22901.lab4.Buyer;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Sale;
import mephi.b22901.lab4.Wand;

public class OllivandersGUI {
    private JFrame frame;
    private DatabaseManager dbManager;
    
    public OllivandersGUI() {
        dbManager = new DatabaseManager();
        initializeDefaultData(); 
        initialize();
    }

    private void initializeDefaultData() {
        try {
            dbManager.initializeDefaultData(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Ошибка инициализации начальных данных: " + e.getMessage(),
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void initialize() {
        frame = new JFrame("Система учёта волшебных палочек Олливандера");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAddBuyer = createStyledButton("Добавить покупателя", e -> showAddBuyerDialog());
        JButton btnAddWand = createStyledButton("Добавить палочку", e -> showAddWandDialog());
        JButton btnTrackStock = createStyledButton("Отслеживать запасы", e -> trackStock());
        JButton btnMoveToShop = createStyledButton("Переместить в магазин", e -> showMoveToShopDialog());
        JButton btnSellWand = createStyledButton("Продать палочку", e -> showSellWandDialog());
        JButton btnViewSales = createStyledButton("Просмотреть продажи", e -> showSalesReport());
        JButton btnClearData = createStyledButton("Очистить данные", e -> clearAllData());
        JButton btnViewBuyers = createStyledButton("Список покупателей", e -> showBuyersList());
        JButton addWoodButton = createStyledButton("Добавить древесину", e -> showAddWoodDialog());
        JButton addCoreButton = createStyledButton("Добавить сердцевину", e -> showAddCoreDialog());

        buttonPanel.add(addWoodButton);
        buttonPanel.add(addCoreButton);
        buttonPanel.add(btnAddBuyer);
        buttonPanel.add(btnAddWand);
        buttonPanel.add(btnTrackStock);
        buttonPanel.add(btnMoveToShop);
        buttonPanel.add(btnSellWand);
        buttonPanel.add(btnViewSales);
        buttonPanel.add(btnClearData);
        buttonPanel.add(btnViewBuyers);

        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        
        JLabel titleLabel = new JLabel("Магазин волшебных палочек Олливандера", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 200)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setFocusPainted(false);
        return button;
    }

    private void showAddBuyerDialog() {
        AddBuyerDialog dialog = new AddBuyerDialog(frame);
        dialog.setVisible(true);
    }

    private void showAddWandDialog() {
        AddWandDialog dialog = new AddWandDialog(frame);
        dialog.setVisible(true);
    }
    
    private void showAddWoodDialog() {
        AddWoodDialog dialog = new AddWoodDialog(frame, dbManager);
        dialog.setVisible(true);
    }

    private void showAddCoreDialog() {
        AddCoreDialog dialog = new AddCoreDialog(frame, dbManager);
        dialog.setVisible(true);
    }
    
    private void showMoveToShopDialog() {
        MakeSupplyDialog dialog = new MakeSupplyDialog(frame);
        dialog.setVisible(true);
    }
    
    private void trackStock() {
        StringBuilder stockInfo = new StringBuilder("=== Инвентаризация палочек ===\n\n");
        Map<Integer, String> woods = dbManager.getAllWoods();
        Map<Integer, String> cores = dbManager.getAllCores();

        stockInfo.append("=== На складе ===\n");
        List<Wand> storageWands = dbManager.getWandsInStorage();
        if (storageWands.isEmpty()) {
            stockInfo.append("Нет палочек на складе\n");
        } else {
            for (Wand wand : storageWands) {
                stockInfo.append(String.format("ID: %d | Древесина: %s | Сердцевина: %s | Длина: %.2f\" | Гибкость: %s\n",
                    wand.getId(), 
                    woods.get(wand.getWoodId()),
                    cores.get(wand.getCoreId()),
                    wand.getLength(),
                    wand.getFlexibility()));
            }
        }
        stockInfo.append("\n=== В магазине (готовы к продаже) ===\n");
        List<Wand> shopWands = dbManager.getWandsInShop();
        if (shopWands.isEmpty()) {
            stockInfo.append("Нет палочек в магазине\n");
        } else {
            for (Wand wand : shopWands) {
                stockInfo.append(String.format("ID: %d | Древесина: %s | Сердцевина: %s | Длина: %.2f\" | Гибкость: %s\n",
                    wand.getId(),
                    woods.get(wand.getWoodId()),
                    cores.get(wand.getCoreId()),
                    wand.getLength(),
                    wand.getFlexibility()));
            }
        }
        stockInfo.append("\n=== Проданные палочки ===\n");
        List<Wand> soldWands = dbManager.getSoldWands();
        if (soldWands.isEmpty()) {
            stockInfo.append("Нет проданных палочек\n");
        } else {
            for (Wand wand : soldWands) {
                stockInfo.append(String.format("ID: %d | Древесина: %s | Сердцевина: %s | Длина: %.2f\"\n",
                    wand.getId(),
                    woods.get(wand.getWoodId()),
                    cores.get(wand.getCoreId()),
                    wand.getLength()));
            }
        }

        showScrollableMessage("Инвентаризация палочек", stockInfo.toString());
    }

    private void showSellWandDialog() {
        SellWandDialog dialog = new SellWandDialog(frame);
        dialog.setVisible(true);
    }

    private void showSalesReport() {
        List<Sale> sales = dbManager.getAllSales();
        StringBuilder report = new StringBuilder("=== Отчет о продажах ===\n\n");
        Map<Integer, String> woods = dbManager.getAllWoods();
        Map<Integer, String> cores = dbManager.getAllCores();
        
        if (sales.isEmpty()) {
            report.append("Нет данных о продажах\n");
        } else {
            for (Sale sale : sales) {
                Wand wand = dbManager.getWandById(sale.getWandId());
                Buyer buyer = dbManager.getBuyerById(sale.getBuyerId());
                
                if (wand != null && buyer != null) {
                    report.append(String.format("Дата: %s | Палочка ID: %d (Древесина: %s, Сердцевина: %s) | Покупатель: %s %s\n",
                        sale.getSaleDate(), 
                        wand.getId(),
                        woods.get(wand.getWoodId()),
                        cores.get(wand.getCoreId()),
                        buyer.getFirstName(), 
                        buyer.getLastName()));
                }
            }
        }

        showScrollableMessage("Отчет о продажах", report.toString());
    }

    private void showBuyersList() {
        List<Buyer> buyers = dbManager.getAllBuyers();
        StringBuilder report = new StringBuilder("=== Список покупателей ===\n\n");
        
        if (buyers.isEmpty()) {
            report.append("Нет зарегистрированных покупателей\n");
        } else {
            for (Buyer buyer : buyers) {
                report.append(String.format("ID: %d | Имя: %s %s\n",
                    buyer.getId(),
                    buyer.getFirstName(),
                    buyer.getLastName()));
            }
        }

        showScrollableMessage("Список покупателей", report.toString());
    }

    private void showScrollableMessage(String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        JOptionPane.showMessageDialog(frame, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearAllData() {
        int confirm = JOptionPane.showConfirmDialog(frame, 
            "Вы уверены, что хотите очистить все данные?\nЭто действие нельзя отменить!", 
            "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dbManager.clearAllData()) {
                JOptionPane.showMessageDialog(frame, 
                    "Все данные успешно очищены!", 
                    "Информация", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Ошибка при очистке данных!", 
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}