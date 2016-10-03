package com.mayo.toppr.favourite;

import com.mayo.toppr.DBLayer;
import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class FavouriteModel {
    private FavouritePresenter mFavouritePresenter;
    private ArrayList<Event> mEvents;

    FavouriteModel(FavouritePresenter favouritePresenter) {
        mFavouritePresenter = favouritePresenter;
        mEvents = new ArrayList<>();
    }

    void prepareFavouriteList() {
        mEvents.clear();
        for (Event event : DBLayer.getInstance().events) {
            if (event.hasLiked) {
                mEvents.add(event);
            }
        }
        //notify the favourite list prepared
        mFavouritePresenter.onFinishedFavouriteList(mEvents);
    }

    String getEventID(int pos) {
        return mEvents.get(pos).id;
    }

    interface OnFavouriteModelListener {
        void onFinishedFavouriteList(ArrayList<Event> favouriteList);
    }
}
