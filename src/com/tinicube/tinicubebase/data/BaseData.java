package com.tinicube.tinicubebase.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.tinicube.tinicubebase.function.C;

public class BaseData {
	protected JSONObject mJsonObject;
	
	public BaseData(JSONObject jsonObject){
		mJsonObject = jsonObject;
		try {
			mJsonObject.put(C.JSON_STATUS, C.JSON_SUCCESS);
		} catch (JSONException e) {
			e.printStackTrace();
			mJsonObject = new JSONObject();
			try {
				mJsonObject.put(C.JSON_STATUS, C.JSON_FAILED);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public BaseData(){
		mJsonObject = new JSONObject();
		try {
			mJsonObject.put(C.JSON_STATUS, C.JSON_FAILED);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject getSelfJsonObject(){
		return mJsonObject;
	}
	public String getSelfStrJsonObject(){
		return mJsonObject.toString();
	}
	
	public boolean isConvertJsonSuccess(){
		if(getBoolean(C.JSON_STATUS)){
			return true;
		} else{
			return false;
		}
	}
	protected int getInt(String key){
		try{
			return mJsonObject.getInt(key);
		} catch (JSONException e){
			e.printStackTrace();
			return 0;
		}
	}
	protected String getString(String key){
		try {
			return mJsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	protected boolean getBoolean(String key){
		try{
			return mJsonObject.getBoolean(key);
		} catch (JSONException e){
			e.printStackTrace();
			return false;
		}
	}
	protected JSONObject getJsonObject(String key){
		try {
			JSONObject jsonObject = mJsonObject.getJSONObject(key);
			jsonObject.put(C.JSON_STATUS, C.JSON_SUCCESS);
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
			return getFailedJsonObject();
		}
	}
	
	protected Calendar getCalendar(String strDate, String strFormat){
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		Calendar calendar;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}
	
	private JSONObject getFailedJsonObject(){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(C.JSON_STATUS, C.JSON_FAILED);
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
