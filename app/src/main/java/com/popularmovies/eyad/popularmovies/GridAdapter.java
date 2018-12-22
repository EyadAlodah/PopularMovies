package com.popularmovies.eyad.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

  Context mContext;
  ArrayList<Movie>moviesList= new ArrayList<>();
  public String formatedPath;

  public GridAdapter(Context context, ArrayList<Movie>list)
  {
    this.mContext = context;
    this.moviesList = list;
  }

  public int getCount() {
    return this.moviesList.size();
  }

  public Object getItem(int position) {
    return moviesList.get(position);
  }

  public long getItemId(int position) {
    return 0;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {

    Log.v("Adabter", "In the adabter");

    Movie movie = moviesList.get(position);
    Log.v("GridAdabter ", "List View is "+ movie.getPosterPath());
    ImageView imageView;
    if (convertView == null) {

      imageView = new ImageView(mContext);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.v("click", "clicked");
          Movie movie = (Movie) getItem(position);
          Intent intent = new Intent(mContext.getApplicationContext(), MovieDetailsActitvity.class);
          intent.putExtra("movie", movie);

          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          mContext.startActivity(intent);

        }
      });

    } else {
      imageView = (ImageView) convertView;
    }

    Picasso.get()
      .load(movie.getPosterPath())
      .into(imageView);

    return imageView;
  }






}
