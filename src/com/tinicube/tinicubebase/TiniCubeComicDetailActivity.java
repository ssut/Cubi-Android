package com.tinicube.tinicubebase;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.tinicube.tinicubebase.commentrating.TiniCubeCommentRatingActivity;
import com.tinicube.tinicubebase.data.work.DataChapter;
import com.tinicube.tinicubebase.data.work.DataList;
import com.tinicube.tinicubebase.function.C;
import com.tinicube.tinicubebase.function.Pref;

public class TiniCubeComicDetailActivity extends TiniCubeBaseActivity implements OnClickListener, OnTouchListener {
	private final String TAG = this.getClass().getSimpleName();
	private AQuery aq;

	private TextView tvTitle;

	private WebView wv;
	private DataList mDataList;
	private ArrayList<DataChapter> mDataChapterList;
	private DataChapter mDataChapter;

	private View viewTop, viewBottom;
	private boolean showOverlayView = false;
	private boolean isDrag = false;
	
	private Animation aniShow, aniHide;
	private ImageButton btnPrev, btnNext;
	private View viewCommentRating;

	private int curPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_detail_activity);
		aq = new AQuery(this);

		getSupportActionBar().hide();

		/** UI Setting **/
		tvTitle = (TextView) findViewById(R.id.tvComicDetailTitle);
		btnPrev = (ImageButton) findViewById(R.id.btnComicDetailPrev);
		btnNext = (ImageButton) findViewById(R.id.btnComicDetailNext);
		viewTop = (View) findViewById(R.id.viewComicDetailTop);
		viewBottom = (View) findViewById(R.id.viewComicDetailBottom);
		viewCommentRating = (View) findViewById(R.id.viewComicDetailCommentRating);
		
		/** UI Event **/
		viewCommentRating.setOnClickListener(this);

		/** Animation **/
		// Setting 버튼 애니메이션
		aniShow = new AlphaAnimation(0, 1);
		aniShow.setDuration(250);
		aniHide = new AlphaAnimation(1, 0);
		aniHide.setDuration(250);

		/** 리스트에서 전달받은 DataChapterList 변수에 삽입 **/
		mDataList = new DataList(Pref.getJsonObject(mContext));
		mDataChapterList = mDataList.getDataChapters();

		Intent intent = getIntent();
		//		int chapterId = intent.getIntExtra("chapterId", 0);
		int position = intent.getIntExtra("position", 0);
		curPosition = position;
		mDataChapter = mDataChapterList.get(position);

		String url = C.API_DETAIL + Pref.getIdWork(mContext) + "/" + mDataChapter.getId();
		Log.d(TAG, "ChapterId : " + mDataChapter.getId());
		Log.d(TAG, "Chapter URL : " + url);
		tvTitle.setText(mDataChapter.getTitle());


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
		wv.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		Log.d(TAG, "Click : " + v.getId());
		switch(v.getId()){
		case R.id.viewComicDetailCommentRating:
			intent = new Intent(TiniCubeComicDetailActivity.this, TiniCubeCommentRatingActivity.class);
			int workId = mDataList.getDataWork().getId();
			int chapterId = mDataChapter.getId();
			intent.putExtra("workId", workId);
			intent.putExtra("chapterId", chapterId);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(v.getId()){
		case R.id.wvComicDetail:
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "Action_Down");
				isDrag = false;
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d(TAG, "Action_Move");
				isDrag = true;
				break;
			case MotionEvent.ACTION_UP:
				Log.d(TAG, "Action_Up");
				if(!isDrag){
					toggleView();
				}
				break;
			}
			break;
		}
		return false;
	}
	private void toggleView(){
		if(showOverlayView){
			showOverlayView = false;
			viewTop.startAnimation(aniHide);
			viewBottom.startAnimation(aniHide);
			viewTop.setVisibility(View.GONE);
			viewBottom.setVisibility(View.GONE);
		} else{
			showOverlayView = true;
			viewTop.startAnimation(aniShow);
			viewBottom.startAnimation(aniShow);
			viewTop.setVisibility(View.VISIBLE);
			viewBottom.setVisibility(View.VISIBLE);
		}
	}

}
