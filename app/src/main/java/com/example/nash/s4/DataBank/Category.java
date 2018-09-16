package com.example.nash.s4.DataBank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    private String Name;

    int Type;

    @SerializedName("Item")
    private List<Item> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        Items = items;
    }
}
