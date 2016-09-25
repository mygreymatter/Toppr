package com.mayo.toppr;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 25/9/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

public class Toppr extends Application {
    private static final String TAG = Toppr.class.getName();
    private static Toppr instance = null;
    public ArrayList<Event> events;
    private RequestQueue mRequestQueue;

    public static Toppr getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        mRequestQueue = Volley.newRequestQueue(this);
        events = new ArrayList<>();
    }

    public void getJSONObject(String url, Response.Listener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "ErrorResponse: " + error);
            }
        });
        mRequestQueue.add(request);
    }

}
