package com.example.todolist;

import java.io.Serializable;

public class RondSpinner implements Serializable {

    private int imageId;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public RondSpinner(String name, int image, int status) {
        this.name = name;
        this.image = image;
        this.status = status;
    }

    public RondSpinner() {
    }

    private String name;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
