package kidoz.mopublib;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;

import java.util.Map;

/**
 * Created by orikam on 03/07/2017.
 */

public class KidozCustomEventRewardedVideo extends CustomEventRewardedVideo
{
    private static final String TAG = "KidozCustomEventRewardedVideo";
    private static final String KIDOZ_ID = "kidoz_id";
    private static final String DEFAULT_REWARD_NAME = "defaultReward";
    private static final int DEFAULT_REWARD_AMOUNT = 1;

    private KidozManager mKidozManager; //MoPub completely recreates this class every time you call load ad. the manager is set to static to preserve it from being destroyed and recreated together with this class
    private Runnable mOnKidozInitRunnable; //MoPub treats sdk init as synchronous (it starts loadAd directly after receiving synch return value from checkInitializeSDK). This Runnable is a workaround to load our ad when init finishes.

    public KidozCustomEventRewardedVideo() {

        if (mKidozManager == null)
        {
            //init Kidoz adapter helper
            Log.d(TAG, "Kidoz | KidozManager is null, calling KidozManager.getInstance()");
            mKidozManager = KidozManager.getInstance();
        }
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception
    {

        if (mKidozManager.getIsKidozInitialized()){
            return false; //notify sdk already initialized
        }

        //Set Kidoz Rewarded ad listener
        setKidozAd(launcherActivity);

        //init Kidoz SDK
        mKidozManager.initKidozSDK(launcherActivity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                Log.d(TAG, "Kidoz | SDK init success");

                if (mOnKidozInitRunnable != null)
                {
                    Log.d(TAG, "Kidoz | SDK init success | running onInit code");
                    mOnKidozInitRunnable.run();
                }
            }

            @Override
            public void onInitError(String error)
            {
                Log.d(TAG, "Kidoz | SDK init error. error = " + error);
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(KidozCustomEventRewardedVideo.class, KIDOZ_ID, MoPubErrorCode.NETWORK_INVALID_STATE);
            }
        });

        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception
    {
        if (!mKidozManager.getIsKidozInitialized())
        {
            mOnKidozInitRunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    //load Kidoz ad when SDK init finishes
                    loadKidozRewardedAd(activity);
                }
            };
            return;
        }

        loadKidozRewardedAd(activity);
    }

    private void loadKidozRewardedAd(Activity activity)
    {
        Log.d(TAG, "KidozRewarded | loadKidozRewardedAd()");
        KidozInterstitial rewardedInterstitial = mKidozManager.getRewarded();
        if (rewardedInterstitial != null){
            Log.d(TAG, "KidozRewarded | loadKidozRewardedAd() | loadAd()");
            rewardedInterstitial.loadAd();
        }
    }

    @Override
    protected void showVideo()
    {
        KidozInterstitial rewardedInterstitial = mKidozManager.getRewarded();
        if (rewardedInterstitial != null)
        {
            rewardedInterstitial.show();
        }
    }


    @Nullable
    @Override
    protected CustomEventRewardedVideoListener getVideoListenerForSdk()
    {
        return null; //CustomEventRewardedVideoListener is an implementation on how this plugin acts when notified of events from MoPub, we do nothing relevant here.
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener()
    {
        return null;
    }

    @NonNull
    @Override
    protected String getAdNetworkId()
    {
        return KIDOZ_ID;
    }

    @Override
    protected void onInvalidate()
    {

    }

    @Override
    protected boolean hasVideoAvailable()
    {
        return true;
    }


    private void setKidozAd(Activity activity)
    {
        mKidozManager.setupKidozRewadrded(activity, new BaseInterstitial.IOnInterstitialEventListener()
                {
                    @Override
                    public void onClosed()
                    {
                        MoPubRewardedVideoManager.onRewardedVideoClosed(KidozCustomEventRewardedVideo.class, KIDOZ_ID);
                        Log.d(TAG, "KidozRewarded | onAdClosed");
                    }

                    @Override
                    public void onOpened()
                    {
                        MoPubRewardedVideoManager.onRewardedVideoStarted(KidozCustomEventRewardedVideo.class, KIDOZ_ID);
                        Log.d(TAG, "KidozRewarded | onAdOpened");
                    }

                    @Override
                    public void onReady()
                    {
                        MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(KidozCustomEventRewardedVideo.class, KIDOZ_ID);
                        Log.d(TAG, "KidozRewarded | onAdReady");
                    }

                    @Override
                    public void onLoadFailed()
                    {
                        MoPubRewardedVideoManager.onRewardedVideoLoadFailure(KidozCustomEventRewardedVideo.class, KIDOZ_ID, MoPubErrorCode.VIDEO_DOWNLOAD_ERROR);
                        Log.d(TAG, "KidozRewarded | onLoadFailed");
                    }

                    @Override
                    public void onNoOffers()
                    {
                        MoPubRewardedVideoManager.onRewardedVideoLoadFailure(KidozCustomEventRewardedVideo.class, KIDOZ_ID, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                        Log.d(TAG, "KidozRewarded | onNoOffers");
                    }
                },
                new BaseInterstitial.IOnInterstitialRewardedEventListener()
                {
                    @Override
                    public void onRewardReceived()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardReceived();
                        }

                        //Note: Kidoz currently have no server to client reward exposure.
                        MoPubRewardedVideoManager.onRewardedVideoCompleted(KidozCustomEventRewardedVideo.class, KIDOZ_ID, MoPubReward.success(DEFAULT_REWARD_NAME, DEFAULT_REWARD_AMOUNT));
                        Log.d(TAG, "KidozRewarded | onRewardReceived");
                    }

                    @Override
                    public void onRewardedStarted()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardedStarted();
                        }

                        MoPubRewardedVideoManager.onRewardedVideoStarted(KidozCustomEventRewardedVideo.class, KIDOZ_ID);
                        Log.d(TAG, "KidozRewarded | onRewardedStarted");
                    }
                });
    }
}
