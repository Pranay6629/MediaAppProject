package com.monu.mediaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.monu.mediaapp.entity.Media;

import java.util.List;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM media_table") // craeting query for get data
    LiveData<List<Media>> getAllMedia();

    @Insert
    void insert(Media media); // inserting data

    @Delete
    void delete(Media media);// deleteing data
}
