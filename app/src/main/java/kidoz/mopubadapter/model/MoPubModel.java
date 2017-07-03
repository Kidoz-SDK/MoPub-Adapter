package kidoz.mopubadapter.model;

import android.app.Activity;
import android.util.Log;

import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;

import kidoz.mopubadapter.R;
import kidoz.mopubadapter.presenter.BannerEventsListener;
import kidoz.mopubadapter.presenter.InterstitialEventsListener;

/**
 * Created by orikam on 02/07/2017.
 */

public class MoPubModel
{
    private static final String TAG = MoPubModel.class.getSimpleName();

    private static final String PROBLEM_REGISTERING_BANNER_LISTENER = "There was a problem registering a banner listener.";
    private static final String PROBLEM_REGISTERING_INTERSTITIAL_LISTENER = "There was a problem registering an interstitial listener.";
    private static final String PROBLEM_REGISTERING_REWARDED_VIDEO_LISTENER = "There was a problem registering an rewarded video listener.";

    private static final String MOPUB_BANNER_ADUNIT_ID = "96c0d688738f4603b7399fe88cf19bfc";
    private static final String MOPUB_INTERSTITIAL_ADUNIT_ID = "a8ffd55af03a405493cb153fc4a716be";
    private static final String MOPUB_REWARDED_VIDEO_ADUNIT_ID = "2fc367600e1d4452a920e9ce78bf27a7";

    private MoPubView mMopubView;
    private MoPubInterstitial mInterstitial;

    public void initMoPub(Activity activity)
    {
        initBanner(activity);
        initInterstitial(activity);
        initRewardedVideo(activity);
    }

    /******************
     * REWARDED VIDEO *
     ******************/

    private void initRewardedVideo(Activity activity)
    {
        MoPubRewardedVideos.initializeRewardedVideo(activity);
    }

    public void loadRewardedVideoAd()
    {
        MoPubRewardedVideos.loadRewardedVideo(MOPUB_REWARDED_VIDEO_ADUNIT_ID);
    }

    public boolean showRewardedVideoAd()
    {
        if (!MoPubRewardedVideos.hasRewardedVideo(MOPUB_REWARDED_VIDEO_ADUNIT_ID))
        {
            return false;
        }

        MoPubRewardedVideos.showRewardedVideo(MOPUB_REWARDED_VIDEO_ADUNIT_ID);
        return true;
    }

    public void registerRewardedVideoListener(MoPubRewardedVideoListener rewardedVideoEventsListener)
    {
        if (mInterstitial == null || rewardedVideoEventsListener == null)
        {
            Log.e(TAG, PROBLEM_REGISTERING_REWARDED_VIDEO_LISTENER);
            return;
        }
        MoPubRewardedVideos.setRewardedVideoListener(rewardedVideoEventsListener);
    }

    /****************
     * INTERSTITIAL *
     ****************/

    public void loadInterstitialAd()
    {
        mInterstitial.load();
    }

    public boolean showInterstitialAd()
    {
        if (!mInterstitial.isReady())
        {
            return false;
        }

        mInterstitial.show();
        return true;
    }

    private void initInterstitial(Activity activity)
    {
        mInterstitial = new MoPubInterstitial(activity, MOPUB_INTERSTITIAL_ADUNIT_ID);
    }

    public void registerInterstitialListener(InterstitialEventsListener interstitialEventsListener)
    {
        if (mInterstitial == null || interstitialEventsListener == null)
        {
            Log.e(TAG, PROBLEM_REGISTERING_INTERSTITIAL_LISTENER);
            return;
        }
        mInterstitial.setInterstitialAdListener(interstitialEventsListener);
    }

    public void destroyInterstitial()
    {
        if (mInterstitial != null)
        {
            mInterstitial.destroy();
        }
    }

    /**********
     * BANNER *
     **********/

    public void loadAndShowBanner(){
        mMopubView.loadAd();
    }

    public void registerBannerListener(BannerEventsListener bannerEventsListener)
    {
        if (mMopubView == null || bannerEventsListener == null)
        {
            Log.e(TAG, PROBLEM_REGISTERING_BANNER_LISTENER);
            return;
        }
        mMopubView.setBannerAdListener(bannerEventsListener);
    }

    public void destroyBanner(){
        if (mMopubView != null)
        {
            mMopubView.destroy();
        }
    }

    private void initBanner(Activity activity)
    {
        mMopubView = activity.findViewById(R.id.adview);
        mMopubView.setAdUnitId(MOPUB_BANNER_ADUNIT_ID);
    }

}
