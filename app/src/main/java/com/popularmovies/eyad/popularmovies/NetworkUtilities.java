package com.popularmovies.eyad.popularmovies;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

  /**
   * These utilities will be used to communicate with the weather servers.
   */
  public final class NetworkUtilities {

    private static final String TAG = NetworkUtilities.class.getSimpleName();

    private static final String MOVIE_API_URL=
      "https://api.themoviedb.org";

    private static final String API_KEY =
      "key";

    private static final String MOVIE_POSTER_RAW_URL = "http://image.tmdb.org";

    private final static String MOVIE_PATH= "3";

    private final static String MOVIE_CATEGORY= "movie";

    private final static String API_KEY_QUERY="api_key";

    private final static String popular= "popular";

    private final static String t ="t";

    private final static String p ="p";



    public static URL buildUrl(String movieID) {
      Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon().appendPath(MOVIE_PATH).appendPath(MOVIE_CATEGORY).appendPath(movieID)
        .appendQueryParameter(API_KEY_QUERY,API_KEY)
        .build();

      URL url = null;
      try {
        url = new URL(builtUri.toString());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      Log.v(TAG, "Built URI " + url);

      return url;
    }


    public static URL buildPosterPath(String posterName, String size) {
      Uri builtUri = Uri.parse(MOVIE_POSTER_RAW_URL).buildUpon().appendPath(t).appendPath(p).appendPath(size)
        .appendPath(posterName)
        .build();

      URL url = null;
      try {
        url = new URL(builtUri.toString());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      Log.v(TAG, "Built URI " + url);

      return url;
    }


    public static URL  buildMoveExtraInformationPath(String extraInformation,String id)
    {
      Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon().appendPath(MOVIE_PATH).appendPath(MOVIE_CATEGORY).appendPath(id).appendPath(extraInformation).appendQueryParameter(API_KEY_QUERY,API_KEY).build();

      URL url = null;
      try {
        url = new URL(builtUri.toString());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      Log.v(TAG, "Built URI " + url);

      return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      try {
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
          return scanner.next();
        } else {
          return null;
        }
      } finally {
        urlConnection.disconnect();
      }
    }
  }

