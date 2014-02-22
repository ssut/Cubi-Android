package com.tinicube.tinicubebase.commentrating;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.tinicube.tinicubebase.data.work.DataChapterComment;
import com.tinicube.tinicubebase.function.BaseAsyncTask;
import com.tinicube.tinicubebase.function.C;

public class TiniCubeCommentRatingTask extends BaseAsyncTask {
	private ArrayList<DataChapterComment> mChapterCommentList;
	private TiniCubeChapterCommentArrayAdapter mAdapter;
	private int workId, chapterId;
	private String resultString;

	public TiniCubeCommentRatingTask(Context context, ArrayList<DataChapterComment> chapterCommentList, TiniCubeChapterCommentArrayAdapter adapter, String title, int workId, int chapterId) {
		super(context, title);
		mChapterCommentList = chapterCommentList;
		mAdapter = adapter;
		this.workId = workId;
		this.chapterId = chapterId;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		HashMap<String, String> valuePair = new HashMap<String, String>();
		valuePair.put("work_id", Integer.toString(workId));
		valuePair.put("chapter_id", Integer.toString(chapterId));
		resultString = postRequest(C.API_CHAPTER_COMMENTRATING_LIST, valuePair);
		return super.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		try {
			JSONObject jsonObject = new JSONObject(resultString);
			JSONArray jsonArray = jsonObject.getJSONArray("comments");
			mChapterCommentList.clear();
			for(int i=0; i<jsonArray.length(); i++){
				JSONObject curJsonObject = jsonArray.getJSONObject(i);
				DataChapterComment curChapterComment = new DataChapterComment(curJsonObject);
				mChapterCommentList.add(curChapterComment);
			}
			mAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(mContext, "통신 실패", Toast.LENGTH_SHORT).show();
		}
	}
	
	

}
