package com.mayo.toppr.event;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

interface EventDetailView {
    void setEventTitle(String title);

    void setEventCategory(String category);

    void setEventDetailImage(String imageURL);

    void setEventFavourite(boolean isFavourite);
}
