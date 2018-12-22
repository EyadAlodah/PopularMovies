package com.popularmovies.eyad.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdabter extends BaseAdapter {


  public Context context;
  public List<Favorites>favorites;
  AppDatabase mDb;

  public FavoritesAdabter(Context c , List<Favorites>data)
  {
    this.favorites=data;
    this.context=c;
    mDb = AppDatabase.getInstance(c);
  }


  public FavoritesAdabter()
  {
  }


  public int getCount() {
    return this.favorites.size();
  }

  public Object getItem(int position) {
    return favorites.get(position);
  }

  public long getItemId(int position) {
    return 0;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {

    Log.v("Adabter", "In the adabter");

     Favorites favorite = favorites.get(position);
    Log.v("GridAdabter ", "List View is "+ favorite.getMovieId());
    ImageView imageView;
    if (convertView == null) {

      imageView = new ImageView(context);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.v("click", "clicked");
          Favorites favorites = (Favorites) getItem(position);
          Intent intent = new Intent(context.getApplicationContext(), MovieDetailsActitvity.class);


          Movie movie = new Movie();
          movie.setMovieName(favorites.getMovieName());
          movie.setVoteAvg(favorites.getMovieRating());
          movie.setMovieId(favorites.getMovieId());
          movie.setPosterPath(favorites.getMoviePoster());
          movie.setReleaseDate(favorites.getRelaseDate());
          movie.setMovieDescription(favorites.getMovieDescription());
          intent.putExtra("movie",movie);

          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent);

        }
      });

    } else {
      imageView = (ImageView) convertView;
    }

    Picasso.get()
            .load(favorite.getMoviePoster())
            .into(imageView);

    return imageView;
  }


}
