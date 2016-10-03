package com.mayo.toppr.home;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 1/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class MainPresenter implements MainModel.onModelListener {
    private IMainView mMainView;
    private MainModel mMainModel;

    MainPresenter(IMainView view) {
        mMainView = view;
        mMainModel = new MainModel(this);
        mMainView.showProgress();
    }

    /**
     * Fetch the events from the network
     */
    void fetchEvents() {
        mMainModel.fetchEvents();
    }

    /**
     * Retrieve events from the local db
     */
    void retrieveEvents() {
        mMainModel.retireveEvents();
    }

    void onEventClicked(int pos) {
        mMainView.showEventDetails(mMainModel.getEventId(pos));
    }

    void onFavouritesClicked() {
        mMainView.showFavourites();
    }

    void onStatisticsClicked() {
        mMainView.showStatistics();
    }

    @Override
    public void onFinishedLoading(ArrayList<Event> events) {
        mMainView.showEventsList(events);
        mMainView.stopProgress();
    }
}
