package kidoz.mopublib;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

/**
 * Created by orikam on 03/07/2017.
 */

public class KidozCustomEventInterstitial extends CustomEventInterstitial
{
    private static final String TAG = "KidozCustomEventInterstitial";

    private KidozManager mKidozManager;
    private CustomEventInterstitial.CustomEventInterstitialListener mMoPubCustomInterstitialListener;

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

        setKidozAd((Activity) context); //and then continue request

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized())
        {
            initKidoz((Activity) context);
        } else {
            continueRequestInterstitialAd();
        }
    }

    private void initKidoz(Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                continueRequestInterstitialAd();
            }

            @Override
            public void onInitError(String error)
            {
                mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.NETWORK_INVALID_STATE);
                Log.d(TAG, "Kidoz | onInitError: " + error);
            }
        });
    }

    private void setKidozAd(Activity activity)
    {
        mKidozManager.setupKidozInterstitial(activity, new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mMoPubCustomInterstitialListener.onInterstitialDismissed();
                Log.d(TAG, "Kidoz | onAdClosed");
            }

            @Override
            public void onOpened()
            {
                mMoPubCustomInterstitialListener.onInterstitialShown();
                Log.d(TAG, "Kidoz | onAdOpened");
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
                Log.d(TAG, "Kidoz | onLoadFailed");
            }

            @Override
            public void onNoOffers()
            {
                mMoPubCustomInterstitialListener.onInterstitialFailed(MoPubErrorCode.NETWORK_NO_FILL);
                Log.d(TAG, "Kidoz | onNoOffers");
            }
        });
    }

    private void continueRequestInterstitialAd()
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();
        kidozInterstitial.loadAd();
    }

    @Override
    public void showInterstitial()
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();
        kidozInterstitial.show();
    }

    @Override
    protected void onInvalidate()
    {

    }
}
