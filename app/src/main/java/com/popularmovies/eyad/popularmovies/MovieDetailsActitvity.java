package com.popularmovies.eyad.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActitvity extends AppCompatActivity {

    TextView title;
    TextView overview;
    TextView releaseDate;
    View spaceView;
    TextView trailersTextView;
    TextView avgRating;
    ListView trailersListView;
    ImageView posterImage;
    Button reviewsButton;
    Favorites retrievedFav;
    ImageView favoriteStarImage;
    AppDatabase mDb;
    Movie movie;
    int currentPosition=0;
    ScrollView scrollView;
    private final static String videos = "videos";
    private final static String reviews = "reviews";
    public static final String CURRENT_POSITION = "current_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_movie_details_actitvity, null);


        Log.v("onCreate", "Started of the movieDetails Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = view.findViewById(R.id.title);
        overview = view.findViewById(R.id.desc);
        releaseDate = view.findViewById(R.id.date);
        favoriteStarImage = view.findViewById(R.id.buttonImage);
        posterImage = view.findViewById(R.id.poster);
        trailersListView = findViewById(R.id.listviewLayout);
        avgRating = view.findViewById(R.id.rating);
        trailersTextView = view.findViewById(R.id.trailersTextView);
        spaceView = view.findViewById(R.id.space2);
        reviewsButton = view.findViewById(R.id.reviewsButton);
        scrollView = view.findViewById(R.id.scrollView);

        mDb = AppDatabase.getInstance(getApplicationContext());
        Intent movieDetails = (Intent) getIntent();

        trailersListView.addHeaderView(view);

        if (movieDetails.getExtras().getSerializable("movie") != null) {

            movie = (Movie) movieDetails.getExtras().getSerializable("movie");
            new FetchMovieExtraInformationTask().execute(movie);
            generateView(movie);
        }
        else {
            setViewsInvisible();
        }


        checkIsAddedToFavorite();
        favoriteStarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (retrievedFav != null) {
                    removeFromFavorites();
                } else {
                    addToFavorite();
                }
            }
        });



    }

    public void showReviews(View v) {
        Intent reviewActivity = new Intent(this, ReviewsActivity.class);
        reviewActivity.putExtra("reviews", movie);
        startActivity(reviewActivity);
    }

    public void generateView(Movie movie) {
        title.setText(movie.getMovieName());
        overview.setText(movie.getMovieDescription());
        releaseDate.setText(movie.getReleaseDate());
        avgRating.setText(movie.getVoteAvg());
        Picasso.get()
                .load(movie.getPosterPath())
                .into(posterImage);
        Log.v("onCreate", "Movie " + movie + " Clicked");
    }

    public void setViewsInvisible() {
        title.setVisibility(View.INVISIBLE);
        overview.setVisibility(View.INVISIBLE);
        releaseDate.setVisibility(View.INVISIBLE);
        posterImage.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "No Information To display", Toast.LENGTH_LONG).show();

    }



    public void checkIsAddedToFavorite() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                retrievedFav = mDb.favoritesDao().loadMovieByName(movie.getMovieName());
                if(retrievedFav != null)
                {
                    favoriteStarImage.setImageResource(R.mipmap.fav_star);
                }else{
                    favoriteStarImage.setImageResource(R.mipmap.unfav_star);

                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putIntArray(CURRENT_POSITION,
                new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("Current v", "Current View"+ savedInstanceState.getIntArray(CURRENT_POSITION));
        final int[] position = savedInstanceState.getIntArray(CURRENT_POSITION);
        if(position != null)
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(position[0], position[1]);
                }
            });
    }


    public void addToFavorite() {
        Log.v("addToFav", "Inside addToFavorite method");

        final Favorites favorite = new Favorites(movie.getMovieName(), movie.getMovieDescription(), movie.getPosterPath(), movie.getMovieId(), movie.getVoteAvg(), movie.getReleaseDate());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {


                mDb.favoritesDao().insetFav(favorite);
                retrievedFav = favorite;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            favoriteStarImage.setImageResource(R.mipmap.fav_star);
                    }
                });
            }
        });
    }


    public void removeFromFavorites() {
        Log.v("Deleting", "deleting the following favorites "+ retrievedFav.getMovieName());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoritesDao().deleteFav(retrievedFav);
                retrievedFav = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        favoriteStarImage.setImageResource(R.mipmap.unfav_star);
                    }
                });
            }
        });

    }


    public class FetchMovieExtraInformationTask extends AsyncTask<Movie, Integer, Movie> {

        @Override
        protected Movie doInBackground(Movie... movies) {
            if (movies.length == 0) {
                return null;
            }

            Movie movie = movies[0];
            String response = null;
            try {
                URL movieTrailerURL = NetworkUtilities.buildMoveExtraInformationPath(videos, String.valueOf(movie.getMovieId()));
                response = NetworkUtilities.getResponseFromHttpUrl(movieTrailerURL);
                List<Trailer> trailers = Utilities.getSelectedMovieTrailers(response);


                URL movieReviewURL = NetworkUtilities.buildMoveExtraInformationPath(reviews, String.valueOf(movie.getMovieId()));
                response = NetworkUtilities.getResponseFromHttpUrl(movieReviewURL);
                List<Review> reviews = Utilities.getSelectedMovieReviews(response);

                movie.setReviews(reviews);
                movie.setTrailers(trailers);
                Log.v("doInBackGround", "movie title " + movie.getMovieName());
                Log.v("doInBackGround", "Trailers size " + movie.getTrailers().size());
                Log.v("doInBackGround", "Trailers is " + movie.getTrailers().get(0).getTrailerTitle());


                Log.v("doInBackGround", "reviews size " + movie.getReviews().size());
                Log.v("doInBackGround", "reviews is " + movie.getReviews().get(0).getContent());

                return movie;
            } catch (Exception e) {
                Log.e("FetchMoviesDataTask", "Error while bulding connection " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie movie) {
            Log.i("FetchMovieExtra", "onPostExecute method");

            if (movie != null && (movie.getTrailers() != null || movie.getTrailers().size() > 0)) {
                  trailersListView.setAdapter(new TrailersAdapter(movie.getTrailers(),getApplicationContext()));
            } else {
                 trailersListView.setVisibility(View.INVISIBLE);
                trailersTextView.setVisibility(View.INVISIBLE);
                spaceView.setVisibility(View.INVISIBLE);
            }

            if (movie == null || (movie.getReviews() == null || movie.getReviews().size() < 0)) {
                reviewsButton.setVisibility(View.INVISIBLE);
            }
        }

    }
}

