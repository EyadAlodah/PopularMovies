package com.popularmovies.eyad.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends ArrayAdapter<Trailer> {


  Context context;
  List<Trailer> trailerArrayList;
  public TrailersAdapter(List<Trailer> data, Context context) {
    super(context, R.layout.trailers_rows_layout, data);
    this.trailerArrayList = data;
    this.context = context;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    String method = "getView";
    Log.v(method, "inside the custome adabter for trailes");
    View view = convertView;

    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      view = inflater.inflate(R.layout.trailers_rows_layout, null);

    }

    final Trailer trailer = getItem(position);

    if (trailer != null) {
      Log.v(method, "before view");

      TextView name = view.findViewById(R.id.trailersTextView);
      ImageView image = view.findViewById(R.id.playImage);
      name.setText(context.getText(R.string.trailer)+" "+ (position+1));

      image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getTrailerKey()));
          Intent webIntent = new Intent(Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=" + trailer.getTrailerKey()));

          appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          try {
            context.startActivity(appIntent);
          } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
          }
        }
      });

    }

    return view;


  }

}
