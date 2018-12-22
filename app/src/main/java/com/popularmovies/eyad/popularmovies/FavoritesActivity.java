package com.popularmovies.eyad.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity{


  private AppDatabase mDb;
   GridView favListGridView;
  LiveData<List<Favorites>> favorites;
  FavoritesInterface favoritesInterface;
  public static final String CURRENT_POSITION = "current_position";

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorites);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mDb = AppDatabase.getInstance(getApplicationContext());
    favListGridView = findViewById(R.id.gridviewFav);
        retrieveFavorites();
        if(savedInstanceState != null)
        {
            int currentPosition= savedInstanceState.getInt(CURRENT_POSITION);
            favListGridView.setSelection(currentPosition);
        }




    }



    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(CURRENT_POSITION, favListGridView.getFirstVisiblePosition());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("Current v", "Current View"+ savedInstanceState.getInt(CURRENT_POSITION));
        int currentPosition= savedInstanceState.getInt(CURRENT_POSITION);
    }

  @Override
  protected void onResume() {
    super.onResume();
    Log.v("OnResume","Favorite activity on resume");
  }



    public void retrieveFavorites() {

      MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getCatchedFavs().observe(this, new Observer<List<Favorites>>() {
            @Override
            public void onChanged(@Nullable List<Favorites> favorites) {
                Log.v("retrieveFavorites", "ViewMode and LiveData retriving favorites");
                favListGridView.setAdapter(new FavoritesAdabter(getApplicationContext(), favorites));
            }
        });


    }


}
