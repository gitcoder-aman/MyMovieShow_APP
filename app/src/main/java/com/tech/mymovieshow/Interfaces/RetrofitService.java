package com.tech.mymovieshow.Interfaces;


import com.tech.mymovieshow.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    //https:api.themoviedb.org/3/  - this is base url .we have already created in client

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
    Call<MovieResponse> getPersonByQuery(@Query("api_key") String api_key, @Query("query") String query);




}
