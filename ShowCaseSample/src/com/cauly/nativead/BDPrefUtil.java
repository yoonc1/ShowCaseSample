package com.cauly.nativead;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Preference 유틸리티 코드
 */
public class BDPrefUtil {
	
	public static final String DEF_PREF_NAME = "Default";
	
	public static void clearAll(Context context) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	public static SharedPreferences getPref(Context context, String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
	
	public static void setStrValue(SharedPreferences.Editor editor, String name, String value) {
		editor.putString(name, value);
	}
	
	public static void setStrValue(Context context, String name, String value) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.putString(name, value);
		editor.commit();
	}
	
	public static void setIntValue(Context context, String name, int value) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.putInt(name, value);
		editor.commit();
	}
	
	public static void setBoolValue(Context context, String name, boolean value) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}
	
	public static void setLongValue(Context context, String name, long value) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.putLong(name, value);
		editor.commit();
	}
	
	public static long getLongValue(Context context, String name, long defValue) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		return pref.getLong(name, defValue);
	}
	
	public static String getStrValue(Context context, String name, String defValue) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		return pref.getString(name, defValue);
	}
	
	public static int getIntValue(Context context, String name, int defValue) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		return pref.getInt(name, defValue);
	}

	public static boolean getBoolValue(Context context, String name, boolean defValue) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		return pref.getBoolean(name, defValue);
	}
	
	public static void setArrayListValue(Context context, String name, ArrayList<String> list)
	{
		if(list!=null)
		{
			SharedPreferences pref = getPref(context, DEF_PREF_NAME);
			SharedPreferences.Editor edit = pref.edit();
		    JSONArray array = new JSONArray();
		    for (int i = 0; i < list.size(); i++)
		    {
		     array.put(list.get(i));
		    }
		    edit.putString(name, array.toString());
		    edit.commit();
		}
	}
	
	public static ArrayList<String> getArrayListValue(Context context,	String name) {
		ArrayList<String> list = new ArrayList<String>();
		SharedPreferences prefs = getPref(context, DEF_PREF_NAME);
		String json = prefs.getString(name, null);
		if (json != null) {
			try {
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					String url = array.optString(i);
					list.add(url);
				}
			} catch (JSONException e) {
			}
		}
		return list;
	}
	
	public static void removeValue(Context context, String name) {
		SharedPreferences pref = getPref(context, DEF_PREF_NAME);
		Editor editor = pref.edit();
		editor.remove(name);
		editor.commit();
	}
	
}
