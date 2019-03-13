package com.example.acer.moviesstage1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavRepos {

    private FavRoomDao favRoomDao;
    private LiveData<List<FavMovieData>> getGetfavorites;
    FavRepos(Application application)
    {
        FavoriteMovieDB movieDB=FavoriteMovieDB.getDatabase(application);
        favRoomDao=movieDB.favRoomDao();
        getGetfavorites=favRoomDao.getfavorites();
    }


    LiveData<List<FavMovieData>> getGetGetfavorites(){
        return getGetfavorites ;
    }


    public FavMovieData checkFav(String  id)
    {
        FavMovieData favMovieData=favRoomDao.favMovieData(id);
        return favMovieData;
    }
    public void insert(FavMovieData favMovieData){
        new insertAsyncTask(favRoomDao).execute(favMovieData);

    }
    public void deleteData(FavMovieData favMovieData)
    {
        new deleteAsync(favRoomDao).execute(favMovieData);
    }


    private class insertAsyncTask extends AsyncTask<FavMovieData ,Void, Void>
    {
        FavRoomDao dao;
        public insertAsyncTask(FavRoomDao favRoomDao)
        {
            this.dao=favRoomDao;
        }

        @Override
        protected Void doInBackground(FavMovieData... favMovieData) {
            dao.insertData(favMovieData[0]);
            return null;
        }
    }

    private class deleteAsync extends AsyncTask<FavMovieData,Void,Void>
    {
        FavRoomDao deleteDao;

        public deleteAsync(FavRoomDao favRoomDao) {
            this.deleteDao=favRoomDao;
        }

        @Override
        protected Void doInBackground(FavMovieData... favMovieData)
        {
            deleteDao.delete(favMovieData[0]);
            return null;
        }
    }

}
