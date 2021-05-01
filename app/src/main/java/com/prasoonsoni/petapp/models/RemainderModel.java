package com.prasoonsoni.petapp.models;

public class RemainderModel {
    String animal, description, time, title;
    public RemainderModel() {
    }

    public RemainderModel(String animal, String description, String time, String title) {
        this.animal = animal;
        this.description = description;
        this.time = time;
        this.title = title;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
