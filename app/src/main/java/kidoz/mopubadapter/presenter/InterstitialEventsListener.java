package kidoz.mopubadapter.presenter;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import kidoz.mopubadapter.view.MainScreenActivity;

/**
 * Created by orikam on 02/07/2017.
 */

public class InterstitialEventsListener implements MoPubInterstitial.InterstitialAdListener
{
    private MainScreenActivity mMainScreenActivity;
    public InterstitialEventsListener(MainScreenActivity mainScreenActivity)
    {
        mMainScreenActivity = mainScreenActivity;
    }


    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial)
    {
        mMainScreenActivity.showFeedBackText("onInterstitialLoaded");
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode)
    {
        mMainScreenActivity.showFeedBackText("onInterstitialFailed | errorCode = " + errorCode.toString());
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial)
    {
        mMainScreenActivity.showFeedBackText("onInterstitialShown");
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial)
    {
        mMainScreenActivity.showFeedBackText("onInterstitialClicked");
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial)
    {
        mMainScreenActivity.showFeedBackText("onInterstitialDismissed");
    }
}
