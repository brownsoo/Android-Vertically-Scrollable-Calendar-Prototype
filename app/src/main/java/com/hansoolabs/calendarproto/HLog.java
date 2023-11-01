package com.hansoolabs.calendarproto;

import android.util.Log;

/** @noinspection unused*/
public class HLog {

	public static boolean isDebugMode = true;
	public static void e(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.e(TAG, text);
		}
	}

	public static void w(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.w(TAG, text);
		}
	}

	public static void i(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.i(TAG, text);
		}
	}

	public static void d(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.d(TAG, text);
		}
	}

	public static void v(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.v(TAG, text);
		}
	}
}
