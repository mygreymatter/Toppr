package com.mayo.toppr.favourite;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class FavouritePresenter implements FavouriteModel.OnFavouriteModelListener {

    private IFavouriteView mFavouriteView;
    private FavouriteModel mFavouriteModel;

    FavouritePresenter(IFavouriteView favouriteView) {
        mFavouriteView = favouriteView;
        mFavouriteModel = new FavouriteModel(this);
    }

    @Override
    public void onFinishedFavouriteList(ArrayList<Event> favouriteList) {
        mFavouriteView.setFavouriteStatus(favouriteList.size() > 0);
        mFavouriteView.showFavourites(favouriteList);
    }

    void prepareFavouriteEvents() {
        mFavouriteModel.prepareFavouriteList();
    }

    void showEventDetails(int position) {
        mFavouriteView.showEventDetails(mFavouriteModel.getEventID(position));
    }
}
