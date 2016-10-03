package com.mayo.toppr;

import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */
public class DBLayer {
    private static DBLayer ourInstance = new DBLayer();
    public ArrayList<Event> events = new ArrayList<>();

    private DBLayer() {
    }

    public static DBLayer getInstance() {
        return ourInstance;
    }

    public Event getEvent(String id) {

        for (Event event : events) {
            if (id.equals(event.id)) {
                return event;
            }
        }

        return null;
    }

    public String getEventId(int pos) {
        return events.get(pos).id;
    }
}


