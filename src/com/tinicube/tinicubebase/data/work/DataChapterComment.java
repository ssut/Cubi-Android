package com.tinicube.tinicubebase.data.work;

import org.json.JSONException;
import org.json.JSONObject;

public class DataChapterComment extends BaseDataComment {

	public DataChapterComment(JSONObject jsonObject) {
		super(jsonObject);
	}
	
	public DataChapterComment(){
		super();
	}
	
	public DataChapter getDataChapter(){
		return new DataChapter(getJsonObject("chapter"));
	}
	
	

}
