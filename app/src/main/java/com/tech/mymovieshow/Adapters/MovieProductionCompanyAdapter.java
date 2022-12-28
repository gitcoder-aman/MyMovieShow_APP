package com.tech.mymovieshow.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.Model.MovieDetailProductCompany;
import com.tech.mymovieshow.R;

import java.util.List;

public class MovieProductionCompanyAdapter extends RecyclerView.Adapter<MovieProductionCompanyAdapter.ViewHolder> {

    Activity activity;
    List<MovieDetailProductCompany>movieDetailProductCompanyList;

    public MovieProductionCompanyAdapter(Activity activity, List<MovieDetailProductCompany> movieDetailProductCompanyList) {
        this.activity = activity;
        this.movieDetailProductCompanyList = movieDetailProductCompanyList;
    }

    @NonNull
    @Override
    public MovieProductionCompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.production_company_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieProductionCompanyAdapter.ViewHolder holder, int position) {

        final MovieDetailProductCompany movieDetailProductCompanyModel = movieDetailProductCompanyList.get(position);

//        String companyName = movieDetailProductCompanyModel.getName();
        String profilePath = movieDetailProductCompanyModel.getLogo_path();
        Picasso.get()
                .load(profilePath)
                .placeholder(R.drawable.image_loding)
                .into(holder.productionCompanyImageView);

        holder.productionCompanyName.setText(movieDetailProductCompanyModel.getName());


    }

    @Override
    public int getItemCount() {
        return movieDetailProductCompanyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private KenBurnsView productionCompanyImageView;
        private AppCompatTextView productionCompanyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productionCompanyImageView = itemView.findViewById(R.id.production_company_imageView);
            productionCompanyName = itemView.findViewById(R.id.production_company_name);
        }
    }
}
