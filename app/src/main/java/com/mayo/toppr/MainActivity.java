package com.mayo.toppr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.mayo.toppr.event.Event;
import com.mayo.toppr.event.EventsAdapter;
import com.mayo.toppr.favourite.FavouritesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    private EventsAdapter mAdapter;
    private ProgressBar mProgress;
    private TextView mProgressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgressStatus = (TextView) findViewById(R.id.progress_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setEventsList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favourite) {
            startActivity(new Intent(this, FavouritesActivity.class));
        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toppr.getInstance().getJSONObject(URL.GET_EVENTS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Response: " + response);
                        try {
                            parseResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void parseResponse(JSONObject response) throws JSONException {
        JSONArray websites = response.getJSONArray(Tag.WEBSITES);
        JSONObject website;
        Event event;

        Toppr.getInstance().events.clear();
        for (int index = 0; index < websites.length(); index++) {
            website = websites.getJSONObject(index);
            event = new Event();

            event.id = website.getString(Tag.ID);
            event.name = website.getString(Tag.NAME);
            event.image = website.getString(Tag.IMAGE);
            event.category = website.getString(Tag.CATEGORY);
            event.description = website.getString(Tag.DESCRIPTION);
            event.experience = website.getString(Tag.EXPERIENCE);

            Toppr.getInstance().events.add(event);
        }

        Log.i(TAG, "Events: " + Toppr.getInstance().events.size());

        mProgress.setVisibility(View.INVISIBLE);
        mProgressStatus.setVisibility(View.INVISIBLE);
        mAdapter.setEvents(Toppr.getInstance().events);
    }

    private void setEventsList() {
        mAdapter = new EventsAdapter(false);

        RecyclerView eventsList = (RecyclerView) findViewById(R.id.events_list);
        eventsList.setLayoutManager(new LinearLayoutManager(this));
        eventsList.setAdapter(mAdapter);

        /*ItemClickSupport.addTo(eventsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i(TAG, "Item Clicked!");
            }
        });*/
    }
}
