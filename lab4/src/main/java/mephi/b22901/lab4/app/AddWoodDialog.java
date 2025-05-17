/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import mephi.b22901.lab4.DatabaseManager;
/**
 *
 * @author ivis2
 */

public class AddWoodDialog extends AbstractDialog {
    private JTextField nameField;
    private JTextArea descriptionArea;

    public AddWoodDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Добавить древесину", dbManager);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        
        formPanel.add(new JLabel("Название древесины:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Описание:"));
        descriptionArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descriptionArea));
        
        add(formPanel, BorderLayout.CENTER);

        JButton btnSave = new JButton("Сохранить");
        btnSave.addActionListener(e -> saveWood());
        add(btnSave, BorderLayout.SOUTH);
    }

    private void saveWood() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty()) {
            showMessage("Название обязательно!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO wood (name, description) VALUES (?, ?)")) {
            
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
            showMessage("Древесина добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            showMessage("Ошибка при сохранении: " + ex.getMessage(), 
                       "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}