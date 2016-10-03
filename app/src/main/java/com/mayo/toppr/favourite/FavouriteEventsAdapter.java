package com.mayo.toppr.favourite;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayo.toppr.R;
import com.mayo.toppr.Tag;
import com.mayo.toppr.models.Event;

import java.util.ArrayList;

/**
 * Created by Mahayogi Lakshmipathi on 25/9/16.
 *
 * @author <a href="mailto:mygreymatter@gmail.com">Mahayogi Lakshmipathi</a>
 * @version 1.0
 */

public class FavouriteEventsAdapter extends RecyclerView.Adapter<FavouriteEventsAdapter.EventVH> {
    private static final String TAG = FavouriteEventsAdapter.class.getName();

    private ArrayList<Event> events;
    private Context context;
    private LayoutInflater inflater;

    @Override
    public EventVH onCreateViewHolder(ViewGroup parent, int viewType) {

        if (null == context) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }

        return new EventVH(inflater.inflate(R.layout.list_item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(final EventVH holder, int position) {
        final Event event = events.get(position);

        Glide.with(context)
                .load(event.image)
                .into(holder.image);

        holder.name.setText(event.name);
        holder.category.setText(event.category);
        setFavouriteIcon(event, holder.favourite);

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, " Favourite clicked");
                event.hasLiked = !event.hasLiked;
                setFavouriteIcon(event, holder.favourite);

                sendBroadcast();
            }
        });
    }

    private void setFavouriteIcon(final Event event, final ImageView v) {
        if (event.hasLiked) {
            Glide.with(context)
                    .load(R.drawable.ic_favourite_selected)
                    .into(v);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_favourite_unselected)
                    .into(v);
        }
    }

    private void sendBroadcast() {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(Tag.ACTION_UPDATE_FAVOURITES));
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    static class EventVH extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView favourite;
        TextView name;
        TextView category;

        EventVH(View v) {
            super(v);

            image = (ImageView) v.findViewById(R.id.event_image);
            favourite = (ImageView) v.findViewById(R.id.event_favourite);
            name = (TextView) v.findViewById(R.id.event_name);
            category = (TextView) v.findViewById(R.id.event_category);


        }
    }
}
