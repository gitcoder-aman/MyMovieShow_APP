package com.tech.mymovieshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tech.mymovieshow.Client.RetrofitClient;
import com.tech.mymovieshow.Interfaces.RetrofitService;
import com.tech.mymovieshow.Model.MovieResponse;
import com.tech.mymovieshow.Model.MovieResponseResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private MaterialSpinner spinner;
    private AppCompatEditText queryEditText;
    private AppCompatButton querySearchBtn;
    private RecyclerView resultRecyclerView;

    private String movie = "By Movie Title";
    private String person = "By Person Name";
    private final static String api = "ac28a3498de90c46b11f31bda02b8b97";

    //initiate the Retrofit Service
    private RetrofitService retrofitService;


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

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        final ArrayList<String> category = new ArrayList<>();
        //set List for Spinner
        category.add(movie);
        category.add(person);

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

                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        //get the query from user

        querySearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (queryEditText.getText().toString() != null) {

                    String query = queryEditText.getText().toString();

                    if (query.equals("") || query.equals(" ")) {
                        Toast.makeText(MainActivity.this, "Please enter any text..", Toast.LENGTH_SHORT).show();
                    } else {
                        queryEditText.setText("");

                        //get the category to search the query . movie or Person
                        String finalQuery = query.replace(" ", "+");   //all space character convert into concat the string.

                        if (category.size() > 0) {
                            String categoryName = category.get(spinner.getSelectedIndex());
                            if (categoryName.equals(movie)) {

                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByQuery(api, finalQuery);

                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                                        MovieResponse movieResponse = response.body();
                                        if (movieResponse != null) {

                                            List<MovieResponseResults> movieResponseResultsList = movieResponse.getResults();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                                    }
                                });
                            } else {

                            }
                        }
                    }
                }
            }
        });
    }
}