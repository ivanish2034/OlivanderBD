/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import javax.swing.*;
import java.awt.*;
import mephi.b22901.lab4.Buyer;
import mephi.b22901.lab4.DatabaseManager;
/**
 *
 * @author ivis2
 */

public class AddBuyerDialog extends AbstractDialog {
    private JTextField txtFirstName;
    private JTextField txtLastName;

    public AddBuyerDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Добавить покупателя", dbManager);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(300, 150);

        add(new JLabel("Имя:"));
        txtFirstName = new JTextField();
        add(txtFirstName);

        add(new JLabel("Фамилия:"));
        txtLastName = new JTextField();
        add(txtLastName);

        JButton btnAdd = new JButton("Добавить");
        btnAdd.addActionListener(e -> addBuyer());
        add(btnAdd);
    }

    private void addBuyer() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showMessage("Заполните все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Buyer buyer = new Buyer();
        buyer.setFirstName(firstName);
        buyer.setLastName(lastName);

        if (dbManager.addBuyer(buyer)) {
            showMessage("Покупатель добавлен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showMessage("Ошибка при добавлении!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}