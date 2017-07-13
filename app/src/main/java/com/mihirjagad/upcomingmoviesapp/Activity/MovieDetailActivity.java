package com.mihirjagad.upcomingmoviesapp.Activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mihirjagad.upcomingmoviesapp.Constants.Config;
import com.mihirjagad.upcomingmoviesapp.Model.ImagesDataModel;
import com.mihirjagad.upcomingmoviesapp.Model.MovieDetailsDataModel;
import com.mihirjagad.upcomingmoviesapp.R;
import com.mihirjagad.upcomingmoviesapp.Util.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView textViewMovieTitle;
    private TextView textViewOverview;
    private RatingBar ratingBar;
    public int movie_id;
    public String movie_name;
    private ArrayList<ImagesDataModel> imagesDataModelArrayList;
    private MovieDetailsDataModel movieDetailsDataModel;
    private ImagesDataModel imagesDataModel;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("Movie Id",0);
        movie_name = intent.getStringExtra("Movie Name");

        configureActionBar();

        imagesDataModelArrayList = new ArrayList<>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(5);

        textViewMovieTitle = (TextView) findViewById(R.id.movie_detail_title);
        textViewOverview = (TextView) findViewById(R.id.movie_detail_overview);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        getMoviesDetails();
        getImagesForMovie();

        viewPagerAdapter = new ViewPagerAdapter(MovieDetailActivity.this,imagesDataModelArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

    }


    /*
    * getting movie details from server.
    *
    * */
    public void getMoviesDetails(){
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Config.MOVIE_START_URL + String.valueOf(movie_id) + Config.MOVIE_PARAM_KEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

                parseMovieDetailData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error","Data Not Loaded");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    /*
    *
    * @param response
    * parsing movie details from JSON
    *
    * */

    public void parseMovieDetailData(String response){

        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(response);


            movieDetailsDataModel = new MovieDetailsDataModel();

            movieDetailsDataModel.setTitle(jsonObject.getString(Config.MOVIE_TITLE));
            movieDetailsDataModel.setOverview(jsonObject.getString(Config.MOVIE_OVERVIEW));
            movieDetailsDataModel.setPopularity(jsonObject.getDouble(Config.MOVIE_POPULARITY));

                textViewMovieTitle.setText(movieDetailsDataModel.getTitle());
                textViewOverview.setText(movieDetailsDataModel.getOverview());
                ratingBar.setRating((float) movieDetailsDataModel.getPopularity());




        }  catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
    *
    * getting images from server for movie.
    *
    * */
    public void getImagesForMovie(){
        StringRequest stringRequest = new StringRequest(Config.MOVIE_START_URL + String.valueOf(movie_id) + "/images" + Config.MOVIE_PARAM_KEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseMovieImagesData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error","Data Not Loaded");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    /*
    *
    * @param response
    * parsing movie images from JSON
    *
    **/
    public void parseMovieImagesData(String response){

        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(response);
            JSONArray images;
            images = jsonObject.getJSONArray(Config.JSON_ARRAY_BACKDROPS);

            for (int i = 0; i < images.length(); i++) {
                imagesDataModel = new ImagesDataModel();
                JSONObject jo = images.getJSONObject(i);
                imagesDataModel.setFilepath(Config.IMAGE_START_URL + jo.getString(Config.MOVIE_DETAIL_FILE_PATH));
                imagesDataModelArrayList.add(imagesDataModel);
            }
            viewPagerAdapter.notifyDataSetChanged();

        }  catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /*
    *
    * configuring custom action bar.
    *
    * */
    public void configureActionBar(){

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            View view = getSupportActionBar().getCustomView();

            TextView title = (TextView) view.findViewById(R.id.action_bar_title);
            title.setText(movie_name);

            ImageView information = (ImageView) view.findViewById(R.id.information);
            information.setVisibility(View.GONE);
        }

    }

}
