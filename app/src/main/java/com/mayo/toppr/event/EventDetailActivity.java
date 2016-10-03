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

public class EventDetailActivity extends AppCompatActivity
        implements IEventDetailView {

    private ImageView mEventImage;
    private TextView mEventCategory;
    private ImageView mEventFav;
    private EventDetailPresenter mEventDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        mEventFav = (ImageView) findViewById(R.id.event_favourite);
        mEventCategory = (TextView) findViewById(R.id.event_category);
        mEventImage = (ImageView) findViewById(R.id.event_image);
        setToolbar();

        mEventDetailPresenter = new EventDetailPresenter(this);
        mEventDetailPresenter.setEvent(getIntent().getStringExtra(Tag.ID));
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

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
    }

    public void changeFavourite(View v) {
        mEventDetailPresenter.toggleEventFavourite();
    }

    @Override
    public void setEventTitle(String eventTitle) {
        //set collapsibletoolbar
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(eventTitle);
    }

    @Override
    public void setEventCategory(String categoryName) {
        mEventCategory.setText(categoryName);
    }

    @Override
    public void setEventDetailImage(String imageURL) {

        Glide.with(this)
                .load(imageURL)
                .into(mEventImage);
    }

    @Override
    public void setEventFavourite(final boolean isFavourite) {
        Glide.with(this)
                .load(isFavourite ? R.drawable.ic_favourite_selected : R.drawable.ic_favourite_unselected)
                .into(mEventFav);
    }
}
