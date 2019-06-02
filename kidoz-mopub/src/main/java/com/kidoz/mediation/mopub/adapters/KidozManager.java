package com.kidoz.mediation.mopub.adapters;

import android.app.Activity;
import android.util.Log;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.KidozSDK;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.kidoz.sdk.api.ui_views.kidoz_banner.KidozBannerListener;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.BANNER_POSITION;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.KidozBannerView;

import java.util.Map;


public class KidozManager
{

    private KidozInterstitial mKidozInterstitial;
    private KidozInterstitial mKidozRewarded;
    private KidozBannerView mKidozBanner;
    private static final String TAG = "KidozManager";

    private static String mKidozPublisherId = "";// "5"; //default (test values)
    private static String mKidozPublisherToken = ""; //"i0tnrdwdtq0dm36cqcpg6uyuwupkj76s"; //default (test values)
    private static BaseInterstitial.IOnInterstitialRewardedEventListener mDeveloperRewardedListener;

    /************************
     *      Singleton       *
     ************************/

    private static KidozManager sInstance = null;

    synchronized public static KidozManager getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new KidozManager();
        }
        return sInstance;
    }

    private KidozManager(){}

    /****************************
     *      Developer API       *
     ****************************/
    public static void setKidozPublisherId(String publisherId){
        mKidozPublisherId = publisherId;
    }

    public static void setKidozPublisherToken(String publisherToken){
        mKidozPublisherToken = publisherToken;
    }

    public static void setRewardedEvents(BaseInterstitial.IOnInterstitialRewardedEventListener rewardedListener){
        mDeveloperRewardedListener = rewardedListener;
    }

    /****************************
     *     Adapter Internal     *
     ****************************/

    protected void setupKidozBanner(Activity activity, BANNER_POSITION bannerPosition, KidozBannerListener kidozBannerListener){
        mKidozBanner = KidozSDK.getKidozBanner(activity);
        mKidozBanner.setBannerPosition(bannerPosition);
        mKidozBanner.setKidozBannerListener(kidozBannerListener);
    }


    protected KidozBannerView getKidozBanner(Activity activity){
        return KidozSDK.getKidozBanner(activity);
    }

    protected void setupKidozBanner(KidozBannerView KidozBanner, BANNER_POSITION bannerPosition, KidozBannerListener kidozBannerListener){
        KidozBanner.setBannerPosition(bannerPosition);
        KidozBanner.setKidozBannerListener(kidozBannerListener);
    }


    protected void createKidozInterstitial(Activity activity){
        mKidozInterstitial = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.INTERSTITIAL);
    }

    protected void setupKidozInterstitial(KidozInterstitial kidozInterstitial, BaseInterstitial.IOnInterstitialEventListener interstitialListener){
        kidozInterstitial.setOnInterstitialEventListener(interstitialListener);
    }

    protected void setupKidozInterstitial(Activity activity, BaseInterstitial.IOnInterstitialEventListener interstitialListener){
        mKidozInterstitial = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.INTERSTITIAL);
        mKidozInterstitial.setOnInterstitialEventListener(interstitialListener);
    }

    protected void setupKidozRewadrded(Activity activity, BaseInterstitial.IOnInterstitialEventListener interstitialListener, BaseInterstitial.IOnInterstitialRewardedEventListener rewardedListener){
        mKidozRewarded = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.REWARDED_VIDEO);
        mKidozRewarded.setOnInterstitialEventListener(interstitialListener);
        mKidozRewarded.setOnInterstitialRewardedEventListener(rewardedListener);
    }


    protected void createKidozRewadrded(Activity activity){
        mKidozRewarded = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.REWARDED_VIDEO);
    }


    protected void setupKidozRewadrded(KidozInterstitial mKidozRewarded, BaseInterstitial.IOnInterstitialEventListener interstitialListener, BaseInterstitial.IOnInterstitialRewardedEventListener rewardedListener){
        mKidozRewarded.setOnInterstitialEventListener(interstitialListener);
        mKidozRewarded.setOnInterstitialRewardedEventListener(rewardedListener);
    }


    protected void initKidozSDK(Activity activity, SDKEventListener sdkEventsListener)
    {
        KidozSDK.setSDKListener(sdkEventsListener);
        KidozSDK.initialize(activity, mKidozPublisherId, mKidozPublisherToken);
    }

    protected boolean getIsKidozInitialized(){
        return KidozSDK.isInitialised();
    }

    protected KidozBannerView getBanner() { return mKidozBanner; }

    protected KidozInterstitial getInterstitial(){
        return mKidozInterstitial;
    }

    protected KidozInterstitial getRewarded(){
        return mKidozRewarded;
    }

    protected BaseInterstitial.IOnInterstitialRewardedEventListener getDeveloperRewardedListener(){
        return mDeveloperRewardedListener;
    }


    protected static String getPublisherIdFromParams(Map<String, String> serverExtras) {
        String appID = null;
        if (serverExtras != null && !serverExtras.isEmpty()) {
            try
            {
                appID = serverExtras.get("AppID");
                if (appID == null)
                    Log.d(TAG, "Kidoz | PublisherId parameter - Token AppID");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Kidoz | PublisherId parameter error");
            }
        }else {
            Log.d(TAG, "Kidoz | PublisherId parameter empty");
        }
        return appID;
    }


    protected static String getPublisherTokenFromParams(Map<String, String> serverExtras) {
        String token = "";
        if (serverExtras != null && !serverExtras.isEmpty()) {
            try
            {
                token = serverExtras.get("Token");
                if (token == null)
                    Log.d(TAG, "Kidoz | PublisherToken parameter - Token error");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Kidoz | PublisherToken parameter error");
            }

        }else {
            Log.d(TAG, "Kidoz | PublisherToken parameter empty");
        }
        return token;
    }

}
