package com.popularmovies.eyad.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface FavoritesDao {

  @Query("Select * from favorites ")
  LiveData<List<Favorites>> loadUserFavorite();

  @Insert
  void insetFav(Favorites favorites);

  @Delete
  void deleteFav(Favorites favorites);

  @Query("Select * from favorites where movieName= :name")
  Favorites loadMovieByName(String name);
}
