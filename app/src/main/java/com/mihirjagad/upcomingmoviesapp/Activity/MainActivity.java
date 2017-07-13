package com.mihirjagad.upcomingmoviesapp.Activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mihirjagad.upcomingmoviesapp.Constants.Config;
import com.mihirjagad.upcomingmoviesapp.Util.CustomListAdapter;
import com.mihirjagad.upcomingmoviesapp.Model.MovieDataModel;
import com.mihirjagad.upcomingmoviesapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MovieDataModel> listMovies;
    private MovieDataModel movieDataModel;
    private CustomListAdapter customListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureActionBar();

        listView = (ListView) findViewById(R.id.list);
        listMovies = new ArrayList<>();
        customListAdapter = new CustomListAdapter(this, listMovies);
        listView.setAdapter(customListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("Movie Name", listMovies.get(position).getTitle());
                intent.putExtra("Movie Id", listMovies.get(position).getId());
                startActivity(intent);
            }
        });

        getMovies();
    }

    /*
    *  getting movies from the server.
    *
    * */
    public void getMovies()
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Config.MOVIE_DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

                parseMovieData(response);
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
    * parsing JSON data received from server.
    *
    **/

    public void parseMovieData(String response){

        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(response);
            JSONArray movies;
            movies = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < movies.length(); i++) {
                movieDataModel = new MovieDataModel();
                JSONObject jo = movies.getJSONObject(i);
                movieDataModel.setId(jo.getInt(Config.MOVIE_ID));
                movieDataModel.setTitle(jo.getString(Config.MOVIE_TITLE));
                movieDataModel.setPosterUrl(Config.IMAGE_START_URL + jo.getString(Config.MOVIE_POSTER_PATH));
                movieDataModel.setReleaseDate(jo.getString(Config.MOVIE_RELEASE_DATE));
                movieDataModel.setAdult(jo.getBoolean(Config.MOVIE_ADULT));

                listMovies.add(movieDataModel);
            }

        }  catch (JSONException e) {
            e.printStackTrace();
        }
        customListAdapter.notifyDataSetChanged();
    }



    /*
    *
    * configuring custom action bar.
    *
    * */
    public void configureActionBar(){
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            View view =getSupportActionBar().getCustomView();

            ImageView information= (ImageView) view.findViewById(R.id.information);
            information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


}
