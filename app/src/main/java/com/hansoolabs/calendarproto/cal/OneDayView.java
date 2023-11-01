package com.hansoolabs.calendarproto.cal;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hansoolabs.calendarproto.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * View to display a day
 * @author Brownsoo
 *
 * @noinspection unused
 */
public class OneDayView extends RelativeLayout {

    /** number text field */
    @NonNull
    private final TextView dayTv;
    /** message text field*/
    @NonNull
    private final TextView msgTv;
    /** Weather icon */
    @NonNull
    private final ImageView weatherIv;
    /** Value object for a day info */
    @NonNull
    private OneDayData one;

    /**
     * OneDayView constructor
     * @param context context
     */
    public OneDayView(@NonNull Context context) {
        this(context, null);
    }

    /**
     * OneDayView constructor for xml
     * @param context context
     * @param attrs AttributeSet
     */
    public OneDayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = View.inflate(context, R.layout.oneday, this);
        dayTv = v.findViewById(R.id.onday_dayTv);
        weatherIv = v.findViewById(R.id.onday_weatherIv);
        msgTv = v.findViewById(R.id.onday_msgTv);
        one = new OneDayData();
        
    }
    
    /**
     * Set the day to display
     * @param year 4 digits of year
     * @param month Calendar.JANUARY ~ Calendar.DECEMBER
     * @param day day of month
     */
    public void setDay(int year, int month, int day) {
        this.one.cal.set(year, month, day);
    }

    /**
     * Set the day to display
     * @param cal Calendar instance
     */
    public void setDay(Calendar cal) {
        this.one.setDay((Calendar) cal.clone());
    }

    /**
     * Set the day to display
     * @param one OneDayData instance
     */
    public void setDay(OneDayData one) {
        this.one = one;
    }
    
    /**
     * Get the day to display
     * @return OneDayData instance
     */
    public OneDayData getDay() {
        return one;
    }

    /**
     * Set the message to display
     * @param msg message
     */
    public void setMessage(String msg){
        one.setMessage(msg);
    }

    /**
     * Get the message
     * @return message
     */
    public CharSequence getMessage(){
        return  one.getMessage();
    }

    /**
     * Same function with {@link Calendar#get(int)}<br>
     * <br>
     * Returns the value of the given field after computing the field values by
     * calling {@code complete()} first.
     * 
     * @param field Calendar.YEAR or Calendar.MONTH or Calendar.DAY_OF_MONTH
     *
     * @throws IllegalArgumentException
     *                if the fields are not set, the time is not set, and the
     *                time cannot be computed from the current field values.
     * @throws ArrayIndexOutOfBoundsException
     *                if the field is not inside the range of possible fields.
     *                The range is starting at 0 up to {@code FIELD_COUNT}.
     */
    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return one.get(field);
    }

    /**
     * Set weather
     * @param weather Weather instance
     */
    public void setWeather(WeatherInfo.Weather weather) {
        this.one.setWeather(weather);
    }
    
    /**
     * Updates UI upon the value object.
     */
    public void refresh() {
        
        //HLog.d(TAG, CLASS, "refresh");
        
        dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));

        if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayTv.setTextColor(Color.RED);
        }
        else if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayTv.setTextColor(Color.BLUE);
        }
        else {
            dayTv.setTextColor(Color.BLACK);
        }

        msgTv.setText((one.getMessage()==null)?"":one.getMessage());
        switch (one.weather) {
            case CLOUDY, SUN_CLOUDY -> weatherIv.setImageResource(R.drawable.cloudy);
            case RAINY -> weatherIv.setImageResource(R.drawable.rainy);
            case SNOW -> weatherIv.setImageResource(R.drawable.snowy);
            case SUNSHINE -> weatherIv.setImageResource(R.drawable.sunny);
        }
        
    }
    
}