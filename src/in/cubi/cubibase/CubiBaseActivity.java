package in.cubi.cubibase;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.mocoplex.adlib.AdlibAdViewContainer;
import com.mocoplex.adlib.AdlibManager;
import com.mocoplex.adlib.AdlibManager.AdlibVersionCheckingListener;

public class CubiBaseActivity extends ActionBarActivity {
	private final String TAG = this.getClass().getSimpleName();
	private Context mContext;
	public boolean showAd = true;
	private static Typeface mTypeface;

	/** Adlib **/
	private AdlibManager _amanager;
	private String idFlurry;
	protected AdlibAdViewContainer adlibView = null;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mContext = this;
		idFlurry = getResources().getString(R.string.id_flurry);

		_amanager = new AdlibManager();
		_amanager.onCreate(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		if (CubiBaseActivity.mTypeface == null)
			CubiBaseActivity.mTypeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        setGlobalFont(root);
        
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// setContentView에 전달된 layout으로 view생성
		View view = inflater.inflate(layoutResID, null);

		// 전체화면 구성하는 LinearLayout llRootView 생성
		LinearLayout llRootView = new LinearLayout(mContext);
		// llRootView는 LinearLayout, Vertical
		llRootView.setOrientation(LinearLayout.VERTICAL);
		// llRootView는 width-match_parent, height-match_parent
		llRootView.setLayoutParams(new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		// llRootView에 setContentView에서 지정한 layout을 추가
		// weight 1.0f로 광고를 제외한 모든 부분을 차지하게 함
		llRootView.addView(view, new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				0, 	1.0f));

		// 광고를 위한 LayoutParmas
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

		// 광고 생성
		adlibView = new AdlibAdViewContainer(mContext);
		if(showAd){
			llRootView.addView(adlibView, params);
		}

		// Adlib뷰에 스케쥴러 바인드
		bindAdsContainer(adlibView);

		super.setContentView(llRootView);
	}


	@Override
	protected void onStart() {
		super.onStart();
//		Log.d(TAG, "Flurry ID : " + idFlurry);
		FlurryAgent.onStartSession(this, idFlurry);
	}
	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

	protected void onResume(){		
		_amanager.onResume(this);
		super.onResume();
	}

	protected void onPause(){    	
		_amanager.onPause();
		super.onPause();
	}

	protected void onDestroy(){    	
		_amanager.onDestroy(this);
		super.onDestroy();
	}

	/** Adlib **/
	public void setAdsContainer(int rid){_amanager.setAdsContainer(rid);}
	public void bindAdsContainer(AdlibAdViewContainer a){	_amanager.bindAdsContainer(a);}
	public void setVersionCheckingListner(AdlibVersionCheckingListener l){ _amanager.setVersionCheckingListner(l);}
	public void destroyAdsContainer() {_amanager.destroyAdsContainer();}
	
	// Font
	void setGlobalFont(ViewGroup root) {
	    for (int i = 0; i < root.getChildCount(); i++) {
	        View child = root.getChildAt(i);
	        if (child instanceof TextView){
	            ((TextView)child).setTypeface(mTypeface);
	        }
	        else if (child instanceof ViewGroup){
	            setGlobalFont((ViewGroup)child);
	        }
	    }
	}
}
