package com.tinicube.tinicubebase.commentrating;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.tinicube.tinicubebase.R;
import com.tinicube.tinicubebase.TiniCubeBaseActivity;
import com.tinicube.tinicubebase.R.id;
import com.tinicube.tinicubebase.data.work.DataChapterComment;

public class TiniCubeCommentRatingActivity extends TiniCubeBaseActivity {
	private final String TAG = this.getClass().getSimpleName();
	private final int INTENT_ERROR = 33333;
	private AQuery aq;
	
	private TextView tvAuthorName, tvAuthorComment, tvRating;
	private RatingBar rb;
	private EditText et;
	private ImageButton ibSendComment;
	
	private ListView lv;
	private ArrayList<DataChapterComment> mChapterCommentList;
	private TiniCubeChapterCommentArrayAdapter mAdapter;
	
	private int workId, chapterId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_rating_activity);
		aq = new AQuery(this);
		
		/** Intent **/
		Intent intent = getIntent();
		workId = intent.getIntExtra("workId", INTENT_ERROR);
		chapterId = intent.getIntExtra("chapterId", INTENT_ERROR);
		
		/** UI Connect **/
		tvAuthorName = (TextView) findViewById(R.id.tvCommentRatingAuthorName);
		tvAuthorComment = (TextView) findViewById(R.id.tvCommentRatingAuthorComment);
		tvRating = (TextView) findViewById(R.id.tvCommentRatingRating);
		rb = (RatingBar) findViewById(R.id.rbCommentRating);
		et = (EditText) findViewById(R.id.etCommentRating);
		ibSendComment = (ImageButton) findViewById(R.id.ibCommentRatingSendComment);
		
		/** ListView Setting **/
		lv = (ListView) findViewById(R.id.lvCommentRating);
		mChapterCommentList = new ArrayList<DataChapterComment>();
		mAdapter = new TiniCubeChapterCommentArrayAdapter(mContext, R.layout.chapter_comment_list_item, mChapterCommentList);
		lv.setAdapter(mAdapter);
		
		new TiniCubeCommentRatingTask(mContext, mChapterCommentList, mAdapter, "댓글 목록 & 평점 불러오는 중...", workId, chapterId).execute();
	}
	
	

}
