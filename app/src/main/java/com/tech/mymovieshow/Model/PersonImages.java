package com.tech.mymovieshow.Model;

import java.util.List;

public class PersonImages {

    private List<PersonImagesProfile>profiles;
    private Integer id;

    public PersonImages() {
    }

    public PersonImages(List<PersonImagesProfile> profiles, Integer id) {
        this.profiles = profiles;
        this.id = id;
    }

    public List<PersonImagesProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<PersonImagesProfile> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
