/*
* Copyright (C) 2015 HansooLabs.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.hansoolabs.calendarproto;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hansoolabs.calendarproto.cal.OneDayView;

import java.util.Calendar;


public class MainActivity extends FragmentActivity {

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "MainActivity";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

    private TextView thisMonthTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button addButton = findViewById(R.id.main_add_bt);
//        Button monthButton = findViewById(R.id.main_monthly_bt);
//        Button weekButton = findViewById(R.id.main_weekly_bt);
//        Button dayButton = findViewById(R.id.main_daily_bt);
        thisMonthTv = findViewById(R.id.this_month_tv);
        
        MonthlyFragment mf = (MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.monthly);
        mf.setOnMonthChangeListener(new MonthlyFragment.OnMonthChangeListener() {
            
            @Override
            public void onChange(int year, int month) {
                HLog.d(TAG, CLASS, "onChange " + year + "." + month);
                thisMonthTv.setText(year + "." + (month + 1));
            }

            @Override
            public void onDayClick(OneDayView dayView) {
                Toast.makeText(MainActivity.this, "Click  " + dayView.get(Calendar.MONTH) + "/" + dayView.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT)
                    .show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    
}
