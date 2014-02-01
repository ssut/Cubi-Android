package in.cubi.cubibase.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DataChapter {
	private JSONObject mJsonObject;
	
	public DataChapter(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}
	
	public String getTitle(){
		try {
			return mJsonObject.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCreated(){
		try {
			return mJsonObject.getString("created");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getChapterNumber(){
		try {
			return mJsonObject.getString("reg_no");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public DataImage getThumbnail(){
		try {
			return new DataImage(mJsonObject.getJSONObject("thumbnail"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public DataImage getCover(){
		try {
			return new DataImage(mJsonObject.getJSONObject("cover"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
