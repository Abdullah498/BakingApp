package com.example.bakingapp.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String ingredients;
    private ArrayList<String> shortDescription;
    private ArrayList<String>  description;
    private ArrayList<String> videoURL;
    private ArrayList<String>  thumbnailURL;

    public Recipe() {
    }

    public Recipe(String name, String ingredients, ArrayList<String> shortDescription, ArrayList<String> description, ArrayList<String> videoURL, ArrayList<String> thumbnailURL) {
        this.name = name;
        this.ingredients = ingredients;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getShortDescription() {
        return shortDescription;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public ArrayList<String> getVideoURL() {
        return videoURL;
    }

    public ArrayList<String> getThumbnailURL() {
        return thumbnailURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setShortDescription(ArrayList<String> shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public void setVideoURL(ArrayList<String> videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(ArrayList<String> thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
