package com.owncloud.android.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.owncloud.android.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarEventViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewItem;

    public CalendarEventViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewItem = itemView.findViewById(R.id.textViewItem);
    }
}
