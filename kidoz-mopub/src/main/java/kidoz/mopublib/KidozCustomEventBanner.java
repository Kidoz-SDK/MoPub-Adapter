package kidoz.mopublib;

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

/**
 * Created by orikam on 03/07/2017.
 */

public class KidozCustomEventBanner extends CustomEventBanner
{
    private static final String TAG = "KidozCustomEventBanner";
    private static final BANNER_POSITION DEFAULT_BANNER_POSITION = BANNER_POSITION.BOTTOM;

    private CustomEventBannerListener mCustomEventBannerListener;
    private KidozManager mKidozManager;

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
            Log.d(TAG, "Kidoz | requestBannerAd with non Activity context");
            return;
        }

        Log.d(TAG, "KidozBannerAdapter | requestBannerAd called");

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized())
        {
            Log.d("ahmed", "KidozBannerAdapter | kidoz not init, initializing first");
            initKidoz((Activity) context);
        } else {
            Log.d("ahmed", "KidozBannerAdapter | kidoz already init");
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
        Log.d("ahmed", "KidozBannerAdapter | kidoz continueRequestBannerAd");
        if (mKidozManager.getBanner() == null)
        {
            Log.d("ahmed", "kidozBannerAdapter | banner not set up, calling view load.");
            setupKidozBanner(activity);
        }

        Log.d("ahmed", "KidozBannerAdapter | continueRequestBannerAd | calling load()");
        mKidozManager.getBanner().setLayoutWithoutShowing();
        mKidozManager.getBanner().load();
    }

    private void setupKidozBanner(final Activity activity)
    {
        Log.d("ahmed", "kidozBannerAdapter | kidozBannerView == null, calling view creation. START");
        mKidozManager.setupKidozBanner(activity, DEFAULT_BANNER_POSITION, new KidozBannerListener()
        {
            @Override
            public void onBannerReady()
            {
                //ask banner not to insert itself to view hierarchy, MoPub will do that in onAdLoaded(...)
                final KidozBannerView kbv = mKidozManager.getBanner();
                kbv.setBackgroundColor(Color.TRANSPARENT);

                kbv.show();
            }

            @Override
            public void onBannerViewAdded()
            {
                mCustomEventBannerListener.onBannerLoaded(mKidozManager.getBanner());
                mCustomEventBannerListener.onBannerExpanded();
            }

            @Override
            public void onBannerError()
            {
                mCustomEventBannerListener.onBannerFailed(MoPubErrorCode.NETWORK_TIMEOUT);
            }

            @Override
            public void onBannerClose()
            {
                mCustomEventBannerListener.onBannerCollapsed();
            }
        });
        Log.d("ahmed", "kidozBannerAdapter | kidozBannerView == null, calling view creation. END");
    }

    @Override
    protected void onInvalidate()
    {

    }
}
