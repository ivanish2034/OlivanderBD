/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import mephi.b22901.lab4.Wand;

/**
 *
 * @author ivis2
 */
public class WandComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Wand) {
            Wand wand = (Wand) value;
            StringBuilder sb = new StringBuilder();
            
            sb.append("Палочка #").append(wand.getId());
            if (wand.getWood() != null) {
                sb.append(" | Древесина: ").append(wand.getWood().getName());
            }
            if (wand.getCore() != null) {
                sb.append(" | Сердцевина: ").append(wand.getCore().getName());
            }
            sb.append(" | Длина: ").append(String.format("%.1f", wand.getLength()));
            sb.append("\" | ").append(wand.getFlexibility());
            
            setText(sb.toString());
        }
        return this;
    }
}