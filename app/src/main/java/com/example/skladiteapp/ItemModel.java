package com.example.skladiteapp;

public class ItemModel {
    private String type, model, locationAmount;
    private boolean expandable ;

    public ItemModel(String type, String model, String locationAmount) {
        this.type = type;
        this.model = model;
        this.locationAmount = locationAmount;
        this.expandable = false ;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocationAmount() {
        return locationAmount;
    }

    public void setLocationAmount(String locationAmount) {
        this.locationAmount = locationAmount;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", locationAmount='" + locationAmount + '\'' +
                '}';
    }
}
