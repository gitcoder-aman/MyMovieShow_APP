package com.tech.mymovieshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.tech.mymovieshow.Adapters.MovieCreditsCastAdapter;
import com.tech.mymovieshow.Adapters.MovieCreditsCrewAdapter;
import com.tech.mymovieshow.Adapters.MoviePosterImagesAdapter;
import com.tech.mymovieshow.Adapters.MovieProductionCompanyAdapter;
import com.tech.mymovieshow.Adapters.MovieVideosAdapter;
import com.tech.mymovieshow.Client.RetrofitClient;
import com.tech.mymovieshow.Interfaces.RetrofitService;
import com.tech.mymovieshow.Model.MovieCreditsCastModel;
import com.tech.mymovieshow.Model.MovieCreditsCrewModel;
import com.tech.mymovieshow.Model.MovieCreditsModel;
import com.tech.mymovieshow.Model.MovieDetailModel;
import com.tech.mymovieshow.Model.MovieDetailProductCompany;
import com.tech.mymovieshow.Model.MovieDetailProductCountry;
import com.tech.mymovieshow.Model.MovieDetailsGenres;
import com.tech.mymovieshow.Model.MovieImagesBackDropAndPosters;
import com.tech.mymovieshow.Model.MovieImagesModel;
import com.tech.mymovieshow.Model.MovieVideosModel;
import com.tech.mymovieshow.Model.MovieVideosResults;

import java.util.ArrayList;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private LinearLayoutCompat movieDetailTitleLayout;
    private LinearLayoutCompat movieDetailLanguageLayout;
    private LinearLayoutCompat movieDetailAdultLayout;
    private LinearLayoutCompat movieDetailStatusLayout;
    private LinearLayoutCompat movieDetailRuntimeLayout;
    private LinearLayoutCompat movieDetailBudgetLayout;
    private LinearLayoutCompat movieDetailRevenueLayout;
    private LinearLayoutCompat movieDetailGenresLayout;
    private LinearLayoutCompat movieDetailProductionCountryLayout;
    private LinearLayoutCompat movieDetailReleaseLayout;
    private LinearLayoutCompat movieDetailHomepageLayout;
    private LinearLayoutCompat movieDetailOverviewLayout;
    private LinearLayoutCompat movieDetailCastLayout;
    private LinearLayoutCompat movieDetailCrewLayout;
    private LinearLayoutCompat movieDetailProductionCompanyLayout;
    private LinearLayoutCompat movieDetailImagesLayout;
    private LinearLayoutCompat movieDetailVideoLayout;

    private AppCompatTextView movieDetailTitle1TextView;

    private AppCompatTextView movieDetailTitle2TextView;
    private AppCompatTextView movieDetailLanguageTextView;
    private AppCompatTextView movieDetailAdultTextView;
    private AppCompatTextView movieDetailStatusTextView;
    private AppCompatTextView movieDetailRuntimeTextView;
    private AppCompatTextView movieDetailBudgetTextView;
    private AppCompatTextView movieDetailRevenueTextView;
    private AppCompatTextView movieDetailGenresTextView;
    private AppCompatTextView movieDetailProductionCountryTextView;
    private AppCompatTextView movieDetailReleaseDateTextView;
    private AppCompatTextView movieDetailHomepageTextView;
    private AppCompatTextView movieDetailOverviewTextView;

    private CircularProgressIndicator movieDetailRatingBar;
    private CircleImageView movieDetailPosterCircleImageView;
    private KenBurnsView movieDetailPosterImage;

    private RecyclerView movieDetailCastRecyclerView;
    private RecyclerView movieDetailCrewRecyclerView;
    private RecyclerView movieDetailProductionCompanyRecyclerView;
    private RecyclerView movieDetailImageRecyclerView;
    private RecyclerView movieDetailVideoRecyclerView;

    private final static String api = "ac28a3498de90c46b11f31bda02b8b97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Layout findView
        movieDetailTitleLayout = findViewById(R.id.movie_detail_titleLayout);
        movieDetailLanguageLayout = findViewById(R.id.movie_detail_languageLayout);
        movieDetailAdultLayout = findViewById(R.id.movie_detail_adultLayout);
        movieDetailStatusLayout = findViewById(R.id.movie_detail_statusLayout);
        movieDetailRuntimeLayout = findViewById(R.id.movie_detail_runtimeLayout);
        movieDetailBudgetLayout = findViewById(R.id.movie_detail_budgetLayout);
        movieDetailRevenueLayout = findViewById(R.id.movie_detail_revenueLayout);
        movieDetailGenresLayout = findViewById(R.id.movie_detail_genresLayout);
        movieDetailProductionCountryLayout = findViewById(R.id.movie_detail_productionCountryLayout);
        movieDetailReleaseLayout = findViewById(R.id.movie_detail_releaseLayout);
        movieDetailHomepageLayout = findViewById(R.id.movie_detail_homePageLayout);
        movieDetailOverviewLayout = findViewById(R.id.movie_detail_overviewLayout);
        movieDetailCastLayout = findViewById(R.id.movie_detail_cast_layout);
        movieDetailCrewLayout = findViewById(R.id.movie_detail_crew_layout);
        movieDetailProductionCompanyLayout = findViewById(R.id.movie_detail_productionCompanyLayout);
        movieDetailImagesLayout = findViewById(R.id.movie_detail_ImagesLayout);
        movieDetailVideoLayout = findViewById(R.id.movie_detail_VideoLayout);

        //TextView findView
        movieDetailTitle1TextView = findViewById(R.id.movie_detail_title1);

        movieDetailTitle2TextView = findViewById(R.id.movie_detail_title2);
        movieDetailLanguageTextView = findViewById(R.id.movie_detail_language);
        movieDetailAdultTextView = findViewById(R.id.movie_detail_adult);
        movieDetailStatusTextView = findViewById(R.id.movie_detail_status);
        movieDetailRuntimeTextView = findViewById(R.id.movie_detail_runtime);
        movieDetailBudgetTextView = findViewById(R.id.movie_detail_budget);
        movieDetailRevenueTextView = findViewById(R.id.movie_detail_revenue);
        movieDetailGenresTextView = findViewById(R.id.movie_detail_genres);
        movieDetailProductionCountryTextView = findViewById(R.id.movie_detail_productionCountry);
        movieDetailReleaseDateTextView = findViewById(R.id.movie_detail_release);
        movieDetailHomepageTextView = findViewById(R.id.movie_detail_homePage);
        movieDetailOverviewTextView = findViewById(R.id.movie_detail_overview);

        movieDetailCastRecyclerView = findViewById(R.id.movie_detail_cast_recyclerView);
        movieDetailCastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailCrewRecyclerView = findViewById(R.id.movie_detail_crew_recyclerView);
        movieDetailCrewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailProductionCompanyRecyclerView = findViewById(R.id.movie_detail_productionCompany_recyclerView);
        movieDetailProductionCompanyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailImageRecyclerView = findViewById(R.id.movie_detail_Image_recyclerView);
        movieDetailImageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        movieDetailVideoRecyclerView = findViewById(R.id.movie_detail_Video_recyclerView);
        movieDetailVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        movieDetailRatingBar = findViewById(R.id.movie_detail_ratingBar);
        movieDetailPosterCircleImageView = findViewById(R.id.movie_detail_posterCircle_imageView);
        movieDetailPosterImage = findViewById(R.id.movie_detail_posterImage);


        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class); //create Retrofit service

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("id") != null) {

                int movie_id = Integer.parseInt(intent.getExtras().getString("id"));

                //Movie Details Call
                Call<MovieDetailModel> movieDetailModelCall = retrofitService.getMovieDetailsById(movie_id, api);

                movieDetailModelCall.enqueue(new Callback<MovieDetailModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {

                        MovieDetailModel movieDetailModelResponse = response.body();

                        if (movieDetailModelResponse != null) {
                            prepareMovieDetails(movieDetailModelResponse);
                        } else {
                            Log.d("debug", "MOVIE RESPONSE NULL");
                            Toast.makeText(MovieDetailActivity.this, "Any details not found!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                        Log.d("debug", "MOVIE RESPONSE FAIL");
                    }
                });

                //Credit , Crew/cast details call
                Call<MovieCreditsModel> movieCreditsModelCall = retrofitService.getMovieCreditsById(movie_id, api);

                movieCreditsModelCall.enqueue(new Callback<MovieCreditsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                        MovieCreditsModel movieCreditsModelResponse = response.body();
                        if (movieCreditsModelResponse != null) {

                            //Set adapter cast

                            List<MovieCreditsCastModel> movieCreditsCastModelList = movieCreditsModelResponse.getCast();

                            if (movieCreditsCastModelList != null && movieCreditsCastModelList.size() > 0) {
                                MovieCreditsCastAdapter movieCreditsCastAdapter = new MovieCreditsCastAdapter(MovieDetailActivity.this, movieCreditsCastModelList);
                                movieDetailCastRecyclerView.setAdapter(movieCreditsCastAdapter);

                                movieDetailCastLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailCastRecyclerView.setLayoutAnimation(controller);
                                movieDetailCastRecyclerView.scheduleLayoutAnimation();

                            } else {
                                movieDetailCastLayout.setVisibility(View.GONE);
                                Toast.makeText(MovieDetailActivity.this, "Any Cast details not found ", Toast.LENGTH_SHORT).show();
                            }

                            //Set adapter crew
                            List<MovieCreditsCrewModel> movieCreditsCrewModelList = movieCreditsModelResponse.getCrew();

                            if (movieCreditsCrewModelList != null && movieCreditsCrewModelList.size() > 0) {
                                MovieCreditsCrewAdapter movieCreditsCrewAdapter = new MovieCreditsCrewAdapter(MovieDetailActivity.this, movieCreditsCrewModelList);
                                movieDetailCrewRecyclerView.setAdapter(movieCreditsCrewAdapter);

                                movieDetailCrewLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailCrewRecyclerView.setLayoutAnimation(controller);
                                movieDetailCrewRecyclerView.scheduleLayoutAnimation();

                            } else {
                                movieDetailCrewLayout.setVisibility(View.GONE);
                                Toast.makeText(MovieDetailActivity.this, "Any Crew details not found ", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            movieDetailCastLayout.setVisibility(View.GONE);
                            movieDetailCrewLayout.setVisibility(View.GONE);
                            Log.d("debug", "MOVIE RESPONSE NULL");
                            Toast.makeText(MovieDetailActivity.this, "Any Credit details not found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                        Log.d("debug", "Movie Credits Response Fail");
                    }
                });

                //Movie Images Call from API
                Call<MovieImagesModel>movieImagesModelCall = retrofitService.getMovieImagesById(movie_id, api);

                movieImagesModelCall.enqueue(new Callback<MovieImagesModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieImagesModel> call, @NonNull Response<MovieImagesModel> response) {

                        MovieImagesModel movieImagesModelCallResponse = response.body();

                        if(movieImagesModelCallResponse != null){

                            ArrayList<MovieImagesBackDropAndPosters> movieImagesBackDropAndPostersArrayList = new ArrayList<>();
                            List<MovieImagesBackDropAndPosters>movieImagesBackDropList = movieImagesModelCallResponse.getBackdrops();
                            List<MovieImagesBackDropAndPosters>movieImagesPostersList = movieImagesModelCallResponse.getPosters();

                            if(movieImagesBackDropList != null && movieImagesBackDropList.size() > 0) {

                                if(movieImagesPostersList != null && movieImagesPostersList.size() > 0){
                                    movieImagesBackDropAndPostersArrayList.addAll(movieImagesBackDropList);
                                    movieImagesBackDropAndPostersArrayList.addAll(movieImagesPostersList);
                                }else{
                                    movieImagesBackDropAndPostersArrayList.addAll(movieImagesBackDropList);
                                }
                            }else if(movieImagesPostersList != null && movieImagesPostersList.size() > 0){
                                movieImagesBackDropAndPostersArrayList.addAll(movieImagesPostersList);
                            }else{
                                movieImagesBackDropAndPostersArrayList.clear();
                                movieDetailImagesLayout.setVisibility(View.GONE);
                            }

                            if(movieImagesBackDropAndPostersArrayList.size() > 0){
                                MoviePosterImagesAdapter moviePosterImagesAdapter = new MoviePosterImagesAdapter(MovieDetailActivity.this,movieImagesBackDropAndPostersArrayList);
                                movieDetailImageRecyclerView.setAdapter(moviePosterImagesAdapter);

                                movieDetailImagesLayout.setVisibility(View.VISIBLE);
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this,R.anim.layout_slide_right);
                                movieDetailImageRecyclerView.setLayoutAnimation(controller);
                                movieDetailImageRecyclerView.scheduleLayoutAnimation();
                            }

                        }else{
                            Log.d("debug", "Movie Images NULL");
                            Toast.makeText(MovieDetailActivity.this, "Movie Images Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieImagesModel> call, @NonNull Throwable t) {
                        Log.d("debug", "Movie Images Response Fail");
                    }
                });
                //Movie Video Call from API

                Call<MovieVideosModel>movieVideosModelCall = retrofitService.getMovieVideosById(movie_id, api);
                movieVideosModelCall.enqueue(new Callback<MovieVideosModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieVideosModel> call, @NonNull Response<MovieVideosModel> response) {
                        MovieVideosModel movieVideosModelResponse = response.body();

                        if(movieVideosModelResponse != null){
                            List<MovieVideosResults> movieVideosResultsList =  movieVideosModelResponse.getResults();

                            if(movieVideosResultsList != null && movieVideosResultsList.size() > 0){

                                MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(MovieDetailActivity.this,movieVideosResultsList);
                                movieDetailVideoRecyclerView.setAdapter(movieVideosAdapter);

                                movieDetailVideoLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailVideoRecyclerView.setLayoutAnimation(controller);
                                movieDetailVideoRecyclerView.scheduleLayoutAnimation();

                            }else{
                                movieDetailVideoLayout.setVisibility(View.GONE);
                                Toast.makeText(MovieDetailActivity.this, "Video not found", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            Toast.makeText(MovieDetailActivity.this, "Video not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieVideosModel> call, @NonNull Throwable t) {
                        Log.d("debug","Movie Videos Response Fail");
                    }
                });

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void prepareMovieDetails(MovieDetailModel movieDetailModelResponse) {

        String posterImagePath = movieDetailModelResponse.getPoster_path();
        String title = movieDetailModelResponse.getOriginal_title();
        String language = movieDetailModelResponse.getOriginal_language();
        boolean adult = movieDetailModelResponse.getAdult();
        String status = movieDetailModelResponse.getStatus();
        int runtime = movieDetailModelResponse.getRuntime();
        int budget = movieDetailModelResponse.getBudget();
        long revenue = movieDetailModelResponse.getRevenue();
        List<MovieDetailsGenres> genresList = movieDetailModelResponse.getGenres();
        List<MovieDetailProductCountry> productCountryList = movieDetailModelResponse.getProduction_countries();
        List<MovieDetailProductCompany> productCompanyList = movieDetailModelResponse.getProduction_companies();
        String release = movieDetailModelResponse.getRelease_date();
        String homePage = movieDetailModelResponse.getHomepage();
        String overview = movieDetailModelResponse.getOverview();
        Double voteAverage = movieDetailModelResponse.getVote_average() * 10;

        //        movieDetailRatingTextView.setText(String.valueOf(voteAverage));
        // you can set max and current progress values individually
//        circularProgress.setMaxProgress(10000);
//        circularProgress.setCurrentProgress(popularity);
// or all at once
        int rating = voteAverage.intValue();
        movieDetailRatingBar.setProgress(rating, 100);

// you can get progress values using following getters
//        circularProgress.getProgress(); // returns 5000
//        circularProgress.getMaxProgress();


        //Set Circular ImageView
        Picasso.get()
                .load(posterImagePath)
                .placeholder(R.drawable.image_loding)
                .into(movieDetailPosterCircleImageView);

//Set Poster ImageView
        Picasso.get()
                .load(posterImagePath)
                .placeholder(R.drawable.image_loding)
                .into(movieDetailPosterImage);

        if (title != null && title.length() > 0) {
            movieDetailTitle1TextView.setText(title);
            movieDetailTitle2TextView.setText(title);
            movieDetailTitleLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailTitleLayout.setVisibility(View.GONE);
        }

        if (language != null && language.length() > 0) {
            movieDetailLanguageTextView.setText(language);
            movieDetailLanguageLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailLanguageLayout.setVisibility(View.GONE);
        }
        if (adult) {

            movieDetailAdultTextView.setText("Yes");
            movieDetailAdultLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailAdultTextView.setText("No");
            movieDetailAdultLayout.setVisibility(View.VISIBLE);
        }

        if (status != null && status.length() > 0) {
            movieDetailStatusTextView.setText(status);
            movieDetailStatusLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailStatusLayout.setVisibility(View.GONE);
        }

        movieDetailRuntimeTextView.setText(String.valueOf(runtime));
        movieDetailRuntimeLayout.setVisibility(View.VISIBLE);

        movieDetailBudgetTextView.setText(String.valueOf(budget));
        movieDetailBudgetLayout.setVisibility(View.VISIBLE);

        movieDetailRevenueTextView.setText(String.valueOf(revenue));
        movieDetailRevenueLayout.setVisibility(View.VISIBLE);

        if (release != null && release.length() > 0) {
            movieDetailReleaseDateTextView.setText(release);
            movieDetailReleaseLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailReleaseLayout.setVisibility(View.GONE);
        }

        if (homePage != null && homePage.length() > 0) {
            movieDetailHomepageTextView.setText(homePage);
            movieDetailHomepageLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailHomepageLayout.setVisibility(View.GONE);
        }
        if (overview != null && overview.length() > 0) {
            movieDetailOverviewTextView.setText(overview);
            movieDetailOverviewLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailOverviewLayout.setVisibility(View.GONE);
        }


        if (genresList != null && genresList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < genresList.size(); i++) {

                if (i == genresList.size() - 1) {
                    stringBuilder.append(genresList.get(i).getName());
                } else {
                    stringBuilder.append(genresList.get(i).getName()).append(", ");
                }
            }
            movieDetailGenresTextView.setText(stringBuilder.toString());
            movieDetailGenresLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailGenresLayout.setVisibility(View.GONE);
        }

        if (productCountryList != null && productCountryList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < productCountryList.size(); i++) {

                if (i == productCountryList.size() - 1) {
                    stringBuilder.append(productCountryList.get(i).getName());
                } else {
                    stringBuilder.append(productCountryList.get(i).getName()).append(", ");
                }
            }
            movieDetailProductionCountryTextView.setText(stringBuilder.toString());
            movieDetailProductionCountryLayout.setVisibility(View.VISIBLE);
        } else {
            movieDetailProductionCountryLayout.setVisibility(View.GONE);
        }

        if (productCompanyList != null && productCompanyList.size() > 0) {

            MovieProductionCompanyAdapter movieProductionCompanyAdapter = new MovieProductionCompanyAdapter(MovieDetailActivity.this,productCompanyList);
            movieDetailProductionCompanyRecyclerView.setAdapter(movieProductionCompanyAdapter);

            movieDetailProductionCompanyLayout.setVisibility(View.VISIBLE);

            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
            movieDetailProductionCompanyRecyclerView.setLayoutAnimation(controller);
            movieDetailProductionCompanyRecyclerView.scheduleLayoutAnimation();

        } else {
            movieDetailProductionCompanyLayout.setVisibility(View.GONE);
            Toast.makeText(MovieDetailActivity.this, "Any Product Company details not found ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}