package com.example.tabletorder;

import org.json.JSONArray;

import java.util.List;

public class OrderInfo {

    private String Name;
    private String Price;
    private String Size;
    private JSONArray Addi;

    public OrderInfo() {
    }

    public OrderInfo(String name, String price, String size, JSONArray addi){
        Name = name;
        Price = price;
        Size = size;
        Addi = addi;
    }

    // getter
    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getSize() {
        return Size;
    }

    public JSONArray getAddi() {
        return Addi;
    }


    // setter

    public void setName(String name) { Name = name; }

    public void setPrice(String price) {
        Price = price;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setAddi(JSONArray addi) {
        Addi = addi;
    }
}
