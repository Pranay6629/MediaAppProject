package com.monu.mediaapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.monu.mediaapp.entity.Media;
import com.monu.mediaapp.repository.MediaRepository;

import java.util.List;

public class MediaViewModel extends AndroidViewModel {
    private MediaRepository repository;
    private LiveData<List<Media>> allMedia;


    public MediaViewModel(@NonNull Application application) {
        super(application);
        repository = new MediaRepository(application);
        allMedia = repository.getAllMedias();

    }

    public void insert(Media media){
        repository.insert(media);
    }

    public void delete(Media media){
        repository.delete(media);
    }

    public LiveData<List<Media>> getAllMedia(){
        return allMedia;
    }
}
