package com.hansoolabs.calendarproto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hansoolabs.calendarproto.cal.OneDayView;
import com.hansoolabs.calendarproto.cal.OneMonthView;
import com.hansoolabs.calendarproto.cal.YearMonth;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 *
 * PagerAdapter to view calendar monthly
 *
 * @author Brownsoo
 *
 */
class MonthlyPagerAdapter extends PagerAdapter
        implements ViewPager.OnPageChangeListener, OneMonthView.OnClickDayListener {

    private static final String TAG = MConfig.TAG;
    private final String klass = "MonthlyPagerAdapter@" + Integer.toHexString(hashCode());
    @Nullable
    private final MonthlyView monthlyView;
    @NonNull
    private final OneMonthView[] monthViews;
    /** Default year to calculate the page position */
    private final static int BASE_YEAR = 2018;
    /** Default month to calculate the page position */
    private final static int BASE_MONTH = Calendar.JANUARY;
    /** Calendar instance based on default year and month */
    @NonNull
    private final Calendar BASE_CAL;
    /** Page numbers to reuse */
    private final static int PAGES = 5;
    /** Inner virtual pages, I think it may be infinite scroll. */
    private final static int TOTAL_PAGES = Integer.MAX_VALUE;
    /** position basis */
    private final static int BASE_POSITION = TOTAL_PAGES / 2;
    /** previous position */
    private int previousPosition;

    public MonthlyPagerAdapter(@NonNull Context context, @Nullable MonthlyView monthlyView) {
        this.monthlyView = monthlyView;
        Calendar base = Calendar.getInstance();
        base.set(BASE_YEAR, BASE_MONTH, 1);
        BASE_CAL = base;

        monthViews = new OneMonthView[PAGES];
        for(int i = 0; i < PAGES; i++) {
            monthViews[i] = new OneMonthView(context);
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

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        HLog.d(TAG, klass, "instantiateItem " + position);

        int howFarFromBase = position - BASE_POSITION;
        Calendar cal = (Calendar) BASE_CAL.clone();
        cal.add(Calendar.MONTH, howFarFromBase);

        position = position % PAGES;

        container.addView(monthViews[position]);

        monthViews[position].make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        monthViews[position].setOnClickDayListener(this);

        return monthViews[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        HLog.d(TAG, klass, "destroyItem " + position);
        ((OneMonthView) object).setOnClickDayListener(null);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
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
            previousPosition = monthlyView != null ? monthlyView.getCurrentPosition() : 0;
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
            if (monthlyView != null) {
                monthlyView.onMonthChanged(ym.year, ym.month);
            }
            HLog.d(TAG, klass, position + " onPageScrolled-  " + ym.year + "." + ym.month);
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    // implements OneMonthView.OnClickDayListener

    @Override
    public void onClick(OneDayView odv) {
        if (monthlyView != null) monthlyView.onClickDay(odv);
    }
}
