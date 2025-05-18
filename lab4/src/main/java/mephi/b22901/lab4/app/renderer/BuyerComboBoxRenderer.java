/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import mephi.b22901.lab4.Buyer;

/**
 *
 * @author ivis2
 */
public class BuyerComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Buyer) {
            Buyer b = (Buyer)value;
            setText(String.format("%s %s (ID: %d)", 
                    b.getFirstName(), 
                    b.getLastName(), 
                    b.getId()));
        }
        return this;
    }
}