package com.popularmovies.eyad.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.popularmovies.eyad.popularmovies.NetworkUtilities.buildPosterPath;

public class Utilities  {

public static String imageSize = "w185";

  public static ArrayList<Movie> getMoviesInformationFromJson(String response) {
    String method = "getMoviesInformationFromJson";
    Log.v("JsonMethod","Start of getting movie information");
    Log.v("JsonMethod","response is "+ response);

    final String id = "id";
    final String title= "title";
    final String poster_path =  "poster_path";
    final String overview= "overview";
    final String release_date = "release_date";
    final String orignalLanguage= "original_language";
    final String results = "results";
    final String vote_average="vote_average";
    ArrayList<Movie>moviesList = new ArrayList<>();
    JSONObject jsonResult = null;
    try {
      jsonResult = new JSONObject(response);

    JSONArray mainJsonObject = jsonResult.getJSONArray(results);
      Log.i("JsonMethod", "Array object is "+ mainJsonObject);
    for(int i =0; i< mainJsonObject.length();i++) {
      Movie movie = new Movie();
      JSONObject movieObject = (JSONObject) mainJsonObject.get(i);
      String movieId = movieObject.getString(id);
      String movieTitle = movieObject.getString(title);
      String moviePosterath = movieObject.getString(poster_path);
      String description = movieObject.getString(overview);
      String releaseDate = movieObject.getString(release_date);
      String language = movieObject.getString(orignalLanguage);
      String  vote_avg= movieObject.getString(vote_average);
      Log.v("Path","Movie Path is "+ moviePosterath);

      movie.setMovieId(Integer.valueOf(movieId));
      movie.setMovieName(movieTitle);
      movie.setPosterPath(getFormatedPosterPath(moviePosterath));
      movie.setMovieDescription(description);
      movie.setVoteAvg(vote_avg);
      movie.setReleaseDate(releaseDate);


      moviesList.add(movie);

    }

    Log.v("reults", "Movie List length "+ moviesList.size());
    return moviesList;

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return null;


  }


  public static String getFormatedPosterPath(String imageKey)
  {
    String key = imageKey.substring(imageKey.indexOf("/")+1);
      URL url = NetworkUtilities.buildPosterPath(key,imageSize);
    Log.v("reults", "Poster url is "+ url.toString());
      return  url.toString();
  }

  public static List<Trailer> getSelectedMovieTrailers(String trailerResponse)
  {
    String method = "getSelectedMovieTrailers";
    Log.v("TrailersMethod","Start of getSelectedMovieTrailers");
    Log.v("TrailersMethod","response of the movie trailer is "+ trailerResponse);
    final String id ="id";
    final String key = "key";
    final String name = "name";
    final String results = "results";
    List<Trailer>trailers = new ArrayList<>();
    JSONObject jsonResult = null;
    try{
      jsonResult = new JSONObject(trailerResponse);
      JSONArray mainJsonObject = jsonResult.getJSONArray(results);
      Log.i("JsonMethod", "Array object is "+ mainJsonObject);
      for(int i =0; i< mainJsonObject.length();i++) {
        Trailer trailer = new Trailer();
        JSONObject trailerObject = (JSONObject) mainJsonObject.get(i);
        String trailerId = trailerObject.getString(id);
        String trailerKey = trailerObject.getString(key);
        String trailerName = trailerObject.getString(name);

        trailer.setTrailerID(trailerId);
        trailer.setTrailerKey(trailerKey);
        trailer.setTrailerTitle(trailerName);
        trailers.add(trailer);
      }
      return trailers;

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static List<Review> getSelectedMovieReviews(String reviewsResponse)
  {
    String method = "getSelectedMovieReviews";
    Log.v("getSelectedMovieReviews","Start of getSelectedMovieReviews");
    Log.v("getSelectedMovieReviews","response of the movie reviews is "+ reviewsResponse);

    final String id ="id";
    final String content = "content";
    final String name = "author";
    final String results = "results";
    List<Review>reviews = new ArrayList<>();
    JSONObject jsonResult = null;
    try{
      jsonResult = new JSONObject(reviewsResponse);
      JSONArray mainJsonObject = jsonResult.getJSONArray(results);
      Log.i("JsonMethod", "Array object is "+ mainJsonObject);
      for(int i =0; i< mainJsonObject.length();i++) {
        Review review = new Review();
        JSONObject reviewObject = (JSONObject) mainJsonObject.get(i);
        String reviewId = reviewObject.getString(id);
        String reviewContent = reviewObject.getString(content);
        String reviewAuthor = reviewObject.getString(name);

        review.setContent(reviewContent);
        review.setReviewID(reviewId);
        review.setAuthor(reviewAuthor);

        reviews.add(review);
      }
      return reviews;

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return null;
  }
}
