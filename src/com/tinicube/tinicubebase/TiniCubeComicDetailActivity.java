package com.tinicube.tinicubebase;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.tinicube.tinicubebase.data.DataChapter;
import com.tinicube.tinicubebase.data.DataList;
import com.tinicube.tinicubebase.function.C;
import com.tinicube.tinicubebase.function.Pref;

public class TiniCubeComicDetailActivity extends TiniCubeBaseActivity {
	private final String TAG = this.getClass().getSimpleName();
	private AQuery aq;
	
	private TextView tvTitle;
	
	private WebView wv;
	private DataList mDataList;
	private ArrayList<DataChapter> mDataChapterList;
	
	private ImageButton btnPrev, btnNext;
	
	private int curPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_detail);
		aq = new AQuery(this);
		
		getSupportActionBar().hide();
		
		tvTitle = (TextView) findViewById(R.id.tvComicDetailTitle);
		btnPrev = (ImageButton) findViewById(R.id.btnComicDetailPrev);
		btnNext = (ImageButton) findViewById(R.id.btnComicDetailNext);
		
		mDataList = new DataList(Pref.getJsonObject(mContext));
		mDataChapterList = mDataList.getDataChapters();
		
		Intent intent = getIntent();
//		int chapterId = intent.getIntExtra("chapterId", 0);
		int position = intent.getIntExtra("position", 0);
		curPosition = position;
		DataChapter curDataChapter = mDataChapterList.get(position);
		
		String url = C.URL_DETAIL + Pref.getIdWork(mContext) + "/" + curDataChapter.getId();
		Log.d(TAG, "ChapterId : " + curDataChapter.getId());
		Log.d(TAG, "Chapter URL : " + url);
		tvTitle.setText(curDataChapter.getTitle());
		
		
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
