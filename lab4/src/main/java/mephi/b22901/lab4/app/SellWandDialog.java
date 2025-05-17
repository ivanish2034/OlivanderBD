/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

//import com.toedter.calendar.JDateChooser;

import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import mephi.b22901.lab4.Buyer;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;

/**
 *
 * @author ivis2
 */

//public class SellWandDialog extends JDialog {
//    private JComboBox<Wand> cbWand;
//    private JComboBox<Buyer> cbBuyer;
//    private JDateChooser dateChooser;
//    private DatabaseManager dbManager;
//
//    public SellWandDialog(JFrame parent) {
//        super(parent, "Продать палочку", true);
//        this.dbManager = new DatabaseManager();
//        initializeUI();
//    }
//
//    private void initializeUI() {
//        setSize(400, 300);
//        setLayout(new GridLayout(4, 2, 10, 10));
//        
//        List<Wand> availableWands = dbManager.getWandsInShop(); 
//        List<Buyer> buyers = dbManager.getAllBuyers();
//
//        if (availableWands.isEmpty() || buyers.isEmpty()) {
//            String message = availableWands.isEmpty() 
//                ? "Нет палочек в магазине, готовых к продаже" 
//                : "Нет зарегистрированных покупателей";
//            JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
//            dispose();
//            return;
//        }
//
//        add(new JLabel("Выберите палочку:"));
//        cbWand = new JComboBox<>(availableWands.toArray(new Wand[0]));
//        cbWand.setRenderer(new WandComboBoxRenderer(dbManager));
//        add(cbWand);
//
//        add(new JLabel("Выберите покупателя:"));
//        cbBuyer = new JComboBox<>(buyers.toArray(new Buyer[0]));
//        cbBuyer.setRenderer(new BuyerComboBoxRenderer());
//        add(cbBuyer);
//
//        add(new JLabel("Дата продажи:"));
//        dateChooser = new JDateChooser();
//        dateChooser.setDate(new Date()); 
//        add(dateChooser);
//
//        JButton btnSell = new JButton("Продать");
//        btnSell.addActionListener(e -> sellWand());
//        add(btnSell);
//    }
//
//    private void sellWand() {
//        try {
//            Wand selectedWand = (Wand) cbWand.getSelectedItem();
//            Buyer selectedBuyer = (Buyer) cbBuyer.getSelectedItem();
//            Date saleDate = dateChooser.getDate();
//
//            if (selectedWand == null || selectedBuyer == null) {
//                JOptionPane.showMessageDialog(this, "Выберите палочку и покупателя!");
//                return;
//            }
//
//            if (saleDate == null) {
//                saleDate = new Date();
//            }
//
//            if (dbManager.sellWand(selectedWand.getId(), selectedBuyer.getId(), saleDate)) {
//                JOptionPane.showMessageDialog(this, "Палочка успешно продана!");
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, 
//                    "Ошибка при продаже палочки. Возможно, она уже была продана.",
//                    "Ошибка", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, 
//                "Произошла ошибка: " + e.getMessage(),
//                "Ошибка", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
//
public class SellWandDialog extends AbstractDialog {
    private JComboBox<Wand> cbWand;
    private JComboBox<Buyer> cbBuyer;
    private JDateChooser dateChooser;

    public SellWandDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Продать палочку", dbManager);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));
        
        List<Wand> availableWands = dbManager.getWandsInShop(); 
        List<Buyer> buyers = dbManager.getAllBuyers();

        if (availableWands.isEmpty() || buyers.isEmpty()) {
            String message = availableWands.isEmpty() 
                ? "Нет палочек в магазине, готовых к продаже" 
                : "Нет зарегистрированных покупателей";
            showMessage(message, "Ошибка", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        add(new JLabel("Выберите палочку:"));
        cbWand = new JComboBox<>(availableWands.toArray(new Wand[0]));
        cbWand.setRenderer(new WandComboBoxRenderer(dbManager));
        add(cbWand);

        add(new JLabel("Выберите покупателя:"));
        cbBuyer = new JComboBox<>(buyers.toArray(new Buyer[0]));
        cbBuyer.setRenderer(new BuyerComboBoxRenderer());
        add(cbBuyer);

        add(new JLabel("Дата продажи:"));
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date()); 
        add(dateChooser);

        JButton btnSell = new JButton("Продать");
        btnSell.addActionListener(e -> sellWand());
        add(btnSell);
    }

    private void sellWand() {
        try {
            Wand selectedWand = (Wand) cbWand.getSelectedItem();
            Buyer selectedBuyer = (Buyer) cbBuyer.getSelectedItem();
            Date saleDate = dateChooser.getDate();

            if (selectedWand == null || selectedBuyer == null) {
                showMessage("Выберите палочку и покупателя!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (saleDate == null) {
                saleDate = new Date();
            }

            if (dbManager.sellWand(selectedWand.getId(), selectedBuyer.getId(), saleDate)) {
                showMessage("Палочка успешно продана!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showMessage("Ошибка при продаже палочки. Возможно, она уже была продана.",
                          "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            showMessage("Произошла ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}