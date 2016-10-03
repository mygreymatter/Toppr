package com.mayo.toppr.favourite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.customview.ItemClickSupport;
import com.mayo.toppr.event.EventDetailActivity;
import com.mayo.toppr.models.Event;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity
        implements IFavouriteView {

    private FavouritePresenter mFavouritePresenter;

    private FavouriteEventsAdapter mAdapter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Tag.ACTION_UPDATE_FAVOURITES.equals(action)) {
                mFavouritePresenter.prepareFavouriteEvents();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mAdapter = new FavouriteEventsAdapter();
        setToolbar();
        setFavouriteEventsList();

        mFavouritePresenter = new FavouritePresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mFavouritePresenter.prepareFavouriteEvents();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver, getIntentFilter());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);

        super.onPause();
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Favourites");

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

    private void setFavouriteEventsList() {
        RecyclerView eventsList = (RecyclerView) findViewById(R.id.favourite_events_list);
        eventsList.setLayoutManager(new LinearLayoutManager(this));
        eventsList.setAdapter(mAdapter);

        ItemClickSupport.addTo(eventsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i(Tag.LOG, "Item Clicked!");
                mFavouritePresenter.showEventDetails(position);
            }
        });
    }

    private IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Tag.ACTION_UPDATE_FAVOURITES);

        return filter;
    }

    @Override
    public void showEventDetails(String id) {
        Intent i = new Intent(FavouritesActivity.this, EventDetailActivity.class);
        i.putExtra(Tag.ID, id);

        startActivity(i);
    }

    @Override
    public void setFavouriteStatus(boolean favouriteStatus) {
        findViewById(R.id.no_favourite_events).setVisibility(favouriteStatus ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void showFavourites(ArrayList<Event> favouriteEvents) {
        mAdapter.setEvents(favouriteEvents);
    }
}
