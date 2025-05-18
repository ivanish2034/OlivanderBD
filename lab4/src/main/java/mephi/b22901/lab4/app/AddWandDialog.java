/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import mephi.b22901.lab4.Core;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;
import mephi.b22901.lab4.Wood;
import mephi.b22901.lab4.app.renderer.*;
/**
 *
 * @author ivis2
 */
public class AddWandDialog extends AbstractDialog {
    private JComboBox<Wood> cbWood;
    private JComboBox<Core> cbCore;
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

        List<Wood> woods = dbManager.getAllWoods();
        List<Core> cores = dbManager.getAllCores();

        add(new JLabel("Древесина:"));
        cbWood = new JComboBox<>(woods.toArray(new Wood[0]));
        cbWood.setRenderer(new WoodComboBoxRenderer());
        add(cbWood);

        add(new JLabel("Сердцевина:"));
        cbCore = new JComboBox<>(cores.toArray(new Core[0]));
        cbCore.setRenderer(new CoreComboBoxRenderer());
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
            Wood selectedWood = (Wood) cbWood.getSelectedItem();
            Core selectedCore = (Core) cbCore.getSelectedItem();

            String lengthText = txtLength.getText().trim();
            double length = Double.parseDouble(lengthText);

            if (length <= 0 || length > 99) {
                showMessage("Длина должна быть от 0 до 99", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Wand wand = new Wand();
            wand.setWood(selectedWood);
            wand.setCore(selectedCore);
            wand.setLength(length);
            wand.setFlexibility((String) cbFlexibility.getSelectedItem());
            wand.setStatus("in_storage");

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
}