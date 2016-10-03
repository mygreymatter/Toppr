package com.mayo.toppr.favourite;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

interface IFavouriteView {
    void showEventDetails(String id);

    void setFavouriteStatus(boolean isFavouritesEmpty);

    void showFavourites(ArrayList<Event> favouriteEvents);
}
