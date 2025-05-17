/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4.app;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import mephi.b22901.lab4.DatabaseManager;

/**
 *
 * @author ivis2
 */
public abstract class AbstractDialog extends JDialog implements CommonDialog {
    protected final DatabaseManager dbManager;
    
    public AbstractDialog(JFrame parent, String title, DatabaseManager dbManager) {
        super(parent, title, true);
        this.dbManager = dbManager;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void showDialog() {
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
    
    protected void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}