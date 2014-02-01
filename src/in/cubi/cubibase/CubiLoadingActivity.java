package in.cubi.cubibase;
import in.cubi.cubibase.function.C;
import in.cubi.cubibase.function.JsonFunc;
import in.cubi.cubibase.function.Pref;

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

import com.mocoplex.adlib.AdlibConfig;

public class CubiLoadingActivity extends CubiBaseActivity {
	private final String TAG = getClass().getSimpleName();
	public boolean Debug = true;
	private Context mContext;
	private LinearLayout llLoadingProgress;
	private ProgressBar mProgressBar;

	/** App 관련정보 **/
	private String packageName;
	private String adAdlibr;
	private String idAuthor, idWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		showAd = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
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

		/** 만화 정보 **/
		idAuthor = getResources().getString(R.string.id_author);
		idWork = getResources().getString(R.string.id_work);
		Pref.setInfo(mContext, idAuthor, idWork);
		
		/** 화면크기 **/
		Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Pref.setDisplaySize(mContext, display.getWidth(), display.getHeight());

		/** Adlibr **/
//		packageName = getPackageName();
		
		packageName = "in.cubi.cubibase";
		adAdlibr = getResources().getString(R.string.ad_adlibr);
		initAds();

		/** Initalize **/
		llLoadingProgress = (LinearLayout) findViewById(R.id.llLoadingProgress);		
		new InitializeTask().execute();
	}

	private class InitializeTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar = new ProgressBar(mContext);
			llLoadingProgress.setGravity(Gravity.CENTER_HORIZONTAL);
			llLoadingProgress.addView(mProgressBar, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}

		@Override
		protected Void doInBackground(Void... params) {
			String url = C.URL_LIST + idWork;
			Log.d(TAG, "API_MainURL : " + url);
			JSONObject jsonObjectCubi = JsonFunc.getJSONfromURLGet(url);
			Pref.setJsonObject(mContext, jsonObjectCubi);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			Intent intent = new Intent(CubiLoadingActivity.this, CubiListActivity.class);
			startActivity(intent);
			finish();
		}
	}

	// AndroidManifest.xml에 권한과 activity를 추가하여야 합니다.     
	protected void initAds() {
		if(Debug){
			Log.d(TAG, "--initAds Start--");
//			Log.d(TAG, "-INMOBI : " + packageName + ".ads.SubAdlibAdViewInmobi");
			Log.d(TAG, "-ADAM : " + packageName + ".ads.SubAdlibAdViewAdam");
			Log.d(TAG, "-CAULY : " + packageName + ".ads.SubAdlibAdViewCauly");
			Log.d(TAG, "--initAds End--");
		}

//		AdlibConfig.getInstance().bindPlatform("INMOBI", packageName + ".ads.SubAdlibAdViewInmobi");
		// 쓰지 않을 광고플랫폼은 삭제해주세요.
		AdlibConfig.getInstance().bindPlatform("ADAM", packageName + ".ads.SubAdlibAdViewAdam");
		//AdlibConfig.getInstance().bindPlatform("ADMOB","lhy.undernation.ads.SubAdlibAdViewAdmob");
		AdlibConfig.getInstance().bindPlatform("CAULY", packageName + ".ads.SubAdlibAdViewCauly");
		//AdlibConfig.getInstance().bindPlatform("TAD","lhy.undernation.ads.SubAdlibAdViewTAD");
		//AdlibConfig.getInstance().bindPlatform("NAVER","lhy.undernation.ads.SubAdlibAdViewNaverAdPost");
		//AdlibConfig.getInstance().bindPlatform("SHALLWEAD","lhy.undernation.ads.SubAdlibAdViewShallWeAd");
		AdlibConfig.getInstance().setAdlibKey(adAdlibr);        
	}
}