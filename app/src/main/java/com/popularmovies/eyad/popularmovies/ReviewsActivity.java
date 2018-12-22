package com.popularmovies.eyad.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ReviewsActivity extends AppCompatActivity {

  ListView reviewsListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reviews);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    reviewsListView= findViewById(R.id.reviewslists);
    Log.i("OnCreate", "Inside Reviews Activity");
    Intent comments = getIntent();

    if(comments.getExtras().getSerializable("reviews") != null)
    {
      Movie movie = (Movie) comments.getExtras().getSerializable("reviews");
      Log.v("Movie", "Reviews list size "+ movie.getReviews().size());

      setTitle(movie.getMovieName());
      reviewsListView.setAdapter(new ReviewsAdapter(getApplicationContext(),movie.getReviews()));

    }


  }
}
