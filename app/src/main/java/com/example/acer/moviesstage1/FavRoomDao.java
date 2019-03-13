package com.example.acer.moviesstage1;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
//3
@Dao
public interface FavRoomDao {

    @Insert
    void insertData(FavMovieData movieData);

    @Delete
    void delete(FavMovieData favMovieData);

    @Query("Select * from Favorites")
    LiveData<List<FavMovieData>> getfavorites();

    @Query("Select * from Favorites where movieid==:id")
    FavMovieData favMovieData(String  id);


}
