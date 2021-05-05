package com.example.tabletorder;

public class CellectStore {

    private String Address;
    private String Name;
    private String Image;

    public CellectStore() {
    }

    public CellectStore(String address, String name, String image) {
        Address = address;
        Name = name;
        Image = image;
    }

    // getter
    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    // setter
    public void setAddress(String address) {
        Address = address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }
}
