package com.tinicube.tinicubebase;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.tinicube.tinicubebase.data.DataImage;
import com.tinicube.tinicubebase.data.work.DataChapter;
import com.tinicube.tinicubebase.data.work.DataList;
import com.tinicube.tinicubebase.data.work.DataWork;
import com.tinicube.tinicubebase.function.Pref;

public class TiniCubeComicChapterListActivity extends TiniCubeBaseActivity implements OnItemClickListener{
	private final String TAG = this.getClass().getSimpleName();

	private View viewHeader, viewFooter;
	private ImageView ivComicCover;
	private TextView tvComicTitle, tvComicAuthor;
	private TextView tvComicDescription;
	private ListView lvComic;
	private AAComic mAAComic;
	
	private DataList mDataList;
	private DataWork mDataWork;
	private ArrayList<DataChapter> mDataChapterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_list_activity);

		mDataList = new DataList(Pref.getJsonObject(mContext));
		mDataWork = mDataList.getDataWork();
		mDataChapterList = mDataList.getDataChapters();

		// ActionBar
		mActionBar.hide();

		// ListView & Header
		lvComic = (ListView) findViewById(R.id.lvComic);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewHeader = (View) findViewById(R.id.viewComicHeader);
		viewFooter = inflater.inflate(R.layout.comic_list_footer, null);
		lvComic.addFooterView(viewFooter);

		mAAComic = new AAComic(mContext, R.layout.comic_list_item, mDataChapterList);
		lvComic.setAdapter(mAAComic);
		lvComic.setOnItemClickListener(this);

		ivComicCover = (ImageView) viewHeader.findViewById(R.id.ivComicCover);
		tvComicTitle = (TextView) viewHeader.findViewById(R.id.tvComicTitle);
		tvComicAuthor = (TextView) viewHeader.findViewById(R.id.tvComicAuthor);
		tvComicDescription = (TextView) viewHeader.findViewById(R.id.tvComicDescription);

		// Header - 커버 이미지
		DataImage curComicCover = mDataWork.getCover();
		String urlCoverImage = curComicCover.getUrl();
		LayoutParams coverParams = ivComicCover.getLayoutParams();
		coverParams.width = Pref.getDisplayWidth(mContext);
		//		coverParams.height = curComicCover.getHeightForResizedWidth(coverParams.width);
		coverParams.height = curComicCover.getFixedHeight(Pref.getDisplayWidth(mContext));
		ivComicCover.setLayoutParams(coverParams);

		float scale = curComicCover.getScaleForResizedWidth(coverParams.width);
		ImageOptions options = new ImageOptions();
		options.round = (int)(15 * scale);
		Log.d(TAG, "coverRound : " + options.round);
		aq.id(ivComicCover).progress(R.id.pbComicCover).image(urlCoverImage);

		// Header - 만화 제목, 설명
		tvComicTitle.setText(mDataWork.getTitle());
		tvComicAuthor.setText(mDataWork.getAuthor());
		tvComicDescription.setText(mDataWork.getDescription());
	}

	class AAComic extends ArrayAdapter<DataChapter>{
		private ArrayList<DataChapter> dataChapters;
		public AAComic(Context context, int textViewResourceId, ArrayList<DataChapter> dataChapters) {
			super(context, textViewResourceId, dataChapters);
			this.dataChapters = dataChapters;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View curView = convertView;
			if(curView==null){
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				curView = inflater.inflate(R.layout.comic_list_item, null);
			}

			DataChapter curChapter = dataChapters.get(position);

			String chapterTitle = curChapter.getTitle();
			String urlThumbnail = curChapter.getThumbnail().getUrl();
			String created = curChapter.getCreated();

			ImageView ivThumbnail = (ImageView) curView.findViewById(R.id.ivComicListItemThumbnail);
			TextView tvTitle = (TextView) curView.findViewById(R.id.tvComicListItemTitle);
			TextView tvCreated = (TextView) curView.findViewById(R.id.tvComicListItemDate);
			TextView tvRating = (TextView) curView.findViewById(R.id.tvComicListItemRating);

			aq.id(ivThumbnail).image(urlThumbnail);
			tvTitle.setText(chapterTitle);
			tvCreated.setText(created);
			
			// 왠지 레이팅바 Max값이 안변함 -_-;
			RatingBar rb = (RatingBar) curView.findViewById(R.id.rbComicListItem);
			float rating = (float) curChapter.getRating()/2;
			String strRating = String.format("%.1f", rating);
			float ratingFinal = Float.parseFloat(strRating);
			Log.d(TAG, "rating:" + rating);
			Log.d(TAG, "strRating:" + strRating);
			Log.d(TAG, "ratingFinal:" + ratingFinal);
			float rating2 = Math.round(rating*10);
			float rating3 = rating2 / 10;
			
//			Log.d(TAG, "rating2:" + rating2);
//			Log.d(TAG, "rating3:" + rating3);
			rb.setRating(ratingFinal);
			rb.setIsIndicator(true);
			rb.setFocusable(false);
			tvRating.setText("" + curChapter.getRating());

			return curView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Log.d(TAG, parent.getId() + " " + position);
		// Position 0, Last(Header, Footer) 일때는 작동하지않고, position-1값을 넘겨줌
		DataChapter curDataChapter = mDataChapterList.get(position);
		int chapterId = curDataChapter.getId();
		Intent intent = new Intent(TiniCubeComicChapterListActivity.this, TiniCubeComicChapterViewActivity.class);
		intent.putExtra("chapterId", chapterId);
		intent.putExtra("position", position);
		startActivity(intent);
	}

}