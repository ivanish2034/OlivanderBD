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
import java.util.Map;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;

public class AddWandDialog extends JDialog {
    private JComboBox<String> cbWood;
    private JComboBox<String> cbCore;
    private JTextField txtLength;
    private JComboBox<String> cbFlexibility;
    private DatabaseManager dbManager;
    private static final String[] FLEXIBILITY_OPTIONS = {
        "Жесткая",
        "Средняя",
        "Гибкая",
        "Очень гибкая"
    };
    public AddWandDialog(JFrame parent) {
        super(parent, "Добавить палочку", true);
        this.dbManager = new DatabaseManager();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 250);

        // Загрузка данных из БД
        Map<Integer, String> woods = dbManager.getAllWoods();
        Map<Integer, String> cores = dbManager.getAllCores();

        add(new JLabel("Древесина:"));
        cbWood = new JComboBox<>(woods.values().toArray(new String[0]));
        add(cbWood);

        add(new JLabel("Сердцевина:"));
        cbCore = new JComboBox<>(cores.values().toArray(new String[0]));
        add(cbCore);

        add(new JLabel("Длина:"));
        txtLength = new JTextField();
        add(txtLength);

        add(new JLabel("Гибкость:"));
        cbFlexibility = new JComboBox<>(FLEXIBILITY_OPTIONS); // Инициализация комбобокса
        add(cbFlexibility);

        JButton btnAdd = new JButton("Добавить");
        btnAdd.addActionListener(e -> addWand());
        add(btnAdd);
    }

    private void addWand() {
        try {
            // Получаем выбранные ID
            int woodId = getSelectedId(cbWood, true);
            int coreId = getSelectedId(cbCore, false);

            // Проверяем, что ID валидны
            if (woodId == -1 || coreId == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Ошибка: не удалось определить ID выбранных материалов", 
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Проверка длины палочки
            String lengthText = txtLength.getText().trim();
            double length;
            try {
                length = Double.parseDouble(lengthText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Некорректное значение длины!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (length <= 0 || length > 99) {
                JOptionPane.showMessageDialog(this,
                        "Длина палочки должна быть положительным числом не более 99!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Wand wand = new Wand();
            wand.setWoodId(woodId);
            wand.setCoreId(coreId);
            wand.setLength(length);
            wand.setFlexibility((String) cbFlexibility.getSelectedItem());
            wand.setStatus("в наличии");

            if (dbManager.addWand(wand)) {
                JOptionPane.showMessageDialog(this, "Палочка добавлена!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Ошибка при добавлении палочки в БД", 
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка: " + ex.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSelectedId(JComboBox<String> comboBox, boolean isWood) {
        String selected = (String) comboBox.getSelectedItem();
        if (isWood) {
            return dbManager.getWoodIdByName(selected);
        } else {
            return dbManager.getCoreIdByName(selected);
        }
    }
}
