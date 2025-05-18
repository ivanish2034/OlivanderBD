/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Supply;
import mephi.b22901.lab4.Wand;
import mephi.b22901.lab4.app.renderer.*;
/**
 *
 * @author ivis2
 */

public class MakeSupplyDialog extends AbstractDialog {
    private JComboBox<Wand> cbWand;
    private JDateChooser dateChooser;

    public MakeSupplyDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Поставка палочки в магазин", dbManager);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        
        List<Wand> availableWands = dbManager.getWandsInStorage();

        if (availableWands.isEmpty()) {
            showMessage("Нет палочек на складе для поставки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        add(new JLabel("Выберите палочку:"));
        cbWand = new JComboBox<>(availableWands.toArray(new Wand[0]));
        cbWand.setRenderer(new WandComboBoxRenderer());
        add(cbWand);

        add(new JLabel("Дата поставки:"));
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date()); // Устанавливаем текущую дату по умолчанию
        add(dateChooser);

        JButton btnSupply = new JButton("Подтвердить поставку");
        btnSupply.addActionListener(e -> processSupply());
        add(btnSupply);
    }

    private void processSupply() {
        try {
            Wand selectedWand = (Wand) cbWand.getSelectedItem();
            Date supplyDate = dateChooser.getDate();

            if (selectedWand == null) {
                showMessage("Выберите палочку!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (supplyDate == null) {
                showMessage("Укажите дату поставки!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Supply supply = new Supply();
            supply.setWand(selectedWand);
            supply.setSupplyDate(supplyDate);

            if (dbManager.addSupply(supply)) {
                if (dbManager.moveWandToShop(selectedWand.getId())) {
                    showMessage("Палочка успешно поставлена в магазин!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    showMessage("Ошибка при перемещении палочки в магазин", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("Ошибка при регистрации поставки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            showMessage("Произошла ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}