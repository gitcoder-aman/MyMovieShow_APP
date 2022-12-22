package com.tech.mymovieshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.Adapters.PersonProfileImageAdapter;
import com.tech.mymovieshow.Client.RetrofitClient;
import com.tech.mymovieshow.Interfaces.RetrofitService;
import com.tech.mymovieshow.Model.PersonDetailModel;
import com.tech.mymovieshow.Model.PersonImages;
import com.tech.mymovieshow.Model.PersonImagesProfile;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailActivity extends AppCompatActivity {

    private KenBurnsView personDetailProfileImageView;

    private LinearLayoutCompat personDetailAlsoKnownAsLayout;
    private LinearLayoutCompat personDetailBirthdayLayout;
    private LinearLayoutCompat personDetailPlaceOfBirthLayout;
    private LinearLayoutCompat personDetailDeathDayLayout;
    private LinearLayoutCompat personDetailDepartmentLayout;
    private LinearLayoutCompat personDetailHomepageLayout;
    private LinearLayoutCompat personDetailBiographyLayout;
    private LinearLayoutCompat personDetailProfileImagesLayout;

    private AppCompatTextView personDetailAlsoKnownAs;
    private AppCompatTextView personDetailBirthday;
    private AppCompatTextView personDetailPlaceOfBirth;
    private AppCompatTextView personDetailDeathDay;
    private AppCompatTextView personDetailDepartment;
    private AppCompatTextView personDetailHomepage;
    private AppCompatTextView personDetailBiography;
    private AppCompatTextView personDetailName;

    private RecyclerView personDetailProfileImageRecyclerView;
    private PersonProfileImageAdapter personProfileImageAdapter;

    private final static String api = "ac28a3498de90c46b11f31bda02b8b97";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);


        personDetailAlsoKnownAsLayout = findViewById(R.id.person_detail_also_known_as_layout);
        personDetailBirthdayLayout = findViewById(R.id.person_detail_birthday_layout);
        personDetailPlaceOfBirthLayout = findViewById(R.id.person_detail_place_of_birth_layout);
        personDetailDeathDayLayout = findViewById(R.id.person_detail_deathday_layout);
        personDetailDepartmentLayout = findViewById(R.id.person_detail_known_for_department_layout);
        personDetailHomepageLayout = findViewById(R.id.person_detail_homepage_layout);
        personDetailBiographyLayout = findViewById(R.id.person_detail_biography_layout);
        personDetailProfileImagesLayout = findViewById(R.id.person_detail_profile_layout);

        personDetailAlsoKnownAs = findViewById(R.id.person_detail_also_known_as);
        personDetailBirthday = findViewById(R.id.person_detail_birthday);
        personDetailPlaceOfBirth = findViewById(R.id.person_detail_place_of_birth);
        personDetailDeathDay = findViewById(R.id.person_detail_deathday);
        personDetailDepartment = findViewById(R.id.person_detail_known_for_department);
        personDetailHomepage = findViewById(R.id.person_detail_homepage);
        personDetailBiography = findViewById(R.id.person_detail_biography);
        personDetailName = findViewById(R.id.person_detail_name);

        personDetailProfileImageView = findViewById(R.id.person_detail_profileImage);
        personDetailProfileImageRecyclerView = findViewById(R.id.person_detail_profileImage_recyclerView);

        personDetailProfileImageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //initiate the retrofit service
        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("id") != null) {

                int id = Integer.parseInt(intent.getExtras().getString("id"));

                //Person Details call Response
                Call<PersonDetailModel> personDetailModelCall = retrofitService.getPersonDetailsById(id,api);

                personDetailModelCall.enqueue(new Callback<PersonDetailModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PersonDetailModel> call, @NonNull Response<PersonDetailModel> response) {

                        PersonDetailModel personDetailModelResponse = response.body();

                        if(personDetailModelResponse != null){
                            preparePersonDetails(personDetailModelResponse);
                        }else{
                            Log.d("debug", "person Detail NULL");
                            Toast.makeText(PersonDetailActivity.this, "Any details not found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PersonDetailModel> call, @NonNull Throwable t) {
                        Log.d("debug", t.getLocalizedMessage());
                        Log.d("debug", "Fail Person Detail Response");
                    }
                });

                //Person Images call Response
                Call<PersonImages>personImagesCall = retrofitService.getPersonImagesById(id,api);

                personImagesCall.enqueue(new Callback<PersonImages>() {
                    @Override
                    public void onResponse(@NonNull Call<PersonImages> call, @NonNull Response<PersonImages> response) {

                        PersonImages personImages = response.body();
                        if(personImages != null){
                            List<PersonImagesProfile>personImagesProfileList = personImages.getProfiles();

                            if(!personImagesProfileList.isEmpty()){

                                personDetailProfileImagesLayout.setVisibility(View.VISIBLE);
                                personProfileImageAdapter = new PersonProfileImageAdapter(PersonDetailActivity.this, personImagesProfileList);
                                personDetailProfileImageRecyclerView.setAdapter(personProfileImageAdapter);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(PersonDetailActivity.this,R.anim.layout_slide_right);
                                personDetailProfileImageRecyclerView.setLayoutAnimation(controller);
                                personDetailProfileImageRecyclerView.scheduleLayoutAnimation();

                            }
                            else{
                                personDetailProfileImagesLayout.setVisibility(View.GONE);

                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PersonImages> call, @NonNull Throwable t) {
                        Log.d("debug", "On Response Fail");
                    }
                });
            }
        }

    }

    private void preparePersonDetails(PersonDetailModel personDetailModelResponse) {

        String profilePath = personDetailModelResponse.getProfile_path();
        String name = personDetailModelResponse.getName();
        String birthDay = personDetailModelResponse.getBirthday();
        String placeOfBirth = personDetailModelResponse.getPlace_of_birth();
        String deathDay = personDetailModelResponse.getDeathday();
        String department = personDetailModelResponse.getKnown_for_department();
        String homePage = personDetailModelResponse.getHomepage();
        String biography = personDetailModelResponse.getBiography();

        List<String>alsoKnownAsList = personDetailModelResponse.getAlso_known_as();

        Picasso.get()
                .load(profilePath)
                .into(personDetailProfileImageView);

        if(name != null && name.length() > 0){
            personDetailName.setText(name);
            personDetailName.setVisibility(View.VISIBLE);
        }else{
            personDetailName.setVisibility(View.GONE);
        }

        if(birthDay != null && birthDay.length() > 0){

            int year = Integer.parseInt(birthDay.substring(0,4));
            int currentYear = Integer.parseInt(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            personDetailBirthday.setText(birthDay + "  ("+(currentYear-year) +" years old" + ")");
            personDetailBirthdayLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailBirthdayLayout.setVisibility(View.GONE);
        }

        if(placeOfBirth != null && placeOfBirth.length() > 0){
            personDetailPlaceOfBirth.setText(placeOfBirth);
            personDetailPlaceOfBirthLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailPlaceOfBirthLayout.setVisibility(View.GONE);
        }

        if(deathDay != null && deathDay.length() > 0){
            personDetailDeathDay.setText(deathDay);
            personDetailDeathDayLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailDeathDayLayout.setVisibility(View.GONE);
        }

        if(department != null && department.length() > 0){
            personDetailDepartment.setText(department);
            personDetailDepartmentLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailDepartmentLayout.setVisibility(View.GONE);
        }

        if(homePage != null && homePage.length() > 0){
            personDetailHomepage.setText(homePage);
            personDetailHomepageLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailHomepageLayout.setVisibility(View.GONE);
        }

        if(biography != null && biography.length() > 0){
            personDetailBiography.setText(biography);
            personDetailBiographyLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailBiographyLayout.setVisibility(View.GONE);
        }

        if(alsoKnownAsList != null && alsoKnownAsList.size() > 0){

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < alsoKnownAsList.size(); i++){

                if(i == alsoKnownAsList.size()-1){
                    stringBuilder.append(alsoKnownAsList.get(i));
                }else{
                    stringBuilder.append(alsoKnownAsList.get(i)).append(", ");
                }
            }
            personDetailAlsoKnownAs.setText(stringBuilder.toString());
            personDetailAlsoKnownAsLayout.setVisibility(View.VISIBLE);
        }else{
            personDetailAlsoKnownAsLayout.setVisibility(View.GONE);
        }
    }

    //set animation to back main Activity

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}