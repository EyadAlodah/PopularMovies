package com.popularmovies.eyad.popularmovies;

import java.io.Serializable;

public class Review implements Serializable {

  private String author;
  private String content;
  private String reviewID;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getReviewID() {
    return reviewID;
  }

  public void setReviewID(String reviewID) {
    this.reviewID = reviewID;
  }
}
