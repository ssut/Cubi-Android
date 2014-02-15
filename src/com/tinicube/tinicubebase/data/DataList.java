package com.tinicube.tinicubebase.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataList {
	/**
	 * DataWork, ArrayList<DataChapter> 를 갖는 JsonObject
	 * 
	 * - 구조도 -
	 * DataList
	 * 		- DataWork
	 * 		- List<DataChapter>
	 */
	private JSONObject mJsonObject;

	public DataList(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}

	public DataWork getDataWork(){
		try {
			return new DataWork(mJsonObject.getJSONObject("work"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<DataChapter> getDataChapters(){
		try {
			JSONArray jsonArray = mJsonObject.getJSONArray("chapters");
			ArrayList<DataChapter> dataChapters = new ArrayList<DataChapter>();
			for(int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObjectImage = jsonArray.getJSONObject(i);
				dataChapters.add(new DataChapter(jsonObjectImage));
			}
			return dataChapters;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
