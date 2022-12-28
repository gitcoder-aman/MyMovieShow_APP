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
import com.tech.mymovieshow.Model.PersonImagesProfile;
import com.tech.mymovieshow.R;

import java.util.List;
import java.util.Objects;

public class PersonProfileImageAdapter extends RecyclerView.Adapter<PersonProfileImageAdapter.ViewHolder> {

    Activity activity;
    List<PersonImagesProfile>profileImagesList;

    public PersonProfileImageAdapter(Activity activity, List<PersonImagesProfile> profileImagesList) {
        this.activity = activity;
        this.profileImagesList = profileImagesList;
    }

    @NonNull
    @Override
    public PersonProfileImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.profile_image_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonProfileImageAdapter.ViewHolder holder, int position) {

       final PersonImagesProfile personImagesProfile = profileImagesList.get(position);

        String profile_path = personImagesProfile.getFile_path();

            Picasso.get()
                    .load(profile_path)
                    .placeholder(R.drawable.image_loding)
                    .into(holder.profileImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent imageViewerIntent = new Intent(activity, ImageViewerActivity.class);
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.profileImage,
                                Objects.requireNonNull(ViewCompat.getTransitionName(holder.profileImage)));

                        imageViewerIntent.putExtra("image_url",personImagesProfile.getFile_path());
                        activity.startActivity(imageViewerIntent,compat.toBundle());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return profileImagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

       private AppCompatImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_images_item);

        }
    }
}
