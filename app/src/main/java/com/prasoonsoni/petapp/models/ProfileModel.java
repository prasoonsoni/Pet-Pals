package com.prasoonsoni.petapp.models;

public class ProfileModel {
    String animal, born, breed, image, name, weight;

    public ProfileModel() {
    }

    public ProfileModel(String animal, String born, String breed, String image, String name, String weight) {
        this.animal = animal;
        this.born = born;
        this.breed = breed;
        this.image = image;
        this.name = name;
        this.weight = weight;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
