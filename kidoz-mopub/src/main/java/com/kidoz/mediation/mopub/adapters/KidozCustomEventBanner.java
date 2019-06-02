package com.kidoz.mediation.mopub.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.kidoz_banner.KidozBannerListener;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.BANNER_POSITION;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.KidozBannerView;
import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;


public class KidozCustomEventBanner extends CustomEventBanner
{
    private static final String TAG = "KidozCustomEventBanner";
    private static final BANNER_POSITION DEFAULT_BANNER_POSITION = BANNER_POSITION.BOTTOM;

    private CustomEventBannerListener mCustomEventBannerListener;
    private KidozManager mKidozManager;
    private KidozBannerView mKidozBanner;

    public KidozCustomEventBanner()
    {
        mKidozManager = KidozManager.getInstance();
    }

    @Override
    protected void loadBanner(Context context, CustomEventBannerListener customEventBannerListener, Map<String, Object> localExtras, Map<String, String> serverExtras)
    {
         mCustomEventBannerListener = customEventBannerListener;

        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            mCustomEventBannerListener.onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
            Log.d(TAG, "KidozBannerAdapter | requestBannerAd with non Activity context");
            return;
        }

        Log.d(TAG, "KidozBannerAdapter | requestBannerAd called");

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized()) {

            String appID = mKidozManager.getPublisherIdFromParams(serverExtras);
            String token = mKidozManager.getPublisherTokenFromParams(serverExtras);

            if(appID!=null && token!=null && !appID.equals("") && !token.equals("")) {
                mKidozManager.setKidozPublisherId(appID);
                mKidozManager.setKidozPublisherToken(token);
                Log.d(TAG, "KidozBannerAdapter | kidoz not init, initializing first");
                initKidoz((Activity) context);
            }


        } else {
            Log.d(TAG, "KidozBannerAdapter | kidoz already init");
            continueRequestBannerAd((Activity) context);
        }
    }

    private void initKidoz(final Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                continueRequestBannerAd(activity);
            }

            @Override
            public void onInitError(String error)
            {
                mCustomEventBannerListener.onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
                Log.d(TAG, "Kidoz | onInitError: " + error);
            }
        });
    }


    private void continueRequestBannerAd(Activity activity)
    {
        if(mKidozBanner == null)
          mKidozBanner = mKidozManager.getKidozBanner(activity);
        mKidozManager.setupKidozBanner(mKidozBanner, DEFAULT_BANNER_POSITION, new KidozBannerListener()
        {
            @Override
            public void onBannerReady()
            {
                //ask banner not to insert itself to view hierarchy, MoPub will do that in onAdLoaded(...)
                final KidozBannerView kbv = mKidozBanner;
                kbv.setBackgroundColor(Color.TRANSPARENT);
                mCustomEventBannerListener.onBannerLoaded(kbv);
                kbv.show();
                Log.d(TAG, "kidozBannerAdapter | onBannerReady");
            }

            @Override
            public void onBannerViewAdded()
            {
                //final KidozBannerView kbv = mKidozManager.getBanner();
//                mKidozManager.getBanner().bringToFront();
//                mKidozManager.getBanner().invalidate();
                Log.d(TAG, "kidozBannerAdapter | onBannerViewAdded");
            }

            @Override
            public void onBannerError(String errorMsg)
            {
                Log.e(TAG, errorMsg);
                mCustomEventBannerListener.onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
                Log.d(TAG, "kidozBannerAdapter | onBannerError " + errorMsg);
            }

            @Override
            public void onBannerClose()
            {
                Log.d(TAG, "kidozBannerAdapter | onBannerClose");
            }

            @Override
            public void onBannerNoOffers()
            {
                Log.d(TAG, "kidozBannerAdapter | onBannerNoOffers");
            }
        });

        mKidozBanner.setLayoutWithoutShowing();
        mKidozBanner.load();

    }


    @Override
    protected void onInvalidate()
    {
        if(mKidozBanner!=null)
             mKidozBanner.destroy();

        Log.d(TAG, "kidozBannerAdapter | onInvalidate + hide  ");
    }

    private void resetHide()
    {
        if (mKidozManager.getBanner() != null)
        {
            mKidozManager.getBanner().hide();
        }
    }
}
