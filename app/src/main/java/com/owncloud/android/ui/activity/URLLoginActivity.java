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



package com.owncloud.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;

import com.owncloud.android.R;

import androidx.appcompat.app.AppCompatActivity;

public class URLLoginActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setup);

        ScrollView scrollView = findViewById(R.id.scroll);
        //scrollView.setBackground(getDrawable(R.drawable.theme_login_background));

        /*


        if (selectedOption != null){
            if (selectedOption.equals("FIU Light")){
                scrollView.setBackground(getDrawable(R.drawable.theme_login_background));
            }
            if (selectedOption.equals("FIU Dark")){
                scrollView.setBackground(getDrawable(R.drawable.theme_login_background));
                //scrollView.setBackgroundResource(R.drawable.theme_login_background);
            }
        }

         */
    }


}
