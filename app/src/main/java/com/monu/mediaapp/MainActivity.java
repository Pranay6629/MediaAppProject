package com.monu.mediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.monu.mediaapp.adapter.MediaAdapter;
import com.monu.mediaapp.entity.Media;
import com.monu.mediaapp.viewmodel.MediaViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public MediaViewModel mediaViewModel;
    private RecyclerView recyclerView;
    private LiveData<List<Media>> listLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        // create object of media adapter
        final MediaAdapter adapter = new MediaAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        mediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
        mediaViewModel.getAllMedia().observe(this, new Observer<List<Media>>() {
            @Override
            public void onChanged(List<Media> media) {
                adapter.setMedias(media);
            }
        });
    }
}