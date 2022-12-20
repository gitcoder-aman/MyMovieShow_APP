package com.tech.mymovieshow.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.santalu.diagonalimageview.DiagonalImageView;
import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private KenBurnsView posterImageView;
    public AppCompatTextView posterTitle;


    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.posterImageView);
        posterTitle = itemView.findViewById(R.id.posterTitle);

        RandomTransitionGenerator generator = new RandomTransitionGenerator(1000, new DecelerateInterpolator());
        posterImageView.setTransitionGenerator(generator);
    }

    public void setPosterImageView(Context context, String posterUrl) {

        Picasso.get()
                .load(posterUrl)
                .into(posterImageView);
    }
}
