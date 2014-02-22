package com.tinicube.tinicubebase.data.work;

import org.json.JSONObject;

public class DataChapterRating extends BaseDataRating {
	
	public DataChapterRating(JSONObject jsonObject){
		super(jsonObject);
	}
	public DataChapterRating(){
		super();
	}
	
	public DataChapter getChapter(){
		return new DataChapter(getJsonObject("chapter"));
	}
}
