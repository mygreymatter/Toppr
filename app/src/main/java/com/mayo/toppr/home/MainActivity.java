package com.mayo.toppr.home;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.customview.ItemClickSupport;
import com.mayo.toppr.event.EventDetailActivity;
import com.mayo.toppr.event.EventsAdapter;
import com.mayo.toppr.favourite.FavouritesActivity;
import com.mayo.toppr.models.Event;
import com.mayo.toppr.search.SearchActivity;
import com.mayo.toppr.statistic.StatisticActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainView {

    private EventsAdapter mAdapter;
    private ProgressBar mProgress;
    private TextView mProgressStatus;

    private MainPresenter mainPresenter;
    private boolean hasPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new EventsAdapter();

        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgressStatus = (TextView) findViewById(R.id.progress_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainPresenter = new MainPresenter(this);

        if (savedInstanceState == null) {
            mainPresenter.fetchEvents();
        } else {
            //to handle configuration changes
            mainPresenter.retrieveEvents();
        }

        setEventsList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasPaused) {
            hasPaused = false;
            mainPresenter.retrieveEvents();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        hasPaused = true;
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_favourite:
                mainPresenter.onFavouritesClicked();
                break;
            case R.id.nav_statistics:
                mainPresenter.onStatisticsClicked();
                break;
            default:
                break;
        }
        //return false to keep the menu item in the navigation bar unselected
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {
        //to handle configuration changes
        savedInstance.putBoolean(Tag.HAS_SAVED, true);
        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mProgressStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgress() {
        mProgress.setVisibility(View.INVISIBLE);
        mProgressStatus.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEventDetails(String id) {
        Intent i = new Intent(MainActivity.this, EventDetailActivity.class);
        i.putExtra(Tag.ID, id);

        startActivity(i);
    }

    @Override
    public void showFavourites() {
        startActivity(new Intent(this, FavouritesActivity.class));
    }

    @Override
    public void showStatistics() {
        startActivity(new Intent(this, StatisticActivity.class));
    }

    @Override
    public void showEventsList(ArrayList<Event> events) {
        mAdapter.setEvents(events);
    }

    private void setEventsList() {
        RecyclerView eventsList = (RecyclerView) findViewById(R.id.events_list);
        eventsList.setLayoutManager(new LinearLayoutManager(this));
        eventsList.setAdapter(mAdapter);

        ItemClickSupport.addTo(eventsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mainPresenter.onEventClicked(position);
            }
        });
    }
}
