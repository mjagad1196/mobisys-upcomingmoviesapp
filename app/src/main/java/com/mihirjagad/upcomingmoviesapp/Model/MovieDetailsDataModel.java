package com.mihirjagad.upcomingmoviesapp.Model;

/**
 * Created by mihirjagad on 10/7/17.
 */

public class MovieDetailsDataModel {

    private String title;
    private String overview;
    private double popularity;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
