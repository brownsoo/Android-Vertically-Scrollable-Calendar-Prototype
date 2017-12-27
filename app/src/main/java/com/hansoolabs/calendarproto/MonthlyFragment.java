/*
* Copyright (C) 2015 Hansoo Lab.
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
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hansoolabs.calendarproto.cal.OneDayView;
import com.hansoolabs.calendarproto.cal.OneMonthView;

import java.util.Calendar;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;



/**
 * Fragment for displaying the monthly-calendar
 * @author brownsoo
 *
 */
public class MonthlyFragment extends Fragment {

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "MonthlyFragment";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());
    
    public static final String ARG_YEAR = "year";
    public static final String ARG_MONTH = "month";

    /**
     * Callback when current month is changed
     * @author Brownsoo
     *
     */
    public interface OnMonthChangeListener {
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
    
    /**
     * dummy listener
     */
    private OnMonthChangeListener dummyListener = new OnMonthChangeListener() {
        @Override
        public void onChange(int year, int month) {}

        @Override
        public void onDayClick(OneDayView dayView) {}

    };
    
    private OnMonthChangeListener listener = dummyListener;
    /**
     * Vertical View Pager<br>
     * ref: https://github.com/LittlePanpc/VerticalViewPager-1
     */
    private VerticalViewPager vvPager;

    private MonthlySlidePagerAdapter adapter;
    private int mYear = -1;
    private int mMonth = -1;

    public static MonthlyFragment newInstance(int year, int month) {

        HLog.d(TAG, NAME, "newInstance " + year + "/" + month);

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

        HLog.d(TAG, CLASS, "onCreate " + mYear + "." + mMonth);
        
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new MonthlySlidePagerAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_monthly, container, false);

        vvPager = (VerticalViewPager) v.findViewById(R.id.vviewPager);
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

    public void setOnMonthChangeListener(OnMonthChangeListener listener) {
        if(listener == null) this.listener = dummyListener;
        else this.listener = listener;
    }


    private OneMonthView.OnClickDayListener onClickDayListener = new OneMonthView.OnClickDayListener() {
        @Override
        public void onClick(OneDayView odv) {
            listener.onDayClick(odv);
        }
    };


    /**
     * Object to preserve year and month
     * @author Brownsoo
     *
     */
    public class YearMonth {
        int year;
        int month;
        
        YearMonth(int year, int month) {
            this.year = year;
            this.month = month;
        }
    }

    /**
     * 
     * PagerAdapter to view calendar monthly
     * 
     * @author Brownsoo
     *
     */
    private class MonthlySlidePagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @SuppressWarnings("unused")
        private Context mContext;
        
        private OneMonthView[] monthViews;
        /** Default year to calculate the page position */
        final static int BASE_YEAR = 2017;
        /** Default month to calculate the page position */
        final static int BASE_MONTH = Calendar.JANUARY;
        /** Calendar instance based on default year and month */
        final Calendar BASE_CAL;
        /** Page numbers to reuse */
        final static int PAGES = 5;
        /** Inner virtual pages, I think it may be infinite scroll. */
        final static int TOTAL_PAGES = Integer.MAX_VALUE;
        /** position basis */
        final static int BASE_POSITION = TOTAL_PAGES / 2;
        /** previous position */
        private int previousPosition;
        
        public MonthlySlidePagerAdapter(Context context) {
            this.mContext = context;
            Calendar base = Calendar.getInstance();
            base.set(BASE_YEAR, BASE_MONTH, 1);
            BASE_CAL = base;
            
            monthViews = new OneMonthView[PAGES];
            for(int i = 0; i < PAGES; i++) {
                monthViews[i] = new OneMonthView(getActivity());
            }
        }
        
        /**
         * Get the particular date by page position
         * @param position page position
         * @return YearMonth
         */
        public YearMonth getYearMonth(int position) {
            Calendar cal = (Calendar)BASE_CAL.clone();
            cal.add(Calendar.MONTH, position - BASE_POSITION);
            return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        }
        
        /**
         * Get the page position by given date
         * @param year 4 digits number of year
         * @param month month number
         * @return page position
         */
        public int getPosition(int year, int month) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, 1);
            return BASE_POSITION + howFarFromBase(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        }

        /**
         * How many months exist from the base month to the given values?
         * @param year the year to compare with the base year
         * @param month the month to compare with the base month
         * @return counts of month
         */
        private int howFarFromBase(int year, int month) {
            
            int disY = (year - BASE_YEAR) * 12;
            int disM = month - BASE_MONTH;
            
            return disY + disM;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            
            HLog.d(TAG, CLASS, "instantiateItem " + position);
            
            int howFarFromBase = position - BASE_POSITION;
            Calendar cal = (Calendar) BASE_CAL.clone();
            cal.add(Calendar.MONTH, howFarFromBase);
            
            position = position % PAGES;
            
            container.addView(monthViews[position]);
            
            monthViews[position].make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
            monthViews[position].setOnClickDayListener(onClickDayListener);
            
            return monthViews[position];
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            HLog.d(TAG, CLASS, "destroyItem " + position);
            ((OneMonthView) object).setOnClickDayListener(null);
            container.removeView((View) object);
        }        
        
        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
        
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
        
        @Override
        public void onPageScrollStateChanged(int state) {
            switch(state) {
            case ViewPager.SCROLL_STATE_IDLE:
                //HLog.d(TAG, CLASS, "SCROLL_STATE_IDLE");
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                //HLog.d(TAG, CLASS, "SCROLL_STATE_DRAGGING");
                previousPosition = vvPager.getCurrentItem();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                //HLog.d(TAG, CLASS, "SCROLL_STATE_SETTLING");
                break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            
            //HLog.d(TAG, CLASS, position + "-  " + positionOffset);
            if(previousPosition != position) {
                previousPosition = position;
                
                YearMonth ym = getYearMonth(position);
                listener.onChange(ym.year, ym.month);
                
                HLog.d(TAG, CLASS, position + " onPageScrolled-  " + ym.year + "." + ym.month);
            }
        }

        @Override
        public void onPageSelected(int position) {
        }
    }
}
