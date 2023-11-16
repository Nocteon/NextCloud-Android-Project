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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owncloud.android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.owncloud.android.ui.helpers.DatabaseHelper;


import com.owncloud.android.utils.CalendarEvent;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    FloatingActionButton addActivityButton;
    RecyclerView recyclerView;
    List<String> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_calendar);
        calendarView = findViewById(R.id.calendar_view);
        addActivityButton = findViewById(R.id.floatingActionButton);
        calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(CalendarActivity.this,  (month + 1) + "/" + day + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });

        addActivityButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(CalendarActivity.this, AddCalendarEventDialog.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        eventList = databaseHelper.getDisplayableEvents("11/25/2023");



        /*
        eventList.add("test 1");
        eventList.add("test 2");
        eventList.add("test 3");
        eventList.add("test 4");
        eventList.add("test 5");

         */

        CalendarAdapter adapter = new CalendarAdapter(eventList);
        recyclerView.setAdapter(adapter);



    }

}
