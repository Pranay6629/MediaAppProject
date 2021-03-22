package com.monu.mediaapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "media_table")
public class Media implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String image;
    private String url;

    public Media(String title, String image, String url) {
        this.title = title;
        this.image = image;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
