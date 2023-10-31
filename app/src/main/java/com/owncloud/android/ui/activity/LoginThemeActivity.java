package com.owncloud.android.ui.activity;
/*
    Nextcloud Android client application

    Copyright (C) 2023 Ralph Calixte for FIU senior project
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.owncloud.android.R;
import com.owncloud.android.authentication.AuthenticatorActivity;

public class LoginThemeActivity extends AppCompatActivity {
    static String selectedOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_theme);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                RadioButton radioButton = findViewById(checked);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();

                    if (selectedOption.equals("FIU Light")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    }
                    if (selectedOption.equals("FIU Dark")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }

                }

            }
        });

        /*
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("background", R.drawable.theme_login_background);
        editor.apply();

         */

        Button backButton = findViewById(R.id.backButton);
        // navigate back to previous activity
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(LoginThemeActivity.this, AuthenticatorActivity.class);
                startActivity(intent);
            }
        });

    }}

