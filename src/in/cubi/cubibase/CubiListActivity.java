package in.cubi.cubibase;

import in.cubi.cubibase.data.DataChapter;
import in.cubi.cubibase.data.DataImage;
import in.cubi.cubibase.data.DataList;
import in.cubi.cubibase.data.DataWork;
import in.cubi.cubibase.function.Pref;

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
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

public class CubiListActivity extends CubiBaseActivity implements OnItemClickListener{
	private final String TAG = this.getClass().getSimpleName();
	private Context mContext;
	private AQuery aq;
	
	private View viewHeader, viewFooter;
	private ImageView ivComicCover;
	private TextView tvComicTitle;
	private TextView tvComicDescription;
	private ListView lvComic;
	private AAComic mAAComic;
	
	private ActionBar mActionBar;
	
	private DataList mDataList;
	private DataWork mDataWork;
	private ArrayList<DataChapter> mDataChapterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic);
		mContext = this;
		aq = new AQuery(this);
		
		mDataList = new DataList(Pref.getJsonObject(mContext));
		mDataWork = mDataList.getDataWork();
		mDataChapterList = mDataList.getDataChapters();
		
		// ActionBar
		mActionBar = getSupportActionBar();
		mActionBar.hide();
		
		// ListView & Header
		lvComic = (ListView) findViewById(R.id.lvComic);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewHeader = inflater.inflate(R.layout.comic_header, null);
		lvComic.addHeaderView(viewHeader);
		viewFooter = inflater.inflate(R.layout.comic_footer, null);
		lvComic.addFooterView(viewFooter);
		
		mAAComic = new AAComic(mContext, R.layout.comic_list_item, mDataChapterList);
		lvComic.setAdapter(mAAComic);
		lvComic.setOnItemClickListener(this);
		
		ivComicCover = (ImageView) viewHeader.findViewById(R.id.ivComicCover);
		tvComicTitle = (TextView) viewHeader.findViewById(R.id.tvComicTitle);
		tvComicDescription = (TextView) viewHeader.findViewById(R.id.tvComicDescription);
		
		// Header - 커버 이미지
		DataImage curComicCover = mDataWork.getCover();
		String urlCoverImage = curComicCover.getUrl();
		LayoutParams coverParams = ivComicCover.getLayoutParams();
		coverParams.width = Pref.getDisplayWidth(mContext);
		coverParams.height = curComicCover.getHeightForResizedWidth(coverParams.width);
		ivComicCover.setLayoutParams(coverParams);
		
		float scale = curComicCover.getScaleForResizedWidth(coverParams.width);
		ImageOptions options = new ImageOptions();
		options.round = (int)(15 * scale);
		Log.d(TAG, "coverRound : " + options.round);
		aq.id(ivComicCover).progress(R.id.pbComicCover).image(urlCoverImage);
		
		// Header - 만화 제목, 설명
		tvComicTitle.setText(mDataWork.getTitle());
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
			
			aq.id(ivThumbnail).image(urlThumbnail);
			tvTitle.setText(chapterTitle);
			tvCreated.setText(created);
			
			return curView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Position 0, Last(Header, Footer) 일때는 작동하지않고, position-1값을 넘겨줌
		if(parent.getId()==R.id.lvComic && position!=0 && position!=mDataChapterList.size()+1){
//			Intent intent = new Intent(CubiListActivity.this, ThirdpubCrosspopDetail3Activity.class);
//			intent.putExtra("position", position-1);
//			startActivity(intent);
//			new DownloadTask(mDescription.getDataChapters().get(position), mContext).execute();
		}
	}
	
}