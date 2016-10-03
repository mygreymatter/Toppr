package com.mayo.toppr.event;

import com.mayo.toppr.DBLayer;
import com.mayo.toppr.models.Event;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class EventDetailModel {

    Event getEvent(String id) {
        return DBLayer.getInstance().getEvent(id);
    }
}
