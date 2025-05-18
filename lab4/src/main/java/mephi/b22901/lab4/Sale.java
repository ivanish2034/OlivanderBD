/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

/**
 *
 * @author ivis2
 */
import java.util.Date;

public class Sale {
    private int id;
    private Wand wand;      
    private Buyer buyer;    
    private Date saleDate;
    
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
    public Buyer getBuyer() { 
        return buyer; 
    }
    public void setBuyer(Buyer buyer) { 
        this.buyer = buyer; 
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
