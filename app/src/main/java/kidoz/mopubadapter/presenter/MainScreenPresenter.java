package kidoz.mopubadapter.presenter;

/**
 * Created by orikam on 02/07/2017.
 */

public interface MainScreenPresenter
{
    void onCreate();
    void onDestroy();
    void onClick_LoadInterstitial();
    void onClick_ShowInterstitial();
    void onClick_LoadRewarded();
    void onClick_ShowRewarded();
    void onClick_LoadBanner();
    void onClick_ShowBanner();
}

