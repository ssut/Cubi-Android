package com.tinicube.tinicubebase.function;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BaseArrayAdapter<T> extends ArrayAdapter<T>{
	protected final String TAG = this.getClass().getName();
	protected Context mContext;
	private static Typeface mTypeface;

	public BaseArrayAdapter(Context context, int resource, ArrayList<T> objects) {
		super(context, resource, objects);
		mContext = context;
	}

	protected View getViewWithFont(LayoutInflater inflater, int layoutRes){
		if (BaseArrayAdapter.mTypeface == null)
			BaseArrayAdapter.mTypeface = Typeface.createFromAsset(mContext.getAssets(), "NanumBarunGothic.mp3");
		View view = inflater.inflate(layoutRes, null);
		setGlobalFont((ViewGroup) view);
		return view;
	}

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
