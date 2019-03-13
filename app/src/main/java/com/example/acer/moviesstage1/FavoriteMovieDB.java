package com.example.acer.moviesstage1;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavMovieData.class},version = 1)

public abstract class FavoriteMovieDB extends RoomDatabase {
    public abstract FavRoomDao favRoomDao();
    private static volatile FavoriteMovieDB INSTANCE;

    static FavoriteMovieDB getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (FavoriteMovieDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteMovieDB.class, "Room_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
    }
            }
        }

        return INSTANCE;
}
}
