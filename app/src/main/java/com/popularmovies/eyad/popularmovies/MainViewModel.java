package com.popularmovies.eyad.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
   private LiveData<List<Favorites>> catchedFavs;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        catchedFavs = database.favoritesDao().loadUserFavorite();
    }

    public LiveData<List<Favorites>> getCatchedFavs() {
        return catchedFavs;
    }


}
