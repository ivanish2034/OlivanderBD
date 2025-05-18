/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

import java.util.Date;

/**
 *
 * @author ivis2
 */

public class Supply {
    private int id;
    private Wand wand; 
    private Date supplyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Wand getWand() { 
        return wand; 
    }
    public void setWand(Wand wand) { 
        this.wand = wand; 
    }
    
    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }
}