package com.mayo.toppr.event;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.Toppr;

public class EventDetailActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        event = Toppr.getInstance().events.get(getIntent().getIntExtra(Tag.POSITION, -1));
        setToolbar();
        setImage();
        setFavourite();
        setCategory();
    }

    private void setCategory() {
        TextView category = (TextView) findViewById(R.id.event_category);
        category.setText(event.category);
    }

    private void setImage() {
        ImageView v = (ImageView) findViewById(R.id.event_image);
        Glide.with(this)
                .load(event.image)
                .into(v);
    }

    private void setFavourite() {
        final ImageView fav = (ImageView) findViewById(R.id.event_favourite);

        Glide.with(this)
                .load(event.hasLiked ? R.drawable.ic_favourite_selected : R.drawable.ic_favourite_unselected)
                .into(fav);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.hasLiked = !event.hasLiked;
                Glide.with(EventDetailActivity.this)
                        .load(event.hasLiked ? R.drawable.ic_favourite_selected : R.drawable.ic_favourite_unselected)
                        .into(fav);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        /*final TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Bots Challenge!");*/

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //reduces the gap between navigation icon and the title in the toolbar
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //set collapsibletoolbar
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(event.name);

    }

}
