package com.mayo.toppr.home;

import android.util.Log;

import com.android.volley.Response;
import com.mayo.toppr.DBLayer;
import com.mayo.toppr.Tag;
import com.mayo.toppr.Toppr;
import com.mayo.toppr.URL;
import com.mayo.toppr.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Mahayogi Lakshmipathi on 2/10/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

class MainModel {
    private onModelListener mListener;

    MainModel(MainPresenter mainPresenter) {
        mListener = mainPresenter;
    }

    /**
     * Fetch events from the Server.
     */
    void fetchEvents() {
        Toppr.getInstance().getJSONObject(URL.GET_EVENTS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(Tag.LOG, "Response: " + response);
                        try {
                            parseResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void parseResponse(JSONObject response) throws JSONException {
        JSONArray websites = response.getJSONArray(Tag.WEBSITES);
        JSONObject website;
        Event event;

        DBLayer.getInstance().events.clear();
        for (int index = 0; index < websites.length(); index++) {
            website = websites.getJSONObject(index);
            event = new Event();

            event.id = website.getString(Tag.ID);
            event.name = website.getString(Tag.NAME);
            event.image = website.getString(Tag.IMAGE);
            event.category = website.getString(Tag.CATEGORY);
            event.description = website.getString(Tag.DESCRIPTION);
            event.experience = website.getString(Tag.EXPERIENCE);

            DBLayer.getInstance().events.add(event);
        }

        Log.i(Tag.LOG, "Events: " + DBLayer.getInstance().events.size());

        mListener.onFinishedLoading(DBLayer.getInstance().events);
    }

    /**
     * Retrieve events from the local DB.
     */
    void retireveEvents() {
        mListener.onFinishedLoading(DBLayer.getInstance().events);
    }

    String getEventId(int pos) {
        return DBLayer.getInstance().events.get(pos).id;
    }

    interface onModelListener {
        void onFinishedLoading(ArrayList<Event> events);
    }
}
