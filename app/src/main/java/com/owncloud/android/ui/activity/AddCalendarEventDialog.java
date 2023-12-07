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

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.owncloud.android.R;
import com.owncloud.android.ui.helpers.DatabaseHelper;
import com.owncloud.android.utils.CalendarEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class AddCalendarEventDialog extends AppCompatActivity {

    private TextInputEditText editTextDesc; //mandatory
    private TextInputEditText editTextDate; //mandatory
    private TextInputEditText editTextTime; //mandatory
    private TextInputEditText editTextLoc; //optional
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private FloatingActionButton timePickerButton;
    private Calendar calendar;
    private Button addButton;
    private Button cancelButton;
    public static ArrayList<CalendarEvent>  eventsList = new ArrayList<>();
    //instance of DatabaseHelper
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    String userId;
    UserInfoForCalendarActivity uic;
    DateTimeHelper dth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_calendar_event);

        dth = new DateTimeHelper();

        //Date Validation
        editTextDate = findViewById(R.id.editTextDate);
        dth.validateDate(editTextDate);


        //Time Picker
        editTextTime = findViewById(R.id.editTextTime);
        timePickerButton = findViewById(R.id.timePickerButton);

        dth.displayTimePicker(editTextTime, timePickerButton, AddCalendarEventDialog.this);

        //Add button
        addButton = findViewById(R.id.addButton);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextLoc = findViewById(R.id.editTextLocation);

        String message = com.owncloud.android.operations.RefreshFolderOperation.getMessage();

        uic = new UserInfoForCalendarActivity(message);
        userId = uic.getIdFromMessage(message);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first, verify that the fields were filled
                if ((editTextDesc.getText().toString().isEmpty()) || (editTextDate.getText().toString().isEmpty()) || (editTextTime.getText().toString().isEmpty())){
                    dth.toastForMissingMandatoryFields(AddCalendarEventDialog.this);
                }
                else {
                    String description = editTextDesc.getText().toString();
                    String dateAndTime = editTextDate.getText().toString() + " " + editTextTime.getText().toString();
                    String location = editTextLoc.getText().toString();
                    if (location.isEmpty()){
                        location = "no location provided";
                    }
                    String displayableEvent = "Event: " + description + " - " + editTextDate.getText().toString() + " at " + editTextTime.getText().toString()
                        + " - Location: " + location;
                    databaseHelper.saveEventDetails(description, dateAndTime, location, displayableEvent, userId);
                    editTextDesc.setText(null);
                    editTextDate.setText(null);
                    editTextTime.setText(null);
                    editTextLoc.setText(null);
                    Intent intent = new Intent(AddCalendarEventDialog.this, CalendarActivity.class);
                    startActivity(intent);
                }
            }
        });

        //cancel button
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
