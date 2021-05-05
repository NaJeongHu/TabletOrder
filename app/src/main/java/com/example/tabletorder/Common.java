package com.example.tabletorder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public class Common {

    private String Name;
    private String Price;
    private String Img_url;
    private JSONArray Size;
    private JSONArray Add;


    public Common() {
    }

    public Common(String name, String price, String img_url, JSONArray size, JSONArray add) {
        Name = name;
        Price = price;
        Img_url = img_url;
        Size = size;
        Add = add;
    }

    // Getter

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getImg_url() {
        return Img_url;
    }

    public JSONArray getSize() { return Size; }

    public JSONArray getAdd() { return Add; }

    // Setter

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setImg_url(String img_url) {
        Img_url = img_url;
    }

    public void setSize(JSONArray size) { Size = size; }

    public void setAdd(JSONArray add) { Add = add; }
}