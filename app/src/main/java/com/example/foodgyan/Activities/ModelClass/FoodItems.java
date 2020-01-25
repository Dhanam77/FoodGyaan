package com.example.foodgyan.Activities.ModelClass;

public class FoodItems {

    private String foodImage, foodName;

    public FoodItems() {
    }

    public FoodItems(String foodImage, String foodName) {
        this.foodImage = foodImage;
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
