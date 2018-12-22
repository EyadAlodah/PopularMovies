package com.popularmovies.eyad.popularmovies;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorites")
public class Favorites implements Serializable {

  @PrimaryKey
  private int movieId;
  private String movieName;
  private String movieDescription;
  private String moviePoster;
  private String movieRating;
  private String relaseDate;



  public Favorites(String movieName, String movieDescription, String moviePoster, int movieId, String movieRating, String relaseDate)
  {

      this.movieName = movieName;
      this.movieDescription = movieDescription;
      this.moviePoster= moviePoster;
      this.movieId= movieId;
      this.movieRating = movieRating;
      this.relaseDate = relaseDate;
  }



  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public String getMovieDescription() {
    return movieDescription;
  }

  public void setMovieDescription(String movieDescription) {
    this.movieDescription = movieDescription;
  }

  public String getMoviePoster() {
    return moviePoster;
  }

  public void setMoviePoster(String moviePoster) {
    this.moviePoster = moviePoster;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

  public String getRelaseDate() {
    return relaseDate;
  }

  public void setRelaseDate(String relaseDate) {
    this.relaseDate = relaseDate;
  }
}
