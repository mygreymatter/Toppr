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

import com.mayo.toppr.ItemClickSupport;
import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.Toppr;
import com.mayo.toppr.event.Event;
import com.mayo.toppr.event.EventsAdapter;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private static final String TAG = FavouritesActivity.class.getName();
    private ArrayList<Event> mFavaouriteEvents;
    private EventsAdapter mAdapter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Tag.ACTION_UPDATE_FAVOURITES.equals(action)) {
                prepareFavouriteList();
                mAdapter.setEvents(mFavaouriteEvents);
                findViewById(R.id.no_favourite_events).setVisibility(mFavaouriteEvents.size() > 0 ? View.INVISIBLE : View.VISIBLE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mFavaouriteEvents = new ArrayList<>();

        setToolbar();
        setFavouriteEventsList();
        prepareFavouriteList();

        mAdapter.setEvents(mFavaouriteEvents);

        findViewById(R.id.no_favourite_events).setVisibility(mFavaouriteEvents.size() > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void prepareFavouriteList() {
        mFavaouriteEvents.clear();

        for (Event e : Toppr.getInstance().events) {
            if (e.hasLiked) {
                mFavaouriteEvents.add(e);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        mAdapter = new EventsAdapter(true);

        RecyclerView eventsList = (RecyclerView) findViewById(R.id.favourite_events_list);
        eventsList.setLayoutManager(new LinearLayoutManager(this));
        eventsList.setAdapter(mAdapter);

        ItemClickSupport.addTo(eventsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i(TAG, "Item Clicked!");
            }
        });
    }

    private IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Tag.ACTION_UPDATE_FAVOURITES);

        return filter;
    }
}
