package com.monu.mediaapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.monu.mediaapp.dao.MediaDao;
import com.monu.mediaapp.database.MediaDatabase;
import com.monu.mediaapp.entity.Media;

import java.util.List;

public class MediaRepository {
    private MediaDao mediaDao;
    private LiveData<List<Media>> allMedias;

    public MediaRepository(Application application){
        MediaDatabase database= MediaDatabase.getInstance(application);
        mediaDao = database.mediaDao();
        allMedias= mediaDao.getAllMedia();


    }

    public void insert(Media media){
        new InsertMedia(mediaDao).execute(media);
    }

    public void delete(Media media){
        new DeleteMedia(mediaDao).execute(media);
    }

    public LiveData<List<Media>> getAllMedias(){
        return allMedias;
    }

    private static class InsertMedia extends AsyncTask<Media, Void, Void>{

        private MediaDao mediaDao;

        public InsertMedia(MediaDao mediaDao) {
            this.mediaDao = mediaDao;
        }

        @Override
        protected Void doInBackground(Media... media) {
            mediaDao.insert(media[0]);
            return null;
        }
    }

    private static class DeleteMedia extends AsyncTask<Media, Void, Void>{

        private MediaDao mediaDao;

        public DeleteMedia(MediaDao mediaDao) {
            this.mediaDao = mediaDao;
        }

        @Override
        protected Void doInBackground(Media... media) {
            mediaDao.delete(media[0]);
            return null;
        }
    }
}
