package com.tinicube.tinicubebase.data.work;

import org.json.JSONException;
import org.json.JSONObject;

import com.tinicube.tinicubebase.data.BaseData;
import com.tinicube.tinicubebase.data.DataUser;

public class BaseDataRating extends BaseData {
	
	public BaseDataRating(JSONObject jsonObject){
		super(jsonObject);
	}
	public BaseDataRating(){
		super();
	}
	
	public int getId(){
		return getInt("id");
	}
	
	public DataUser getAuthor(){
		try {
			return new DataUser(mJsonObject.getJSONObject("author"));
		} catch (JSONException e) {
			e.printStackTrace();
			return new DataUser();
		}
	}
	
	public String getCreatedDate(){
		return getString("created_date");
	}
	
	public String getCreatedTime(){
		return getString("created_time");
	}
	
	public int getScore(){
		return getInt("score");
	}
}
