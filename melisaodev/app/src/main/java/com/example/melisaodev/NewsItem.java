package com.example.melisaodev;

public class NewsItem {
    private String category;
    private int imageResId;
    private String description;

    public NewsItem(String category, int imageResId, String description) {
        this.category = category;
        this.imageResId = imageResId;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
} 