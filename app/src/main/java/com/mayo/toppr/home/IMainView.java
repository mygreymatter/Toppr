package com.mayo.toppr.home;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 1/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

interface IMainView {
    void showProgress();

    void stopProgress();

    void showEventDetails(String id);

    void showFavourites();

    void showStatistics();

    void showEventsList(ArrayList<Event> events);
}
