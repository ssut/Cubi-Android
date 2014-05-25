package com.tinicube.tinicubebase;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mocoplex.adlib.AdlibConfig;
import com.tinicube.tinicubebase.data.work.DataList;
import com.tinicube.tinicubebase.function.BaseAsyncTask;
import com.tinicube.tinicubebase.function.C;
import com.tinicube.tinicubebase.function.JsonFunc;
import com.tinicube.tinicubebase.function.Pref;

public class TiniCubeComicLoadingActivity extends TiniCubeBaseActivity {
	private final String TAG = getClass().getSimpleName();
	public boolean Debug = true;
	private boolean loadSuccess = false;
	private Context mContext;
	private LinearLayout llLoadingProgress;
	private ProgressBar mProgressBar;
	private TextView tvMessage;

	/** App 관련정보 **/
	private String packageName;
	private String adAdlibr;
	private String idAuthor, idWork;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		showAd = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_activity);
		mContext= this;

		/** Debug message **/
		if(Debug){
			String adAdlibr = getResources().getString(R.string.ad_adlibr);
			String adAdam = getResources().getString(R.string.ad_adam);
			String adCauly = getResources().getString(R.string.ad_cauly);
			String adInmobi = getResources().getString(R.string.ad_inmobi);
			String idFlurry = getResources().getString(R.string.id_flurry);
			Log.d(TAG, "----- APP Info Start -----------------------------------");
			Log.d(TAG, "- PackageName : " + getPackageName());
			Log.d(TAG, "- Adlibr : " + adAdlibr);
			Log.d(TAG, "- Adam : " + adAdam);
			Log.d(TAG, "- Cauly : " + adCauly);
			Log.d(TAG, "- InMobi : " + adInmobi);
			Log.d(TAG, "- Flurry : " + idFlurry);
			Log.d(TAG, "----- APP Info End ------------------------------------");
		}

		/** ActionBar **/
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.hide();

		/** 작품 정보 **/
		type = getResources().getString(R.string.type);
		idAuthor = getResources().getString(R.string.id_author);
		idWork = getResources().getString(R.string.id_work);
		Pref.setInfo(mContext, idAuthor, idWork);

		/** 화면크기 **/
		Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Pref.setDisplaySize(mContext, display.getWidth(), display.getHeight());
		tvMessage = (TextView) findViewById(R.id.tvLoadingMessage);
		
		/** Adlibr **/
		//		packageName = getPackageName();

		packageName = "com.tinicube.tinicubebase";
		adAdlibr = getResources().getString(R.string.ad_adlibr);
		initAds();

		/** Initalize **/
		llLoadingProgress = (LinearLayout) findViewById(R.id.llLoadingProgress);		
		new InitializeTask(mContext, "초기화 중...").execute();
	}

	private class InitializeTask extends BaseAsyncTask{
		private String resultString;
		
		public InitializeTask(Context context, String title) {
			super(context, title);
			showDialog = false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar = new ProgressBar(mContext);
			llLoadingProgress.setGravity(Gravity.CENTER_HORIZONTAL);
			llLoadingProgress.addView(mProgressBar, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}

		
		@Override
		protected Integer doInBackground(Void... params) {
			try{
				String workId = Pref.getIdWork(mContext);
				HashMap<String, String> valuePair = new HashMap<String, String>();
				valuePair.put("work_id", workId);
				resultString = postRequest(C.API_CHAPTER_LIST, valuePair);
				
				// 받은 데이터 확인 후, 구성요소 있는지 테스트
				DataList dataList = new DataList(new JSONObject(resultString));
				dataList.getDataChapters();
				dataList.getDataWork();
				Pref.setJsonObjectString(mContext, resultString);
				loadSuccess = true;
			} catch(Exception e){
				e.printStackTrace();
				loadSuccess = false;
			}
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			if(loadSuccess){
				Intent intent = null;
				if(type.equals("webtoon")){
					intent = new Intent(TiniCubeComicLoadingActivity.this, TiniCubeComicChapterListActivity.class);
					startActivity(intent);
					finish();
				} else{
					Toast.makeText(mContext, "작품 타입을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
				}
				
			} else{
				Toast.makeText(mContext, "서버와의 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
				tvMessage.setText("Load Faild");
			}
		}

		
	}

	// 광고 초기화 함수
	protected void initAds() {
		if(Debug){
			Log.d(TAG, "--initAds Start--");
			//			Log.d(TAG, "-INMOBI : " + packageName + ".ads.SubAdlibAdViewInmobi");
			Log.d(TAG, "-ADAM : " + packageName + ".ads.SubAdlibAdViewAdam");
			Log.d(TAG, "-CAULY : " + packageName + ".ads.SubAdlibAdViewCauly");
			Log.d(TAG, "--initAds End--");
		}

		//AdlibConfig.getInstance().bindPlatform("INMOBI", packageName + ".ads.SubAdlibAdViewInmobi");
		//쓰지 않을 광고플랫폼은 삭제해주세요.
		AdlibConfig.getInstance().bindPlatform("ADAM", packageName + ".ads.SubAdlibAdViewAdam");
		//AdlibConfig.getInstance().bindPlatform("ADMOB","lhy.undernation.ads.SubAdlibAdViewAdmob");
		AdlibConfig.getInstance().bindPlatform("CAULY", packageName + ".ads.SubAdlibAdViewCauly");
		//AdlibConfig.getInstance().bindPlatform("TAD","lhy.undernation.ads.SubAdlibAdViewTAD");
		//AdlibConfig.getInstance().bindPlatform("NAVER","lhy.undernation.ads.SubAdlibAdViewNaverAdPost");
		//AdlibConfig.getInstance().bindPlatform("SHALLWEAD","lhy.undernation.ads.SubAdlibAdViewShallWeAd");
		AdlibConfig.getInstance().setAdlibKey(adAdlibr);        
	}
}