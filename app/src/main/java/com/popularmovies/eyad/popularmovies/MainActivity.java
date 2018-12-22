package com.popularmovies.eyad.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  GridView mGridView;
  public String selectedSetting ="popular";
  public ArrayList<Movie> moviesList =  new ArrayList<>();
  public static final String CURRENT_POSITION = "current_position";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Log.i("onCreate", "Start of the application");
    mGridView = findViewById(R.id.gridview);


    //NetworkUtilities.buildUrl("550");

    selectedSetting = loadPreferences();
    if(!selectedSetting.equals("favorites"))
    new FetchMoviesDataTask().execute(selectedSetting);
    else
    {
      retrieveFavorites();
    }
    if(savedInstanceState != null)
    {
      int currentPosition= savedInstanceState.getInt(CURRENT_POSITION);
      mGridView.setSelection(currentPosition);
    }



  }

  @Override
  public void onSaveInstanceState(Bundle outState) {

    outState.putInt(CURRENT_POSITION, mGridView.getFirstVisiblePosition());
    super.onSaveInstanceState(outState);

  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Log.v("Current v", "Current View"+ savedInstanceState.getInt(CURRENT_POSITION));
    int currentPosition= savedInstanceState.getInt(CURRENT_POSITION);
  }

  @Override
  public void onBackPressed() {
    savePreferences();
    super.onBackPressed();
  }


  private void savePreferences(){
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("selectedSetting", selectedSetting);
    editor.commit();
  }

  private String loadPreferences(){
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    String  selectedValue = sharedPreferences.getString("selectedSetting","popular");
    return  selectedValue;
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.popular_movies) {
      selectedSetting= "popular";
      savePreferences();
      new FetchMoviesDataTask().execute("popular");
      return true;
    }

    if(id== R.id.top_rated)
    {
      selectedSetting= "top_rated";
      savePreferences();
      new FetchMoviesDataTask().execute("top_rated");
      return true;

    }
    if(id == R.id.favorites_movie)
    {
      selectedSetting = "favorites";
      savePreferences();
      retrieveFavorites();

    }
    return super.onOptionsItemSelected(item);
  }



  public void retrieveFavorites() {

    MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    viewModel.getCatchedFavs().observe(this, new Observer<List<Favorites>>() {
      @Override
      public void onChanged(@Nullable List<Favorites> favorites) {
        Log.v("retrieveFavorites", "ViewMode and LiveData retriving favorites");
        mGridView.setAdapter(new FavoritesAdabter(getApplicationContext(), favorites));
      }
    });


  }
  public class FetchMoviesDataTask extends AsyncTask <String, Integer, ArrayList<Movie>> {


    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
      if (strings.length == 0) {
        return null;
      }

      String urlString = strings[0];

      try{
        URL movieURL = NetworkUtilities.buildUrl(urlString);
        String jsonResponse = NetworkUtilities.getResponseFromHttpUrl(movieURL);
        Log.i("FetchMoviesDataTask", "response is "+ jsonResponse);

        moviesList= Utilities.getMoviesInformationFromJson(jsonResponse);
        return moviesList;

      }catch (Exception e)
      {
        Log.e("FetchMoviesDataTask", "Error while bulding connection "+ e.getMessage());
        return null;
      }

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
      Log.i("FetchMoviesDataTask", "onPostExecute method");

      if(movies ==null)
      {
        Toast.makeText(getApplicationContext(),"There are no Movies to show", Toast.LENGTH_LONG).show();

      }
      if(movies !=null && movies.size()>0)
      {
            moviesList = movies;
            mGridView.setAdapter(new GridAdapter(getApplicationContext(), moviesList));

      }else
      {
        Toast.makeText(getApplicationContext(),"There are no Movies to show", Toast.LENGTH_LONG).show();
      }

    }
  }
}
