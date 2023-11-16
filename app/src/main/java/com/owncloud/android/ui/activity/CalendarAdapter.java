package com.owncloud.android.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.owncloud.android.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarEventViewHolder> {
    private List<String> eventList;

    public CalendarAdapter(List<String> eventList) {
        this.eventList = eventList;
    }
    @NonNull
    @Override
    public CalendarEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_recycler_list_item, parent, false);
        return new CalendarEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventViewHolder holder, int position) {
        String eventItem = eventList.get(position);
        holder.textViewItem.setText(eventItem);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
