package com.tinicube.tinicubebase.commentrating;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tinicube.tinicubebase.R;
import com.tinicube.tinicubebase.data.work.DataChapterComment;
import com.tinicube.tinicubebase.function.BaseArrayAdapter;

public class TiniCubeChapterCommentArrayAdapter extends BaseArrayAdapter<DataChapterComment>{
	private ArrayList<DataChapterComment> mChapterCommentList;
	private int res;

	public TiniCubeChapterCommentArrayAdapter(Context context, int resource, ArrayList<DataChapterComment> objects) {
		super(context, resource, objects);
		this.mChapterCommentList = objects;
		this.res = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View curView = convertView;
		if(curView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			curView = getViewWithFont(inflater, res);
		}
		
		DataChapterComment curChapterComment = mChapterCommentList.get(position);
		String nickname = curChapterComment.getAuthor().getNickname();
		String date = curChapterComment.getCreatedDate() + " " + curChapterComment.getCreatedTime();
		String content = curChapterComment.getContent();
		
		TextView tvNickname = (TextView) curView.findViewById(R.id.tvChapterCommentListItemNickname);
		TextView tvDate = (TextView) curView.findViewById(R.id.tvChapterCommentListItemDate);
		TextView tvContent = (TextView) curView.findViewById(R.id.tvChapterCommentListItemContent);
		
		tvNickname.setText(nickname);
		tvDate.setText(date);
		tvContent.setText(content);
		
		return curView;
	}
	
	

}
