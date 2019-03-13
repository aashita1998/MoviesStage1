package com.example.acer.moviesstage1.Model_Movies;

public class Movie {
    String title;
    String image;
    String id;
    String vote;
    String overview;
    String releasedate;

    public Movie(String image, String title, String id, String vote, String overview, String releasedate) {
        this.title=title;
        this.image=image;
        this.id=id;
        this.overview=overview;
        this.vote=vote;
        this.releasedate=releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }
}
