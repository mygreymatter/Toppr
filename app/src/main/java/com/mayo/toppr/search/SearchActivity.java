package com.mayo.toppr.search;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mayo.toppr.ItemClickSupport;
import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.Toppr;
import com.mayo.toppr.event.Event;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private ArrayList<Event> mResults;
    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mResults = new ArrayList<>();

        setToolbar();
        setResultsList();
    }

    private void setResultsList() {
        mAdapter = new SearchAdapter();
        mAdapter.setResults(mResults);

        RecyclerView resultsList = (RecyclerView) findViewById(R.id.listView);
        resultsList.setLayoutManager(new LinearLayoutManager(this));
        resultsList.setAdapter(mAdapter);

        ItemClickSupport.addTo(resultsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search");

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

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //do search with query

            Log.i(Tag.LOG, "Query: " + query);
        }

    }

    @Override
    public boolean onClose() {
        Log.i(Tag.LOG, "OnClose");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(Tag.LOG, "OnSubmit");
        return true;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        Log.i(Tag.LOG, "OnChange");

        mResults.clear();

        Event newEvent;
        for (Event event : Toppr.getInstance().events) {
            if (event.name.toLowerCase().contains(text.toLowerCase())) {
                newEvent = new Event();
                newEvent.id = event.id;
                newEvent.category = event.category;
                newEvent.description = event.description;
                newEvent.name = event.name;
                newEvent.image = event.image;
                newEvent.hasLiked = event.hasLiked;

                mResults.add(newEvent);
            }
        }

        mAdapter.setResults(mResults);

        return true;
    }
}
