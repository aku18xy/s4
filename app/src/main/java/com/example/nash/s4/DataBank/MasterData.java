package com.example.nash.s4.DataBank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterData {

    String Title;

    @SerializedName("Category")
    List<Category> Categories;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<Category> getCategories() {
        return Categories;
    }

    public void setCategories(List<Category> categories) {
        Categories = categories;
    }
}
