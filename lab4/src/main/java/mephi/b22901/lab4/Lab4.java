/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mephi.b22901.lab4;

import javax.swing.SwingUtilities;
import mephi.b22901.lab4.app.OllivandersGUI;
/**
 *
 * @author ivis2
 */
public class Lab4 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    OllivandersGUI window = new OllivandersGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
