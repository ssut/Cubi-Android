package com.tinicube.tinicubebase.ads;

/*
 * adlibr - Library for mobile AD mediation.
 * http://adlibr.com
 * Copyright (c) 2012-2013 Mocoplex, Inc.  All rights reserved.
 * Licensed under the BSD open source license.
 */

/*
 * confirmed compatible with ad@m SDK 2.2.3.3
 */


import net.daum.adam.publisher.AdInterstitial;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.impl.AdError;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.mocoplex.adlib.AdlibManager;
import com.mocoplex.adlib.SubAdlibAdViewCore;
import com.tinicube.tinicubebase.R;

public class SubAdlibAdViewAdam extends SubAdlibAdViewCore  {
	private String adAdam;
	protected net.daum.adam.publisher.AdView ad;
	protected boolean bGotAd = false;
	
	// 여기에 ADAM ID 를 입력하세요.
	
	static String adamID;
    static String adamInterstitialID = "ADAM_INTERSTITIAL_ID";

	public SubAdlibAdViewAdam(Context context) {
		this(context,null);
	}	
	
	public SubAdlibAdViewAdam(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initAdamView();
	}
	
	public void initAdamView()
	{
		adamID = getResources().getString(R.string.ad_adam);
		ad = new net.daum.adam.publisher.AdView(this.getContext());
		
		// 할당 받은 clientId 설정
		ad.setClientId(adamID);
		// 광고 갱신 시간 : 기본 60초
		ad.setRequestInterval(12);
        
        int adHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48.0, getResources().getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, adHeight);
		ad.setLayoutParams(params);
		
		ad.setOnAdLoadedListener(new OnAdLoadedListener() {
			@Override
			public void OnAdLoaded() {
				
				bGotAd = true;
				// 광고를 받아왔으면 이를 알려 화면에 표시합니다.
				gotAd();
			} });
		
		ad.setOnAdFailedListener(new OnAdFailedListener() {
			@Override
			public void OnAdFailed(AdError arg0, String arg1) {
				
				bGotAd = true;
				failed();
			} });

		this.addView(ad);
	}
	
	// 스케줄러에의해 자동으로 호출됩니다.
	// 실제로 광고를 보여주기 위하여 요청합니다.	
	public void query()
	{
		bGotAd = false;
		
		if(ad == null)
			initAdamView();
		
		queryAd();

		ad.resume();
		
		// 3초 이상 리스너 응답이 없으면 다음 플랫폼으로 넘어갑니다.
		Handler adHandler = new Handler();
		adHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if(bGotAd)
					return;
				else
				{
					if(ad != null)
						ad.pause();
					failed();
				}
			}
				
		}, 3000);
	}

	// 광고뷰가 사라지는 경우 호출됩니다. 
	public void clearAdView()
	{
		if(ad != null)
		{
			this.removeView(ad);
			ad.destroy();
			ad = null;
		}

		super.clearAdView();
	}
	
	public void onResume()
	{		
		if(ad != null)
		{
			ad.resume();
		}
        
        super.onResume();
	}
	public void onPause()
	{
		if(ad != null)
		{
			ad.pause();
		}
        
        super.onPause();
	}
	public void onDestroy()
	{
		if(ad != null)
		{
			ad.destroy();
			ad = null;
		}
        
        super.onDestroy();
	}
	
	public static void loadInterstitial(Context ctx, final Handler h)
	{
		AdInterstitial mAdInterstitial = new AdInterstitial((Activity)ctx);
 	    mAdInterstitial.setClientId(adamInterstitialID);
 	    mAdInterstitial.setOnAdLoadedListener(new OnAdLoadedListener() {

 			@Override
 			public void OnAdLoaded() {
	 			try
	 			{
	 				if(h != null)
	 				{
	 					h.sendMessage(Message.obtain(h, AdlibManager.DID_SUCCEED, "ADAM"));
	 				}
	 			}
	 			catch(Exception e)
	 			{
	 					
	 			}
 					
 			}
 	    });
 	    mAdInterstitial.setOnAdFailedListener(new OnAdFailedListener() {

 			@Override
 			public void OnAdFailed(AdError arg0, String arg1) {
 					
	 			try
	 			{
	 				if(h != null)
	 				{
	 	 				h.sendMessage(Message.obtain(h, AdlibManager.DID_ERROR, "ADAM"));
	 				}
	 			}
	 			catch(Exception e)
	 			{
	 					
	 			}
 			}
 	    });
 	    mAdInterstitial.setOnAdClosedListener(new OnAdClosedListener() {

 			@Override
 			public void OnAdClosed() {
 					
	 			try
	 			{
	 				if(h != null)
	 				{
	 					h.sendMessage(Message.obtain(h, AdlibManager.INTERSTITIAL_CLOSED, "ADAM"));
	 				}
	 			}
	 			catch(Exception e)
	 			{
	 					
	 			}
 				
 			}
 	    });
 	    	
 	    mAdInterstitial.loadAd();		
	}
}