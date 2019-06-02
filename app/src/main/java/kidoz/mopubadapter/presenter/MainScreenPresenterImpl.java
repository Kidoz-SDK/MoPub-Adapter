package kidoz.mopubadapter.presenter;

import kidoz.mopubadapter.model.MoPubModel;
import kidoz.mopubadapter.view.MainScreenActivityImpl;

/**
 * Created by orikam on 07/06/2017.
 */

public class MainScreenPresenterImpl implements MainScreenPresenter
{
    private static final boolean MOPUB_IS_TESTING = false;

    private static final String MOPUB_INTERSTITIAL_NOT_LOADED = "The interstitial wasn't loaded yet.";
    private static final String MOPUB_REWADED_NOT_LOADED = "Rewarded video wasn't loaded yet.";

    private MainScreenActivityImpl mMainView;
    private MoPubModel mMoPubModel;

    public MainScreenPresenterImpl(MainScreenActivityImpl mainView)
    {
        mMainView = mainView;
        mMoPubModel = new MoPubModel();
    }

    @Override
    public void onCreate()
    {
        mMoPubModel.initMoPub(mMainView.getActivity());

        setupMoPubListeners();
    }

    private void setupMoPubListeners()
    {
        mMoPubModel.registerBannerListener(new BannerEventsListener(mMainView));
        mMoPubModel.registerInterstitialListener(new InterstitialEventsListener(mMainView));
        mMoPubModel.registerRewardedVideoListener(new RewardedVideoEventsListener(mMainView));
    }

    @Override
    public void onDestroy()
    {
        mMoPubModel.destroyBanner();
        mMoPubModel.destroyInterstitial();
    }

    @Override
    public void onClick_LoadInterstitial()
    {
        mMainView.showFeedBackText("MoPub | trying to load interstitial ad...");
        mMoPubModel.loadInterstitialAd();
    }

    @Override
    public void onClick_ShowInterstitial()
    {
        mMainView.showFeedBackText("MoPub | trying to show interstitial ad...");
        if (!mMoPubModel.showInterstitialAd())
        {
            mMainView.showFeedBackText("MoPub | interstitial is not ready yet.");
        }
    }

    @Override
    public void onClick_LoadRewarded()
    {
        mMainView.showFeedBackText("MoPub | trying to load rewarded video ad...");
        mMoPubModel.loadRewardedVideoAd();
    }

    @Override
    public void onClick_ShowRewarded()
    {
        mMainView.showFeedBackText("MoPub | trying to show rewarded video ad...");
        if (!mMoPubModel.showRewardedVideoAd())
        {
            mMainView.showFeedBackText("MoPub | There is no rewarded video to show at the moment.");
        }
    }

    @Override
    public void onClick_LoadBanner()
    {
        mMoPubModel.loadAndShowBanner();
    }



    @Override
    public void onClick_HideBanner()
    {
        mMoPubModel.hideBanner();
    }

    @Override
    public void onClick_ShowBanner()
    {

    }
}
