package com.tech.mymovieshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tech.mymovieshow.Adapters.MovieSearchAdapter;
import com.tech.mymovieshow.Adapters.PersonSearchAdapter;
import com.tech.mymovieshow.Client.RetrofitClient;
import com.tech.mymovieshow.Interfaces.RetrofitService;
import com.tech.mymovieshow.Model.MovieResponse;
import com.tech.mymovieshow.Model.MovieResponseResults;
import com.tech.mymovieshow.Model.PersonResponse;
import com.tech.mymovieshow.Model.PersonResponseResults;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private MaterialSpinner spinner;
    private AppCompatEditText queryEditText;
    private AppCompatButton querySearchBtn;
    private RecyclerView resultRecyclerView;

    private final String movie = "By Movie Title";
    private final String person = "By Person Name";
    private final static String api = "ac28a3498de90c46b11f31bda02b8b97";

    //initiate the Retrofit Service
    private RetrofitService retrofitService;
    private MovieSearchAdapter movieSearchAdapter;
    private PersonSearchAdapter personSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //disable the keyword on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        spinner = (MaterialSpinner) findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.queryEditText);
        querySearchBtn = findViewById(R.id.query_search_btn);
        resultRecyclerView = findViewById(R.id.results_recyclerView);

        resultRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        Paper.init(this);

        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        final ArrayList<String> category = new ArrayList<>();
        //set List for Spinner
        category.add(movie);
        category.add(person);

        spinner.setItems(category);

        //retrieve the position at start and the set the spinner
        if (Paper.book().read("position") != null) {
            int position = Paper.book().read("position");
            spinner.setSelectedIndex(position);
        }

        int position = spinner.getSelectedIndex();
        if (position == 0) {
            queryEditText.setHint("Enter any movie title...");
        } else {
            queryEditText.setHint("Enter any Person name...");
        }

//        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinner.setItems(category);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //when spinner is clicked changed the text of the editText
                if (position == 0) {
                    queryEditText.setHint("Enter any movie title...");
                } else {
                    queryEditText.setHint("Enter any Person name...");
                }

                Snackbar.make(view, "Switched " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        //retrieve the results from paper db and start
        if (Paper.book().read("cache") != null) {
            String results = Paper.book().read("cache");

            if (Paper.book().read("source") != null) {
                String source = Paper.book().read("source");
                if (source.equals("movie")) {
                    //convert the string cache to model movie response class using gson
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);
                    if (movieResponse != null) {

                        List<MovieResponseResults> movieResponseResultsList = movieResponse.getResults();

                        movieSearchAdapter = new MovieSearchAdapter(MainActivity.this, movieResponseResultsList);
                        resultRecyclerView.setAdapter(movieSearchAdapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);
                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        //now store the results in paper database to access offline

                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                        //store also category to set the spinner at app start
                        Paper.book().write("source", "movie");
                    }

                } else {
                    PersonResponse personResponse = new Gson().fromJson(results, PersonResponse.class);

                    if (personResponse != null) {

                        List<PersonResponseResults> personResponseResultsList = personResponse.getResults();

                        personSearchAdapter = new PersonSearchAdapter(MainActivity.this, personResponseResultsList);
                        resultRecyclerView.setAdapter(personSearchAdapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);
                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        //now store the results in paper database to access offline

                        Paper.book().write("cache", new Gson().toJson(personResponse));

                        //store also category to set the spinner at app start
                        Paper.book().write("source", "person");
                    }
                }
            }
        }
        //get the query from user

        querySearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // keyword close after search button click
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);


                if (queryEditText.getText().toString() != null) {

                    String query = queryEditText.getText().toString();

                    if (query.equals("") || query.equals(" ")) {
                        Toast.makeText(MainActivity.this, "Please enter any text..", Toast.LENGTH_SHORT).show();
                    } else {
                        queryEditText.setText("");

                        //get the category to search the query . movie or Person
                        String finalQuery = query.replace(" ", "+");   //all space character convert into concat the string.
                        Log.d("debug", finalQuery);

                        if (category.size() > 0) {
                            String categoryName = category.get(spinner.getSelectedIndex());
                            Log.d("debug", categoryName);

                            if (categoryName.equals(movie)) {

                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByQuery(api, finalQuery);

                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                                        MovieResponse movieResponse = response.body();
                                        if (movieResponse != null) {
                                            Log.d("debug", "onResponse on movie");

                                            List<MovieResponseResults> movieResponseResultsList = movieResponse.getResults();

                                            if (!movieResponseResultsList.isEmpty()) { //here checking is movie is  available or not

                                                Log.d("debug", "not null");
                                                Log.d("debug", movieResponseResultsList.get(0).getTitle());

                                                movieSearchAdapter = new MovieSearchAdapter(MainActivity.this, movieResponseResultsList);
                                                resultRecyclerView.setAdapter(movieSearchAdapter);

                                                //Create some animation view item loading
                                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);
                                                resultRecyclerView.setLayoutAnimation(controller);
                                                resultRecyclerView.scheduleLayoutAnimation();

                                                //now store the results in paper database to access offline

                                                Paper.book().write("cache", new Gson().toJson(movieResponse));

                                                //store also category to set the spinner at app start
                                                Paper.book().write("source", "movie");
                                            } else {
                                                Snackbar.make(view, query + " Movie haven't available", Snackbar.LENGTH_LONG).show();
                                                Log.d("debug", "No Movie Response");
                                            }
                                        } else {
                                            Log.d("debug", "MovieResponse NULL");
                                        }

                                    }  //onResponse close bracket

                                    @Override
                                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                                        Log.d("debug", "Fail API for movie");
                                    }
                                });
                            } else {

                                //Now do the same thing of person

                                Call<PersonResponse> personResponseCall = retrofitService.getPersonByQuery(api, finalQuery);

                                personResponseCall.enqueue(new Callback<PersonResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<PersonResponse> call, @NonNull Response<PersonResponse> response) {

                                        PersonResponse personResponse = response.body();
                                        if (personResponse != null) {

                                            Log.d("debug", "onResponse on Person");
                                            List<PersonResponseResults> personResponseResultsList = personResponse.getResults();

                                            personSearchAdapter = new PersonSearchAdapter(MainActivity.this, personResponseResultsList);
                                            resultRecyclerView.setAdapter(personSearchAdapter);

                                            //Create some animation view item loading
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);
                                            resultRecyclerView.setLayoutAnimation(controller);
                                            resultRecyclerView.scheduleLayoutAnimation();

                                            //now store the results in paper database to access offline

                                            Paper.book().write("cache", new Gson().toJson(personResponse));

                                            //store also category to set the spinner at app start
                                            Paper.book().write("source", "person");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<PersonResponse> call, @NonNull Throwable t) {
                                        Log.d("debug", "Fail API for Person");
                                    }
                                });

                            }
                        }
                    }
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        //set the position of spinner in offline to retrieve at start

        Paper.book().write("position", spinner.getSelectedIndex());
    }
}