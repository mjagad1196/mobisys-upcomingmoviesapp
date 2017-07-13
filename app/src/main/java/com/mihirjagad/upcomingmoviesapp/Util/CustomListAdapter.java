package com.mihirjagad.upcomingmoviesapp.Util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mihirjagad.upcomingmoviesapp.Model.MovieDataModel;
import com.mihirjagad.upcomingmoviesapp.R;

import java.util.ArrayList;

/**
 * Created by mihirjagad on 8/7/17.
 */

public class CustomListAdapter extends ArrayAdapter<MovieDataModel> {
    private ArrayList<MovieDataModel> movieDataModelArrayList;

    private ImageLoader imageLoader;
    Activity context;

    public CustomListAdapter(Activity context, ArrayList<MovieDataModel> dataModelArrayList){
        super(context, R.layout.list_movies_layout, dataModelArrayList);
        this.context = context;
        this.movieDataModelArrayList = dataModelArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_movies_layout, null, true);

        NetworkImageView moviePoster = (NetworkImageView) listViewItem.findViewById(R.id.movie_poster);
        TextView textViewMovieTitle = (TextView) listViewItem.findViewById(R.id.movie_title);
        TextView textViewReleaseDate = (TextView) listViewItem.findViewById(R.id.release_date);
        TextView textViewAdult = (TextView) listViewItem.findViewById(R.id.adult);

        MovieDataModel dataModel = movieDataModelArrayList.get(position);

        imageLoader = CustomImageRequest.getInstance(context).getImageLoader();
        imageLoader.get(dataModel.getPosterUrl(),ImageLoader.getImageListener(moviePoster,0,0));

        moviePoster.setImageUrl(dataModel.getPosterUrl(),imageLoader);
        textViewMovieTitle.setText(dataModel.getTitle());
        textViewReleaseDate.setText(dataModel.getReleaseDate());
        if (dataModel.isAdult())
            textViewAdult.setText("A");
        else
            textViewAdult.setText("A/U");

        return listViewItem;
    }
}