package com.monu.mediaapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.monu.mediaapp.R;
import com.monu.mediaapp.dao.MediaDao;
import com.monu.mediaapp.entity.Media;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Database(entities = {Media.class}, version = 1)
public abstract class MediaDatabase extends RoomDatabase {

    private static MediaDatabase mInstance;

    private static Context activity;

    public abstract MediaDao mediaDao();

    public static synchronized MediaDatabase getInstance(Context context){

        activity = context.getApplicationContext();

        if (mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MediaDatabase.class, "media_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return mInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(mInstance).execute();
        }
    };

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
            private MediaDao mediaDao;

            private PopulateDbAsyncTask(MediaDatabase mediaDatabase){
                mediaDao = mediaDatabase.mediaDao();
//                new PopulateDbAsyncTask(mInstance).execute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
//                mediaDao.insert(new Media("Monu Chamorshikar", "https://img.republicworld.com/republic-prod/shows/promolarge/xxhdpi/tr:w-425,h-233/150375812059a18728c0d9b.jpeg", "http://uds.ak.o.brightcove.com/5384493731001/5384493731001_5552873278001_5552856451001.mp4?pubId=5384493731001&videoId=5552856451001"));
//                mediaDao.insert(new Media("Pranay Chamorshikar", "https://img.republicworld.com/republic-prod/shows/promolarge/xxhdpi/tr:w-425,h-233/150375812059a18728c0d9b.jpeg", "http://uds.ak.o.brightcove.com/5384493731001/5384493731001_5552873278001_5552856451001.mp4?pubId=5384493731001&videoId=5552856451001"));
                fillWithStartingData(activity);
                return null;
            }
        }

        private static void fillWithStartingData(Context context){
            MediaDao dao = getInstance(context).mediaDao();
            JSONArray mediaItem = loadJSONArray(context);

            try {
                for (int i=0; i < mediaItem.length(); i++){
                    JSONObject medias = mediaItem.getJSONObject(i);

                    String title = medias.getString("Title");
                    String image = medias.getString("Image");
                    String url = medias.getString("Url");

                    dao.insert(new Media(title, image, url));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        private static JSONArray loadJSONArray(Context context){
            StringBuilder builder = new StringBuilder();
            InputStream in = context.getResources().openRawResource(R.raw.media_item);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            try {
                while ((line = reader.readLine()) != null){
                    builder.append(line);
                }

                JSONObject json = new JSONObject(builder.toString());
                return json.getJSONArray("MediaItems");
            }catch (IOException | JSONException exception){
                exception.printStackTrace();

            }
            return null;
        }

}
