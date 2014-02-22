package com.tinicube.tinicubebase.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



public class BaseAsyncTask extends AsyncTask<Void, String, Integer>{
	public static String TAG = "BaseAsyncTask";

	private static Boolean D = true;
	protected ProgressDialog mProgressDialog;
	protected boolean showDialog = true;
	protected Context mContext;
	private String title;

	public BaseAsyncTask(Context context, String title){
		mContext = context;
		this.title = title;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(showDialog){
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setTitle("잠시만 기다려주세요...");
			mProgressDialog.setMessage(title);
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.show();
		}
	}

	@Override
	protected Integer doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(showDialog){
			mProgressDialog.dismiss();
		}
	}

	public static String postRequest(String strUrl, HashMap<String, String> valuePair){
		return postRequestHttpClient(strUrl, valuePair);
	}
	public static String postRequest(String strUrl, HashMap<String, String> valuePair, HashMap<String, String> filePair){
		return postRequestFileHttpClient(strUrl, valuePair, filePair);
	}

	public static String postRequestHttpUrlConnection(String strUrl, HashMap<String, String> valuePair){
		URL url;
		try {
			url = new URL(strUrl);

			HttpURLConnection conn = null;

			if (url.getProtocol().toLowerCase().equals("https")) {
				trustAllHosts();
				HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				conn = https;
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			Set<String> keySet = valuePair.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = valuePair.get(key);
				nameValuePairs.add(new BasicNameValuePair(key, value));
			}

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(getQuery(nameValuePairs));
			writer.flush();
			writer.close();
			os.close();

			conn.connect();

			String buffer = null;
			BufferedReader in = new BufferedReader(new InputStreamReader
					(conn.getInputStream()));
			String resultString = "";
			while ((buffer = in.readLine()) != null) {
				//				Log.d(TAG, buffer);
				resultString += buffer;
			}
			in.close();
			return resultString;


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}




	}






	public static String postRequestHttpClient(String url, HashMap<String, String> valuePair){
		InputStream is = null;
		String result = "";

		try{
			// HttpClient, HttpPost 생성
			HttpClient httpclient = getHttpClient();
			HttpPost httppost = new HttpPost(url);

			// HttpParam에 응답시간 5초 넘을 시 timeout
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Parameter 세팅
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			Set<String> keySet = valuePair.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = valuePair.get(key);
				nameValuePairs.add(new BasicNameValuePair(key, value));
			}  

			// Parameter 인코딩, POST요청에 포함
			UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			httppost.setEntity(entityRequest);

			// 요청 및 결과값 리턴
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			if(D) Log.e("log_tag", "Error in http connection "+e.toString());
		}

		// 결과값 String으로 변환
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			//			if(D) Log.d("log_tag", result);
		}catch(Exception e){
			if(D) Log.e("log_tag", "Error converting result "+e.toString());
		}

		return result;
	}

	public static String postRequestFileHttpClient(String url, HashMap<String, String> valuePair, HashMap<String, String> filePair){
		InputStream is = null;
		String result = "";

		try{
			// HttpClient, HttpPost 생성
			HttpClient httpclient = getHttpClient();
			HttpPost httpPost = new HttpPost(url);

			// HttpParam에 응답시간 5초 넘을 시 timeout
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Builder
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			Charset chars = Charset.forName("UTF-8");
			builder.setCharset(chars);

			// FilePair key,value String 세팅
			Set<String> fileKeySet = filePair.keySet();
			Iterator<String> fileIterator = fileKeySet.iterator();
			while(fileIterator.hasNext()){
				String key = fileIterator.next();
				String value = filePair.get(key);

				FileBody bin = new FileBody(new File(value));
				builder.addPart(key, bin);
			}

			// ValuePiar key,value String 세팅
			Set<String> keySet = valuePair.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = valuePair.get(key);
				//				value = URLEncoder.encode(value, "UTF-8");
				builder.addTextBody(key, value, ContentType.create("text/plain", "utf-8"));
				//				builder.addTextBody(key, value);
			}

			// HttpEntity 생성, HttpPost setEntity
			HttpEntity reqEntity = builder.build();
			httpPost.setEntity(reqEntity);

			// 요청 및 결과값 리턴
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			if(D) Log.e("log_tag", "Error in http connection "+e.toString());
		}

		// 결과값 String으로 변환
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			//			if(D) Log.d("log_tag", result);
		}catch(Exception e){
			if(D) Log.e("log_tag", "Error converting result "+e.toString());
		}

		return result;
	}

	public static String getRequest(String url, HashMap<String, String> valuePair){
		InputStream is = null;
		String result = "";
		String getUrl = url;

		try{
			// Parameter 세팅
			getUrl += "?";
			Set<String> keySet = valuePair.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = valuePair.get(key);
				getUrl = getUrl + key + "=" + value + "&";
			}  

			// HttpClient, HttpGet 생성
			HttpClient httpclient = getHttpClient();
			HttpGet httpget = new HttpGet(url);

			// HttpParam에 응답시간 5초 넘을 시 timeout
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);


			// 요청 및 결과값 리턴
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			if(D) Log.e("log_tag", "Error in http connection "+e.toString());
		}

		// 결과값 String으로 변환
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			//			if(D) Log.d("log_tag", result);
		}catch(Exception e){
			if(D) Log.e("log_tag", "Error converting result "+e.toString());
		}

		return result;
	}

	private static DefaultHttpClient getHttpClient(){
		return getNewHttpClient();
	}

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params)
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] chain,
					String authType)
							throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] chain,
					String authType)
							throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
			.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static DefaultHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			//	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			//	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);

		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}


}