package in.cubi.cubibase.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DataWork {
	private JSONObject mJsonObject;

	public DataWork(JSONObject jsonObject){
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
	
	public String getDescription(){
		try {
			return mJsonObject.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCategory(){
		try {
			return mJsonObject.getString("category");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getAuthor(){
		try {
			return mJsonObject.getString("author");
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
	
	public String getMarketAndroid(){
		try {
			return mJsonObject.getString("market_android");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getMarketIOS(){
		try {
			return mJsonObject.getString("market_ios");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	
	
	

	public String get(){
		try {
			return mJsonObject.getString("");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}
