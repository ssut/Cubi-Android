package com.tinicube.tinicubebase.function;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
	
	
	public static void setInfo(Context context, String idAuthor, String idWork){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("idAuthor", idAuthor);
		editor.putString("idWork", idWork);
		editor.commit();
	}
	
	public static String getIdAuthor(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		return pref.getString("idAuthor", "");
	}
	
	public static String getIdWork(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		return pref.getString("idWork", "");
	}
	
	// Display Width, Height 저장
	public static void setDisplaySize(Context context, int width, int height){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("width", width);
		editor.putInt("height", height);
		editor.commit();
	}

	// Display Width 불러오기
	public static int getDisplayWidth(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		int value = pref.getInt("width", 0);
		return value;
	}
	
	// Display Width 불러오기
		public static int getDisplayHeight(Context context){
			SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
			int value = pref.getInt("height", 0);
			return value;
		}

	// Density 저장
	public static void setDensity(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		float density  = context.getResources().getDisplayMetrics().density;
		editor.putFloat("density", density);
		editor.commit();
	}

	// Density 불러오기
	public static float getDensity(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		float density = pref.getFloat("density", 0.0f);
		return density;
	}

	// JsonObject String 형태로 저장
	public static void setJsonObject(Context context, JSONObject jsonObject){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		String strJsonObject = jsonObject.toString();
		editor.putString("jsonObject", strJsonObject);
		editor.commit();
	}

	// JsonObjectString 을 저장
	public static void setJsonObjectString(Context context, String str){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("jsonObject", str);
		editor.commit();
	}

	// String 형태로 저장되어있는 JsonObject를 JsonObject객체로 생성하여 리턴
	public static JSONObject getJsonObject(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		String strJsonObject = pref.getString("jsonObject", "");
		try {
			return new JSONObject(strJsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	// String 형태로 저장되어있는 JsonObject를 String그대로 리턴
	public static String getJsonObjectString(Context context){
		SharedPreferences pref = context.getSharedPreferences("savedValue", Context.MODE_PRIVATE);
		String strJsonObject = pref.getString("jsonObject", "");
		return strJsonObject;
	}
}
