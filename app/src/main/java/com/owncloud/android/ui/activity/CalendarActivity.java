package com.owncloud.android.ui.activity;

/*
    Nextcloud Android client application

    Copyright (C) 2023 Chabeli Castano for FIU senior project

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU AFFERO GENERAL PUBLIC LICENSE
    License as published by the Free Software Foundation; either
    version 3 of the License, or any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU AFFERO GENERAL PUBLIC LICENSE for more details.

    You should have received a copy of the GNU Affero General Public
    License along with this program.  If not, see http://www.gnu.org/licenses/

 */


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owncloud.android.R;
import com.owncloud.android.ui.helpers.DatabaseHelper;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    FloatingActionButton addActivityButton;
    RecyclerView recyclerView;
    List<String> eventList;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_calendar);
        calendarView = findViewById(R.id.calendar_view);

        addActivityButton = findViewById(R.id.floatingActionButton);

        calendar = Calendar.getInstance();

        //set up the recyclerView to display calendar events
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        //when the user presses a date, show the corresponding events
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDate = String.valueOf(month + 1) + "/" + String.valueOf(day) + "/" + String.valueOf(year);

                eventList = databaseHelper.getDisplayableEvents(selectedDate);

                CalendarAdapter adapter = new CalendarAdapter(eventList);
                recyclerView.setAdapter(adapter);
            }
        });

        // Add Event Button
        addActivityButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(CalendarActivity.this, AddCalendarEventDialog.class);
                startActivity(intent);
            }
        });

    }

}
