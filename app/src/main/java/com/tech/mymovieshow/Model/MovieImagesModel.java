package com.tech.mymovieshow.Model;

import java.util.List;

public class MovieImagesModel {

    private Integer id;

    private List<MovieImagesBackDropAndPosters>backdrops;

    private List<MovieImagesBackDropAndPosters>posters;

    public MovieImagesModel() {
    }

    public MovieImagesModel(Integer id, List<MovieImagesBackDropAndPosters> backdrops, List<MovieImagesBackDropAndPosters> posters) {
        this.id = id;
        this.backdrops = backdrops;
        this.posters = posters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieImagesBackDropAndPosters> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<MovieImagesBackDropAndPosters> backdrops) {
        this.backdrops = backdrops;
    }

    public List<MovieImagesBackDropAndPosters> getPosters() {
        return posters;
    }

    public void setPosters(List<MovieImagesBackDropAndPosters> posters) {
        this.posters = posters;
    }
}
