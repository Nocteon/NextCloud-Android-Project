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


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.owncloud.android.R;
import com.owncloud.android.ui.helpers.DatabaseHelper;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteModifyEvent extends AppCompatActivity {

    private TextInputEditText editTextDesc; //mandatory
    private TextInputEditText editTextDate; //mandatory
    private TextInputEditText editTextTime; //mandatory
    private TextInputEditText editTextLoc; //optional

    private Button saveChangesButton;
    private Button cancelButton;

    private String description = "";
    private String dateTime = "";
    private String location = "";
    private int key;
    private String[] dateAndTime = new String[2];
    private FloatingActionButton timePickerButton;
    DateTimeHelper dth;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ImageButton deleteImageButton;



    protected void onCreate (Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.modify_calendar_event);

        dth = new DateTimeHelper();

        Intent intent = getIntent();

        if (intent.hasExtra("eventDetails")){
            String [] pressedEventDetails = intent.getStringArrayExtra("eventDetails");

            if (pressedEventDetails.length == 4){
                description = pressedEventDetails[0];
                dateTime = pressedEventDetails[1];
                location = pressedEventDetails[2];
                key = Integer.parseInt(pressedEventDetails[3]);

                dateAndTime = dateTime.split(" ");

            }

            editTextDesc = findViewById(R.id.editTextDesc);
            editTextDate = findViewById(R.id.editTextDate);
            editTextTime = findViewById(R.id.editTextTime);
            editTextLoc = findViewById(R.id.editTextLocation);

            editTextDesc.setText(description);
            editTextDate.setText(dateAndTime[0]);
            editTextTime.setText(dateAndTime[1]);
            editTextLoc.setText(location);
        }

        //date validation
        editTextDate = findViewById(R.id.editTextDate);
        dth.validateDate(editTextDate);

        //time picker
        editTextTime = findViewById(R.id.editTextTime);
        timePickerButton = findViewById(R.id.timePickerButton);

        dth.displayTimePicker(editTextTime, timePickerButton, DeleteModifyEvent.this);

        //cancel button
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //saveChangesButton
        saveChangesButton = findViewById(R.id.saveChangesButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first, verify that the fields were filled
                if ((editTextDesc.getText().toString().isEmpty()) || (editTextDate.getText().toString().isEmpty()) || (editTextTime.getText().toString().isEmpty())){
                    dth.toastForMissingMandatoryFields(DeleteModifyEvent.this);
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
                    databaseHelper.modifyEvent(key, description, dateAndTime, location, displayableEvent);
                    editTextDesc.setText(null);
                    editTextDate.setText(null);
                    editTextTime.setText(null);
                    editTextLoc.setText(null);
                    Intent intent = new Intent(DeleteModifyEvent.this, CalendarActivity.class);
                    startActivity(intent);
                }
            }
        });

        //delete button
        deleteImageButton = findViewById(R.id.deleteImageButton);
        deleteImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteModifyEvent.this, R.style.FIUTimePickerDialogTheme);
                builder.setTitle("Alert!").setMessage("Delete Event?").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.deleteEvent(key);
                            dialogInterface.dismiss();
                            Intent intent = new Intent(DeleteModifyEvent.this, CalendarActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
});
    }
}
