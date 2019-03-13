package com.example.acer.moviesstage1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavModel extends AndroidViewModel {


    FavRepos favRepos;
    LiveData<List<FavMovieData>> getLiveData;


    public FavModel(@NonNull Application application) {
        super(application);
        favRepos=new FavRepos(application);
        getLiveData=favRepos.getGetGetfavorites();
    }
    public void insertData(FavMovieData movieData)
    {
        favRepos.insert(movieData);
    }

    public LiveData<List<FavMovieData>> getMovieData()
    {
        return getLiveData;
    }

    public void deleteData(FavMovieData movieData)
    {
        favRepos.deleteData(movieData);
    }

    public FavMovieData checkFavMovie(String id)
    {
        return favRepos.checkFav(id);
    }
}
