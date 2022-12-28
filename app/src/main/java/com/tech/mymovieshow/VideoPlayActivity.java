package com.tech.mymovieshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.tech.mymovieshow.Model.MovieVideosResults;
import com.tech.mymovieshow.Utils.FullScreenHelper;

import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity {

    private RecyclerView otherVideoRecyclerView;
    private FullScreenHelper fullScreenHelper;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();

        fullScreenHelper  = new FullScreenHelper(this);

        youTubePlayerView = findViewById(R.id.youtube_player_view);

        getLifecycle().addObserver(youTubePlayerView);

        AppCompatTextView videoTitle = findViewById(R.id.play_video_title);
        AppCompatTextView noResultFound = findViewById(R.id.no_result_found);

        otherVideoRecyclerView = findViewById(R.id.other_videos_recyclerView);

        otherVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //Now get the arrayList and position
        if(intent != null && intent.getExtras() != null){
            ArrayList<MovieVideosResults>movieVideosResultsArrayList = intent.getExtras().getParcelableArrayList("video");

            int position = Integer.parseInt(intent.getExtras().getString("position"));

            if(movieVideosResultsArrayList != null && movieVideosResultsArrayList.size() > 0){

                String key = movieVideosResultsArrayList.get(position).getKey();
                String title = movieVideosResultsArrayList.get(position).getName();

                if(key != null){

                    YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                            super.onReady(youTubePlayer);
                            DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                            youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                        }
                    };

                    // disable web ui
                    IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
                    youTubePlayerView.initialize(listener, options);

                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
                            youTubePlayer.loadVideo(key, 0);
                        }
                    });
                }
                if(title != null){

                    videoTitle.setText(title);
                }
            }
        }

    }
}