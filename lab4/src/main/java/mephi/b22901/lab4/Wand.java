/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

/**
 *
 * @author ivis2
 */

public class Wand {
    private int id;
    private double length;
    private String flexibility;
    private int woodId;
    private int coreId;
    private String status = "in_storage"; // Статусы: in_storage, in_shop, sold

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(String flexibility) {
        this.flexibility = flexibility;
    }

    public int getWoodId() {
        return woodId;
    }

    public void setWoodId(int woodId) {
        this.woodId = woodId;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isInShop() {
        return "in_shop".equals(status);
    }

    public boolean isAvailableForSale() {
        return isInShop();
    }
    
    public boolean isInStorage() {
        return "in_storage".equals(status);
    }
    
    public boolean isSold() {
        return "sold".equals(status);
    }
}