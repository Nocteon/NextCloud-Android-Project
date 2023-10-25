package com.owncloud.android.ui.activity;
/*
    Nextcloud Android client application

    Copyright (C) 2023 Ralph Calixte for FIU senior project

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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.owncloud.android.R;
import com.owncloud.android.authentication.AuthenticatorActivity;

public class LoginThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_theme);
        Button b = findViewById(R.id.themeSwapper);
        b.setOnClickListener(new View.OnClickListener() { //Button 1 Dark mode
            @Override
            public void onClick(View view) {
                themeSwitcher();
            }
        });
        Button b2 = findViewById(R.id.themeSwapper2);
        b2.setOnClickListener(new View.OnClickListener() { //Button 2 Light mode
            @Override
            public void onClick(View view) {
                themeSwitcher2();
            }
        });
    }

    private void themeSwitcher(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    private void themeSwitcher2(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}