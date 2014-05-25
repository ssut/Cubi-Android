package com.tinicube.tinicubebase;

import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;

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
import android.widget.Toast;

import com.androidquery.AQuery;
import com.tinicube.tinicubebase.commentrating.TiniCubeCommentRatingActivity;
import com.tinicube.tinicubebase.data.work.DataChapter;
import com.tinicube.tinicubebase.data.work.DataList;
import com.tinicube.tinicubebase.function.C;
import com.tinicube.tinicubebase.function.Pref;

public class TiniCubeComicChapterViewActivity extends TiniCubeBaseActivity implements OnClickListener, OnTouchListener {
	private final String TAG = this.getClass().getSimpleName();

	private TextView tvTitle;

	private WebView wv;
	private DataList mDataList;
	private ArrayList<DataChapter> mDataChapterList;
	private DataChapter mDataChapter;

	private View viewTop, viewBottom;
	private boolean showOverlayView = false;
	private boolean isDrag = false;
	
	private Animation aniShow, aniHide;
	private ImageButton ibPrev, ibNext, ibShare;
	private View viewCommentRating;

	private int curPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_view_activity);

		mActionBar.hide();
		
		/** UI Setting **/
		tvTitle = (TextView) findViewById(R.id.tvComicViewTitle);
		ibPrev = (ImageButton) findViewById(R.id.ibComicViewPrev);
		ibNext = (ImageButton) findViewById(R.id.ibComicViewNext);
		ibShare = (ImageButton) findViewById(R.id.ibComicViewShare);
		viewTop = (View) findViewById(R.id.viewComicViewTop);
		viewBottom = (View) findViewById(R.id.viewComicViewBottom);
		viewCommentRating = (View) findViewById(R.id.viewComicViewCommentRating);
		
		/** UI Event **/
		ibPrev.setOnClickListener(this);
		ibNext.setOnClickListener(this);
		viewCommentRating.setOnClickListener(this);

		/** Animation **/
		// Setting 버튼 애니메이션
		aniShow = new AlphaAnimation(0, 1);
		aniShow.setDuration(250);
		aniHide = new AlphaAnimation(1, 0);
		aniHide.setDuration(250);

		/** 리스트에서 전달받은 DataList 변수에 삽입, 선택된 DataChapter를 position값과 전체 List로부터 찾아냄 **/
		mDataList = new DataList(Pref.getJsonObject(mContext));
		mDataChapterList = mDataList.getDataChapters();

		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 0);
		curPosition = position;
		mDataChapter = mDataChapterList.get(position);

		String postData = "work_id=" + Pref.getIdWork(mContext) + "&chapter_id=" + mDataChapter.getId();
		Log.d(TAG, "postData : " + postData);
		setChapterTitle(mDataChapter.getTitle());

		/** WebView **/
		wv = (WebView) findViewById(R.id.wvComicView);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				setChapterTitle(url);
				return false;
			}
		});
		wv.postUrl(C.API_CHAPTER_VIEW, EncodingUtils.getBytes(postData, "BASE64"));
		wv.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		Log.d(TAG, "Click : " + v.getId());
		int id = v.getId();
		if (id == R.id.viewComicViewCommentRating) {
			intent = new Intent(TiniCubeComicChapterViewActivity.this, TiniCubeCommentRatingActivity.class);
			int workId = mDataList.getDataWork().getId();
			int chapterId = mDataChapter.getId();
			intent.putExtra("workId", workId);
			intent.putExtra("chapterId", chapterId);
			startActivity(intent);
		} else if (id == R.id.ibComicViewNext) {
			if(curPosition<=0){
				Toast.makeText(mContext, "마지막 화 입니다", Toast.LENGTH_SHORT).show();
			} else{
				curPosition -= 1;
				DataChapter curChapter = mDataChapterList.get(curPosition);
				String postData = "work_id=" + Pref.getIdWork(mContext) + "&chapter_id=" + curChapter.getId();
				wv.postUrl(C.API_CHAPTER_VIEW, EncodingUtils.getBytes(postData, "BASE64"));
				setChapterTitle(curChapter.getTitle());
			}
		} else if (id == R.id.ibComicViewPrev) {
			if(curPosition>=mDataChapterList.size()-1){
				Toast.makeText(mContext, "첫 화 입니다", Toast.LENGTH_SHORT).show();
			} else{
				curPosition += 1;
				DataChapter curChapter = mDataChapterList.get(curPosition);
				String postData = "work_id=" + Pref.getIdWork(mContext) + "&chapter_id=" + curChapter.getId();
				wv.postUrl(C.API_CHAPTER_VIEW, EncodingUtils.getBytes(postData, "BASE64"));
				setChapterTitle(curChapter.getTitle());
			}
		} else if (id == R.id.ibComicViewShare) {
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int id = v.getId();
		if (id == R.id.wvComicView) {
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
//				Log.d(TAG, "Action_Down");
				isDrag = false;
				break;
			case MotionEvent.ACTION_MOVE:
//				Log.d(TAG, "Action_Move");
				isDrag = true;
				break;
			case MotionEvent.ACTION_UP:
//				Log.d(TAG, "Action_Up");
				if(!isDrag){
					toggleView();
				}
				break;
			}
		}
		return false;
	}
	
	private void setChapterTitle(String title){
//		tvTitle.setText(mDataChapter.getTitle());
		tvTitle.setText(title);
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
