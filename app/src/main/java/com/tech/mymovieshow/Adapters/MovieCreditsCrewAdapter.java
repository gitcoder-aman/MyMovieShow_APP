package com.tech.mymovieshow.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.Model.MovieCreditsCrewModel;
import com.tech.mymovieshow.PersonDetailActivity;
import com.tech.mymovieshow.R;

import java.util.List;

public class MovieCreditsCrewAdapter extends RecyclerView.Adapter<MovieCreditsCrewAdapter.ViewHolder> {

    Activity activity;
    List<MovieCreditsCrewModel>movieCreditsCrewModelList;

    public MovieCreditsCrewAdapter(Activity activity, List<MovieCreditsCrewModel> movieCreditsCrewModelList) {
        this.activity = activity;
        this.movieCreditsCrewModelList = movieCreditsCrewModelList;
    }

    @NonNull
    @Override
    public MovieCreditsCrewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.movie_credits_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieCreditsCrewAdapter.ViewHolder holder, int position) {

        MovieCreditsCrewModel movieCreditsCrewModel = movieCreditsCrewModelList.get(position);

        String profilePath = movieCreditsCrewModel.getProfile_path();

            //Set profile
            Picasso.get()
                    .load(profilePath)
                    .placeholder(R.drawable.image_loding)
                    .into(holder.movieCreditsImageview);


        holder.movieCreditsName.setText(movieCreditsCrewModel.getName());
        holder.movieCreditsCharacter.setText("Department "+movieCreditsCrewModel.getDepartment());

        //Person Detail Activity open
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PersonDetailActivity.class);
                intent.putExtra("id",String.valueOf(movieCreditsCrewModel.getId()));
                activity.startActivity(intent);

                //Create some animation to open the new Activity
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCreditsCrewModelList.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {

        public KenBurnsView movieCreditsImageview;

        private AppCompatTextView movieCreditsName;
        private AppCompatTextView movieCreditsCharacter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCreditsName = itemView.findViewById(R.id.movie_credits_name);
            movieCreditsCharacter = itemView.findViewById(R.id.movie_credits_character);
            movieCreditsImageview = itemView.findViewById(R.id.movie_credits_imageView);
        }
    }
}
