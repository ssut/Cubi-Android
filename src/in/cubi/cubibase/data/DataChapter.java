package in.cubi.cubibase.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DataChapter {
	private JSONObject mJsonObject;
	
	public DataChapter(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}
	
	public int getId(){
		try{
			return mJsonObject.getInt("id");
		} catch (JSONException e){
			e.printStackTrace();
			return 0;
		}
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
	
	public double getRating(){
		try {
			return mJsonObject.getDouble("rating");
		} catch (JSONException e){
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public int getRatingNumber(){
		try {
			return mJsonObject.getInt("rating_number");
		} catch (JSONException e){
			e.printStackTrace();
			return 0;
		}
	}
}
