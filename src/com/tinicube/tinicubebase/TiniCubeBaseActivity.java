package com.tinicube.tinicubebase;

import com.androidquery.AQuery;

import android.os.Bundle;
import arcanelux.library.activity.AdlibrActionBarActivity;

public class TiniCubeBaseActivity extends AdlibrActionBarActivity {
	protected AQuery aq;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
	}
}
