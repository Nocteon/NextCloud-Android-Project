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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.owncloud.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class DateTimeHelper{

    private TextInputEditText editTextDesc;
    private TextInputEditText editTextDate;
    private TextInputEditText editTextTime;
    private TextInputEditText editTextLoc;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private FloatingActionButton timePickerButton;
    private Calendar calendar;

    // add text watcher to the editable text field
    public void validateDate (TextInputEditText editTextDate) {
        editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Perform date validation after text changes
                boolean isAMatch = validateDateInput(editable.toString());
                if (isAMatch) {
                    //clear any previous errors
                    editTextDate.setError(null);
                } else {
                    //display error message
                    editTextDate.setError("Invalid format.\nPlease, use mm/dd/yyyy");
                }
            }
        }); }

        // method to validate the date entered by the user
        public boolean validateDateInput(String dateInput) {
            String regexDate = "^(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])/(20)\\d\\d$";
            Pattern pattern = Pattern.compile(regexDate);

            if (dateInput != null) {
                //Check if the input string is in the allowed format
                Matcher matcher = pattern.matcher(dateInput);

                boolean isAMatch = matcher.find();
            }
            Matcher matcher = pattern.matcher(dateInput);

            boolean isAMatch = matcher.find();
            return isAMatch;
        }

        // display time picker
    public void displayTimePicker(TextInputEditText editTextTime, FloatingActionButton timePickerButton, Context classContext) {
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(classContext, R.style.FIUTimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        //make sure that the time have the right format
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        editTextTime.setText(selectedTime);
                    }
                }, hour, min, false);
                timePickerDialog.show();
            }
        });
    }

    public void toastForMissingMandatoryFields(Context classContext){
        Toast toast = new Toast(classContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText("Description, date and time are mandatory fields");
        toast.show();
    }

    }

