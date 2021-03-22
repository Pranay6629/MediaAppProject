package com.monu.mediaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.monu.mediaapp.R;
import com.monu.mediaapp.VideoActivity;
import com.monu.mediaapp.entity.Media;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaHolder> {
    private Context context;
    private List<Media> medias = new ArrayList<>();

    public MediaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
       MediaHolder holder = new MediaHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MediaHolder holder, int position) {
        final Media media = medias.get(position);
        holder.mediaTitle.setText(media.getTitle());

        String imageUrl = media.getImage();
        Picasso.with(holder.mediaImage.getContext()).load(imageUrl).into(holder.mediaImage);

        holder.mediaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("videoURL", media.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(media.getUrl());
//        String mVideoDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        long mTimeInMilliseconds = Long.parseLong(mVideoDuration);
//        long duration = mTimeInMilliseconds / 1000;
//        long hours = duration / 3600;
//        long minutes = (duration - hours * 3600) / 60;
//        long seconds = duration - (hours * 3600 + minutes * 60);
//        System.out.println("duration :" + duration);
//        System.out.println("hours :" + hours);
//        System.out.println("minutes : "+ minutes);
//        System.out.println("Second : " +seconds);
//
//        holder.mediaTitle.setText(media.getTitle() + hours + ":" + minutes +":" + seconds);


    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public void setMedias(List<Media> medias){
        this.medias = medias;
        notifyDataSetChanged();
    }

    public class MediaHolder extends RecyclerView.ViewHolder {

        private TextView mediaTitle;
        private ImageView mediaImage;
        public MediaHolder(@NonNull View itemView) {
            super(itemView);

            mediaTitle = itemView.findViewById(R.id.mediaTitle);
            mediaImage = itemView.findViewById(R.id.mediaImage);

        }
    }
}
