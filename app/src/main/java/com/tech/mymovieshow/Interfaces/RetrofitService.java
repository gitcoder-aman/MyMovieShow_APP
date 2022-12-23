package com.tech.mymovieshow.Interfaces;


import com.tech.mymovieshow.Model.MovieDetailModel;
import com.tech.mymovieshow.Model.MovieResponse;
import com.tech.mymovieshow.Model.PersonDetailModel;
import com.tech.mymovieshow.Model.PersonImages;
import com.tech.mymovieshow.Model.PersonImagesProfile;
import com.tech.mymovieshow.Model.PersonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    //https://api.themoviedb.org/3/  - this is base url .we have already created in client

    //https://api.themoviedb.org/3/search/movie?api_key={YOUR_API_KEY}&query={MOVIE_NAME}   //without curly brackets

    //this is url to get the movie results

    //https://api.themoviedb.org/3/search/person?api_key={YOUR_API_KEY}&query={ACTOR_NAME}   //without curly brackets

    //this is url to get the person results

    //Now create a service to the get result and convert results into model classes

    @GET("search/movie")
    Call<MovieResponse> getMovieByQuery(@Query("api_key") String api_key, @Query("query") String query);

    //now a create a service for person response

    //Before that create a model class for person results

    @GET("search/person")
    Call<PersonResponse> getPersonByQuery(@Query("api_key") String api_key, @Query("query") String query);


    //Create a service for person details

    @GET("person/{person_id}")
    Call<PersonDetailModel>getPersonDetailsById(@Path("person_id") int person_id, @Query("api_key") String api_key);

    //create a service to get the images

    @GET("person/{person_id}/images")
    Call<PersonImages>getPersonImagesById(@Path("person_id") int person_id, @Query("api_key") String api_key);

    //create a Service for movie Details

    @GET("movie/{movie_id}")
    Call<MovieDetailModel>getMovieDetailsById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);


}
