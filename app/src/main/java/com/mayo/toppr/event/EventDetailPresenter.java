package com.mayo.toppr.event;

import com.mayo.toppr.models.Event;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class EventDetailPresenter {
    private IEventDetailView mEventDetailView;
    private EventDetailModel mEventDetailModel;
    private Event event;

    EventDetailPresenter(IEventDetailView view) {
        mEventDetailView = view;
        mEventDetailModel = new EventDetailModel();
    }

    /**
     * Set the event details to the View
     *
     * @param id the id of the event
     */
    void setEvent(String id) {
        event = mEventDetailModel.getEvent(id);

        if (null != event) {
            mEventDetailView.setEventTitle(event.name);
            mEventDetailView.setEventCategory(event.category);
            mEventDetailView.setEventDetailImage(event.image);
            mEventDetailView.setEventFavourite(event.hasLiked);
        }
    }

    /**
     * Toggle favourite of the event
     */
    void toggleEventFavourite() {
        event.hasLiked = !event.hasLiked;
        mEventDetailView.setEventFavourite(event.hasLiked);
    }

}
