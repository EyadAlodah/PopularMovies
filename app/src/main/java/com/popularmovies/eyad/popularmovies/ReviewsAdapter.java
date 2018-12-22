package com.popularmovies.eyad.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewsAdapter extends ArrayAdapter<Review> {

  public Context context;
  public List<Review>reviews;

  public ReviewsAdapter(Context c , List<Review>data)
  {
    super(c, R.layout.reviews_rows_layout, data);
    this.reviews = data;
    this.context = context;

  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    String method = "getView";
    Log.v(method, "inside the custome adabter for Reviews");
    View view = convertView;

    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      view = inflater.inflate(R.layout.reviews_rows_layout, null);

    }

    Review review = getItem(position);

    if (review != null) {
      Log.v(method, "before view");
      TextView author = view.findViewById(R.id.authrName);
      TextView comment = view.findViewById(R.id.comment);

      if(review.getAuthor()!= null)
      author.setText(review.getAuthor());
      if(review.getContent()!= null)
      comment.setText(review.getContent());
    }

    return view;


  }
}
