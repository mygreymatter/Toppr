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

import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.customview.ItemClickSupport;
import com.mayo.toppr.models.Event;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,
        ISearchView {

    private SearchAdapter mAdapter;
    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mAdapter = new SearchAdapter();

        setToolbar();
        setResultsList();
        mSearchPresenter = new SearchPresenter(this);
    }


    @Override
    public void setResults(ArrayList<Event> results) {
        mAdapter.setResults(results);
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
        mSearchPresenter.filterResults(text);
        return true;
    }


    private void setResultsList() {
        RecyclerView resultsList = (RecyclerView) findViewById(R.id.listView);
        resultsList.setLayoutManager(new LinearLayoutManager(this));
        resultsList.setAdapter(mAdapter);

        ItemClickSupport.addTo(resultsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }
}
