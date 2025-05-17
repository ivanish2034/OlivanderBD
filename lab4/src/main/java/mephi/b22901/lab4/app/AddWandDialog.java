/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;
/**
 *
 * @author ivis2
 */

public class AddWandDialog extends AbstractDialog {
    private JComboBox<String> cbWood;
    private JComboBox<String> cbCore;
    private JTextField txtLength;
    private JComboBox<String> cbFlexibility;
    private static final String[] FLEXIBILITY_OPTIONS = {
        "Жесткая", "Средняя", "Гибкая", "Очень гибкая"
    };

    public AddWandDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Добавить палочку", dbManager);
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 250);

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
        cbFlexibility = new JComboBox<>(FLEXIBILITY_OPTIONS);
        add(cbFlexibility);

        JButton btnAdd = new JButton("Добавить");
        btnAdd.addActionListener(e -> addWand());
        add(btnAdd);
    }

    private void addWand() {
        try {
            int woodId = getSelectedId(cbWood, true);
            int coreId = getSelectedId(cbCore, false);

            if (woodId == -1 || coreId == -1) {
                showMessage("Не удалось определить ID выбранных материалов", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String lengthText = txtLength.getText().trim();
            double length = Double.parseDouble(lengthText);

            if (length <= 0 || length > 99) {
                showMessage("Длина должна быть от 0 до 99", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Wand wand = new Wand();
            wand.setWoodId(woodId);
            wand.setCoreId(coreId);
            wand.setLength(length);
            wand.setFlexibility((String) cbFlexibility.getSelectedItem());
            wand.setStatus("в наличии");

            if (dbManager.addWand(wand)) {
                showMessage("Палочка добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showMessage("Ошибка при добавлении палочки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            showMessage("Некорректное значение длины", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            showMessage("Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSelectedId(JComboBox<String> comboBox, boolean isWood) {
        String selected = (String) comboBox.getSelectedItem();
        return isWood ? dbManager.getWoodIdByName(selected) : dbManager.getCoreIdByName(selected);
    }
}