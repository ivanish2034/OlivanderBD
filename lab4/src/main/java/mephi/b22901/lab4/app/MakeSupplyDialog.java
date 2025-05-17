/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;
/**
 *
 * @author ivis2
 */

public class MakeSupplyDialog extends AbstractDialog {
    private JComboBox<Wand> cbWand;

    public MakeSupplyDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Перемещение палочки в магазин", dbManager);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(400, 200);
        setLocationRelativeTo(getParent());

        List<Wand> wands = dbManager.getWandsInStorage();

        add(new JLabel("Выберите палочку:"));
        cbWand = new JComboBox<>(new Vector<>(wands));
        cbWand.setRenderer(new WandComboBoxRenderer(dbManager));
        add(cbWand);

        JButton btnMove = new JButton("Переместить в магазин");
        btnMove.addActionListener(e -> moveToShop());
        add(btnMove);

        add(new JLabel());
        add(new JLabel());
    }

    private void moveToShop() {
        Wand selectedWand = (Wand) cbWand.getSelectedItem();
        if (selectedWand == null) {
            showMessage("Выберите палочку!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dbManager.moveWandToShop(selectedWand.getId())) {
            showMessage("Палочка успешно перемещена в магазин!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showMessage("Ошибка при перемещении палочки! Возможно, она уже была перемещена.",
                      "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}