package com.hansoolabs.calendarproto;

import com.hansoolabs.calendarproto.cal.OneDayView;

import androidx.annotation.NonNull;

public interface MonthlyView {
    void onClickDay(@NonNull OneDayView odv);
    void onMonthChanged(int year, int month);
    int getCurrentPosition();
}
