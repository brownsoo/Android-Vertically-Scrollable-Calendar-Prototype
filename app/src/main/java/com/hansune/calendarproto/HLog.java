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

package com.hansune.calendarproto;

import android.util.Log;

public class HLog {
	
	public static boolean isDebugMode = true;
	public static final void e(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.e(TAG, text);
		}
	}

	public static final void w(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.w(TAG, text);
		}
	}

	public static final void i(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.i(TAG, text);
		}
	}

	public static final void d(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.d(TAG, text);
		}
	}

	public static final void v(String TAG, String CLASS, String msg) {
		if(isDebugMode) {
			String THREAD = Thread.currentThread().getName();
			String text = "[" + THREAD + "] " + CLASS + " " + msg;
			Log.v(TAG, text);
		}
	}
}
