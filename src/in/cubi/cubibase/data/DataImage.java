package in.cubi.cubibase.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataImage {
	private final String TAG = this.getClass().getSimpleName();
	private JSONObject mJsonObject;

	public DataImage(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}

	public int getWidth(){
		try {
			return mJsonObject.getInt("width");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getHeight(){
		try {
			return mJsonObject.getInt("height");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getUrl(){
		try {
			return mJsonObject.getString("url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Cover Resized Height
	public int getHeightForResizedWidth(int width){
		int value;
		if(this.getWidth() > width){
			int coverWidth = this.getWidth();
			int coverHeight = this.getHeight();
			value = (int)(((float) coverHeight / coverWidth) * width);
		} else{
			value = this.getHeight();
		}
		Log.d(TAG, "getHeightForResizedWidth : " + value);
		return value;
	}
	// Cover Scale
	public float getScaleForResizedWidth(int width){
		float value;
		if(this.getWidth() > width){
			value = (float) this.getWidth()/width;
		} else{
			value = 1.0f;
		}
		Log.d(TAG, "getScaleForResizedWidth : " + value);
		return value;
	}
	
}
