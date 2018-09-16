package com.example.nash.s4.DataBank;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    private String Label;

    @Nullable
    @SerializedName("Value")
    private List<String> Value;

    private int ValueType;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public List<String> getValue() {
        return Value;
    }

    public void setValue(List<String> value1) {
        Value = value1;
    }


    public int getValueType() {
        return ValueType;
    }

    public void setValueType(int valueType) {
        ValueType = valueType;
    }
}
