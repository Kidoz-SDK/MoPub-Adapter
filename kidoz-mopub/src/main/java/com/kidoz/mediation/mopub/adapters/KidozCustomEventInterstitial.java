package com.kidoz.mediation.mopub.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;


public class KidozCustomEventInterstitial extends CustomEventInterstitial
{
    private static final String TAG = "KidozCustomEventInterstitial";

    private KidozManager mKidozManager;
    private CustomEventInterstitialListener mMoPubCustomInterstitialListener;

    public KidozCustomEventInterstitial() {
        mKidozManager = KidozManager.getInstance();
    }

    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> localExtras, Map<String, String> serverExtras)
    {
        mMoPubCustomInterstitialListener = customEventInterstitialListener;

        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.INTERNAL_ERROR);
            Log.d(TAG, "Kidoz | requestInterstitialAd with non Activity context");
            return;
        }

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized()) {

            String appID = mKidozManager.getPublisherIdFromParams(serverExtras);
            String token = mKidozManager.getPublisherTokenFromParams(serverExtras);

            if(appID!=null && token!=null && !appID.equals("") && !token.equals("")) {
                mKidozManager.setKidozPublisherId(appID);
                mKidozManager.setKidozPublisherToken(token);
                initKidoz((Activity) context);
            }


        } else {
            continueRequestInterstitialAd((Activity) context);
        }
    }

    private void initKidoz(final Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                continueRequestInterstitialAd(activity);
                Log.d(TAG, "kidozInterstitialAdapter | onInitSuccess");

            }

            @Override
            public void onInitError(String error)
            {
                mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.NETWORK_INVALID_STATE);
                Log.d(TAG, "kidozInterstitialAdapter | onInitError: " + error);
            }
        });
    }



    private void setKidozAd()
    {
        mKidozManager.setupKidozInterstitial(mKidozManager.getInterstitial(), new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mMoPubCustomInterstitialListener.onInterstitialDismissed();
                Log.d(TAG, "kidozInterstitialAdapter | onAdClosed");
            }

            @Override
            public void onOpened()
            {
                mMoPubCustomInterstitialListener.onInterstitialShown();
                Log.d(TAG, "kidozInterstitialAdapter | onAdOpened");
            }

            @Override
            public void onReady()
            {
                mMoPubCustomInterstitialListener.onInterstitialLoaded();
                Log.d(TAG, "Kidoz | onAdReady");
            }

            @Override
            public void onLoadFailed()
            {
                mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.INTERNAL_ERROR);
                Log.d(TAG, "kidozInterstitialAdapter | onLoadFailed");
            }

            @Override
            public void onNoOffers()
            {
                mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.NETWORK_NO_FILL);
                Log.d(TAG, "kidozInterstitialAdapter | onNoOffers");
            }
        });
    }


    private void continueRequestInterstitialAd(Activity activity)
    {
        if(mKidozManager.getInterstitial() == null)
            mKidozManager.createKidozInterstitial(activity);

        setKidozAd();

        Log.d(TAG, "kidozInterstitialAdapter | continueRequestInterstitialAd");
        KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();

        if(!kidozInterstitial.isLoaded())
            kidozInterstitial.loadAd();
        else
            mMoPubCustomInterstitialListener.onInterstitialLoaded();

    }


    @Override
    public void showInterstitial()
    {
        Log.d(TAG, "kidozInterstitialAdapter | showInterstitial");

         KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();
         kidozInterstitial.show();
    }

    @Override
    protected void onInvalidate()
    {
        Log.d(TAG, "kidozInterstitialAdapter | onInvalidate");

    }
}
