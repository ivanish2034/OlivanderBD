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

public class AddCoreDialog extends AbstractDialog {
    private JTextField nameField;
    private JTextArea descriptionArea;
    
    public AddCoreDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Добавить новый вид сердцевины", dbManager);
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(400, 300);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        formPanel.add(new JLabel("Название сердцевины:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Описание:"));
        descriptionArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descriptionArea));
        
        add(formPanel, BorderLayout.CENTER);
        
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveCore());
        add(saveButton, BorderLayout.SOUTH);
    }
    
    private void saveCore() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty()) {
            showMessage("Название сердцевины обязательно!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO core (name, description) VALUES (?, ?)")) {
            
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
            showMessage("Сердцевина успешно добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            showMessage("Ошибка при сохранении: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}