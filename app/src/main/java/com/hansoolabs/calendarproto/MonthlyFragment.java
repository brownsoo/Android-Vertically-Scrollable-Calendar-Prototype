/*
* Copyright (C) 2018 HansooLabs.
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

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hansoolabs.calendarproto.cal.OneDayView;

import java.util.Calendar;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;



/**
 * Fragment for displaying the monthly-calendar
 * @author brownsoo
 *
 */
public class MonthlyFragment extends Fragment implements MonthlyView {

    private static final String TAG = MConfig.TAG;
    private final String klass = "MonthlyFragment@" + Integer.toHexString(hashCode());
    
    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";

    /**
     * Callback when current month is changed
     * @author Brownsoo
     *
     */
    public interface MonthlyFragmentListener {
        /**
         * Notify current month is changed
         * @param year 4 digits number of year
         * @param month number of month (0~11)
         */
        void onChange(int year, int month);

        /**
         * Callback for clicking on a day cell
         * @param dayView OneDayView instance that dispatching this callback
         */
        void onDayClick(OneDayView dayView);
    }

    @Nullable
    private MonthlyFragmentListener listener = null;
    // Vertical View Pager -- ref: https://github.com/LittlePanpc/VerticalViewPager-1
    private VerticalViewPager vvPager;
    private MonthlyPagerAdapter adapter;
    private int mYear = -1;
    private int mMonth = -1;

    /**
     * Make new month view via given year and month
     * @param year YYYY
     * @param month m
     * @return new fragment
     */
    public static MonthlyFragment newInstance(int year, int month) {
        MonthlyFragment fragment = new MonthlyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }
    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mYear = getArguments().getInt(ARG_YEAR);
            mMonth = getArguments().getInt(ARG_MONTH);
        }
        else {
            Calendar now = Calendar.getInstance();
            mYear = now.get(Calendar.YEAR);
            mMonth = now.get(Calendar.MONTH);
        }

        HLog.d(TAG, klass, "onCreate " + mYear + "." + mMonth);
        
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new MonthlyPagerAdapter(context, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_monthly, container, false);
        vvPager = v.findViewById(R.id.vviewPager);
        vvPager.setAdapter(adapter);
        vvPager.setOnPageChangeListener(adapter);
        vvPager.setCurrentItem(adapter.getPosition(mYear, mMonth));
        vvPager.setOffscreenPageLimit(1);
        
        return v;
    }
    
    @Override
    public void onDetach() {
        setOnMonthChangeListener(null);
        super.onDetach();
    }

    public void setOnMonthChangeListener(MonthlyFragmentListener listener) {
        this.listener = listener;
    }

    // implements MonthlyView

    @Override
    public void onClickDay(@NonNull OneDayView odv) {
        if (listener != null) listener.onDayClick(odv);
    }

    @Override
    public void onMonthChanged(int year, int month) {
        if (listener != null) listener.onChange(year, month);
    }

    @Override
    public int getCurrentPosition() {
        return vvPager.getCurrentItem();
    }
    // --
}
