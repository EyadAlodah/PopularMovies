package com.popularmovies.eyad.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {



  private int movieId;
  private String movieName;
  private String posterPath;
  private String movieDescription;
  private String movieActors;
  private String movieGenere;
  private String releaseDate;
  private String voteAvg;
  private List<Trailer>trailers;
  private List<Review>reviews;




  public Movie()
  {
  }

  public String getVoteAvg() {
    return voteAvg;
  }

  public void setVoteAvg(String voteAvg) {
    this.voteAvg = voteAvg;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getMovieDescription() {
    return movieDescription;
  }

  public void setMovieDescription(String movieDescription) {
    this.movieDescription = movieDescription;
  }

  public String getMovieActors() {
    return movieActors;
  }

  public void setMovieActors(String movieActors) {
    this.movieActors = movieActors;
  }

  public String getMovieGenere() {
    return movieGenere;
  }

  public void setMovieGenere(String movieGenere) {
    this.movieGenere = movieGenere;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public List<Trailer> getTrailers() {
    return trailers;
  }

  public void setTrailers(List<Trailer> trailers) {
    this.trailers = trailers;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}
