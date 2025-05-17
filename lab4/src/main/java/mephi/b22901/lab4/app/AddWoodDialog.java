/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

/**
 *
 * @author ivis2
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import mephi.b22901.lab4.DatabaseManager;

public class AddWoodDialog extends JDialog {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JButton saveButton;
    private DatabaseManager dbManager;
    
    public AddWoodDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Добавить новый вид древесины", true);
        this.dbManager = dbManager;
        setLayout(new BorderLayout());
        setSize(400, 300);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        formPanel.add(new JLabel("Название древесины:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Описание:"));
        descriptionArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descriptionArea));
        
        add(formPanel, BorderLayout.CENTER);
        
        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveWood());
        add(saveButton, BorderLayout.SOUTH);
    }

    private void saveWood() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Название древесины обязательно!");
            return;
        }
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO wood (name, description) VALUES (?, ?)")) {
            
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Древесина успешно добавлена!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка при сохранении: " + ex.getMessage());
        }
    }
}