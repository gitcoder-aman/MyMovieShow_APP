package com.tech.mymovieshow.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.Model.MovieVideosResults;
import com.tech.mymovieshow.R;
import com.tech.mymovieshow.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.ViewHolder> {
    Activity activity;
    List<MovieVideosResults> movieVideosResultsList;

    public MovieVideosAdapter(Activity activity, List<MovieVideosResults> movieVideosResultsList) {
        this.activity = activity;
        this.movieVideosResultsList = movieVideosResultsList;
    }

    @NonNull
    @Override
    public MovieVideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.video_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideosAdapter.ViewHolder holder, int position) {

        MovieVideosResults movieVideosResults = movieVideosResultsList.get(position);

        //default controllers remove
//        YouTubePlayerListener youTubePlayerListener = new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
//
//                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(holder.youTubePlayerView, youTubePlayer);
//                holder.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
//            }
//        };
//
//        if(movieVideosResultsList.size() > position) {
//            // disable iframe ui
//            IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
//            holder.youTubePlayerView.initialize(youTubePlayerListener, options);
//        }
////        video play here
//
//        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
//
//                String key = movieVideosResults.getKey();
//                youTubePlayer.loadVideo(key, 0);
//            }
//        });

        String key = movieVideosResults.getKey();
        Picasso.get()
                .load("https://img.youtube.com/vi/"+key+"/0.jpg")
                .placeholder(R.drawable.image_loding)
                .into(holder.youTubeThumbnail);


        if(movieVideosResults.getName() != null)
        holder.videoTitle.setText(movieVideosResults.getName());

        if(movieVideosResults.getSite() != null)
        holder.videoSite.setText(movieVideosResults.getSite());

        if(movieVideosResults.getType() != null)
        holder.videoType.setText(movieVideosResults.getType());

        if(movieVideosResults.getSize() != null)
        holder.videoQuality.setText(String.valueOf(movieVideosResults.getSize()));

        //on itemView Click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity,VideoPlayActivity.class);
                ArrayList<MovieVideosResults>movieVideosResultsArrayList = new ArrayList<>(movieVideosResultsList);

                //set Animation open the video
//                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.youTubeThumbnail,
//                        ViewCompat.getTransitionName(holder.youTubeThumbnail));

                //put the current video position and video list
   \
                intent.putExtra("position", String.valueOf(holder.getAdapterPosition()));
                intent.putExtra("video", movieVideosResultsArrayList);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVideosResultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView videoTitle;
        private final AppCompatImageView youTubeThumbnail;
        private final AppCompatTextView videoSite;
        private final AppCompatTextView videoQuality;
        private final AppCompatTextView videoType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubeThumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoSite = itemView.findViewById(R.id.video_site);
            videoQuality = itemView.findViewById(R.id.video_quality);
            videoType = itemView.findViewById(R.id.video_type);

        }
    }
}
