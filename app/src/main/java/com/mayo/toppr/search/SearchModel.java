package com.mayo.toppr.search;

import com.mayo.toppr.DBLayer;
import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 3/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class SearchModel {
    private SearchPresenter mSearchPresenter;


    SearchModel(SearchPresenter presenter) {
        mSearchPresenter = presenter;
    }

    void filterResults(String text) {
        ArrayList<Event> results = new ArrayList<>();
        Event newEvent;

        for (Event event : DBLayer.getInstance().events) {
            if (event.name.toLowerCase().contains(text.toLowerCase())) {
                newEvent = new Event();
                newEvent.id = event.id;
                newEvent.category = event.category;
                newEvent.description = event.description;
                newEvent.name = event.name;
                newEvent.image = event.image;
                newEvent.hasLiked = event.hasLiked;

                results.add(newEvent);
            }
        }

        mSearchPresenter.onFinishedFiltering(results);
    }

    interface OnSearchListener {
        void onFinishedFiltering(ArrayList<Event> filteredResults);
    }
}
