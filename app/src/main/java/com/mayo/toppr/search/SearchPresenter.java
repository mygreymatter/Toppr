package com.mayo.toppr.search;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 3/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class SearchPresenter
        implements SearchModel.OnSearchListener {

    private ISearchView mSearchView;
    private SearchModel mSearchModel;

    SearchPresenter(ISearchView view) {
        mSearchView = view;
        mSearchModel = new SearchModel(this);
    }

    void filterResults(String filterText) {
        mSearchModel.filterResults(filterText);
    }

    @Override
    public void onFinishedFiltering(ArrayList<Event> filteredResults) {
        mSearchView.setResults(filteredResults);
    }
}
