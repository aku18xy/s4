package com.example.nash.s4.DataBank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewData {

    private String CategoryName;

    private int CategoryIndex;

    @SerializedName("Item")
    private
    List<Item> Items;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryIndex() {
        return CategoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        CategoryIndex = categoryIndex;
    }

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        Items = items;
    }
}
