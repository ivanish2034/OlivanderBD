/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import mephi.b22901.lab4.DatabaseManager;
import mephi.b22901.lab4.Wand;

/**
 *
 * @author ivis2
 */
class WandComboBoxRenderer extends DefaultListCellRenderer {
    private DatabaseManager dbManager;
    
    public WandComboBoxRenderer(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Wand) {
            Wand w = (Wand)value;
            setText(String.format("ID: %d (Древесина: %s, Сердцевина: %s)", 
                w.getId(), 
                dbManager.getAllWoods().get(w.getWoodId()),
                dbManager.getAllCores().get(w.getCoreId())));
        }
        return this;
    }
}

