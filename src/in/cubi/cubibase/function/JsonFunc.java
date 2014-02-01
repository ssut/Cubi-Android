package in.cubi.cubibase.function;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonFunc{

	/** JsonArray 받기 **/
	public static JSONArray getJSONfromURLGetArray(String url){
		InputStream is = null;
		String result = "";
		JSONObject jObj = null;
		JSONArray jAry = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			Log.d("log_tag", result);
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		try{
			jAry = new JSONArray(result);
		} catch(JSONException e){
			e.printStackTrace();
		}
//		try{
//			jObj = new JSONObject(result);            
//		}catch(JSONException e){
//			Log.e("log_tag", "Error parsing data "+e.toString());
//			try {
//				jAry = new JSONArray(result);
//				Log.e("log_tag", "JSONArray parsing");
//				jObj = jAry.getJSONObject(0);
//			} catch (JSONException e1) {
//				e1.printStackTrace();
//			}
//		}

		return jAry;
	}
	
	
	public static JSONObject getJSONfromURLGet(String url){
		InputStream is = null;
		String result = "";
		JSONObject jObj = null;
		JSONArray jAry = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Accept", "application/json");
			httpget.setHeader("Content-type", "application/json");
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			Log.d("log_tag", result);
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		try{
			jObj = new JSONObject(result);            
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
			try {
				jAry = new JSONArray(result);
				Log.e("log_tag", "JSONArray parsing");
				jObj = jAry.getJSONObject(0);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		return jObj;
	}
	
	public static JSONObject getJSONfromURL(String url){
		InputStream is = null;
		String result = "";
		JSONObject jObj = null;
		JSONArray jAry = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			Log.d("log_tag", result);
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		try{
			jObj = new JSONObject(result);            
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
			try {
				jAry = new JSONArray(result);
				Log.e("log_tag", "JSONArray parsing");
				jObj = jAry.getJSONObject(0);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		return jObj;
	}
}