package com.popularmovies.eyad.popularmovies;

import java.io.Serializable;

public class Trailer implements Serializable {

  private String trailerID;
  private String trailerKey;
  private String trailerTitle;

  public String getTrailerKey() {
    return trailerKey;
  }

  public void setTrailerKey(String trailerKey) {
    this.trailerKey = trailerKey;
  }

  public String getTrailerTitle() {
    return trailerTitle;
  }

  public void setTrailerTitle(String trailerTitle) {
    this.trailerTitle = trailerTitle;
  }

  public String getTrailerID() {
    return trailerID;
  }

  public void setTrailerID(String trailerID) {
    this.trailerID = trailerID;
  }
}
