package com.tech.mymovieshow.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.mymovieshow.Model.MovieResponseResults;
import com.tech.mymovieshow.MovieDetailActivity;
import com.tech.mymovieshow.PersonDetailActivity;
import com.tech.mymovieshow.R;
import com.tech.mymovieshow.ViewHolders.SearchViewHolder;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Activity activity;
    private List<MovieResponseResults> results;

    public MovieSearchAdapter(Activity activity, List<MovieResponseResults> results) {
        this.activity = activity;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.search_layout_items,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        MovieResponseResults responseResults = results.get(position);

        holder.setPosterImageView(activity,responseResults.getPoster_path());

        String title = responseResults.getTitle();
        int id = responseResults.getId();

        if(title != null){
            holder.posterTitle.setVisibility(View.VISIBLE);
            holder.posterTitle.setText(title);
        }else{
            holder.posterTitle.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(id));
                activity.startActivity(intent);

                //Create some animation to open the new Activity
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
