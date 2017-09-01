package com.weex.sample.manager;

import android.content.SharedPreferences;

/**
 * Created by tinybian on 2017/9/1.
 */

public class SharedPreferencesManager {
	private static SharedPreferencesManager instance = new SharedPreferencesManager();
	private SharedPreferencesManager() {}
	private SharedPreferences mPref;
	public static SharedPreferencesManager getInstance() {
		return instance;
	}


}
