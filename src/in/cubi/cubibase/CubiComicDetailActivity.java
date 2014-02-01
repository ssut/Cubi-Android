package in.cubi.cubibase;

import in.cubi.cubibase.function.C;
import in.cubi.cubibase.function.Pref;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidquery.AQuery;

public class CubiComicDetailActivity extends CubiBaseActivity {
	private final String TAG = this.getClass().getSimpleName();
	private AQuery aq;
	
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_detail);
		aq = new AQuery(this);
		
		getSupportActionBar().hide();
		
		Intent intent = getIntent();
		int chapterId = intent.getIntExtra("chapterId", 0);
		String url = C.URL_DETAIL + Pref.getIdWork(mContext) + "/" + chapterId;
		Log.d(TAG, "ChapterId : " + chapterId);
		Log.d(TAG, "Chapter URL : " + url);
		
		wv = (WebView) findViewById(R.id.wvComicDetail);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                setTitle(url);
                return false;
            }
        });
		wv.loadUrl(url);
	}

}
