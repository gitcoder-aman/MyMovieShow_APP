package com.tech.mymovieshow.Adapters;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.ImageViewerActivity;
import com.tech.mymovieshow.Model.MovieImagesBackDropAndPosters;
import com.tech.mymovieshow.R;

import java.util.List;
import java.util.Objects;

public class MoviePosterImagesAdapter extends RecyclerView.Adapter<MoviePosterImagesAdapter.ViewHolder> {

    Activity activity;
    List<MovieImagesBackDropAndPosters>movieImagesBackDropAndPostersList;

    public MoviePosterImagesAdapter(Activity activity, List<MovieImagesBackDropAndPosters> movieImagesBackDropAndPostersList) {
        this.activity = activity;
        this.movieImagesBackDropAndPostersList = movieImagesBackDropAndPostersList;
    }

    @NonNull
    @Override
    public MoviePosterImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.profile_image_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterImagesAdapter.ViewHolder holder, int position) {

        final MovieImagesBackDropAndPosters movieImagesBackDropAndPostersModel = movieImagesBackDropAndPostersList.get(position);

        String posterPath = movieImagesBackDropAndPostersModel.getFile_path();
        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.image_loding)
                .into(holder.profileImagesItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageViewerIntent = new Intent(activity, ImageViewerActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.profileImagesItem, Objects.requireNonNull(ViewCompat.getTransitionName(holder.profileImagesItem)));
                imageViewerIntent.putExtra("image_url",posterPath);
                activity.startActivity(imageViewerIntent,compat.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieImagesBackDropAndPostersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView profileImagesItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImagesItem = itemView.findViewById(R.id.profile_images_item);
        }
    }
}
