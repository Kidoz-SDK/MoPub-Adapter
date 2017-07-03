package kidoz.mopubadapter.presenter;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

import kidoz.mopubadapter.view.MainScreenActivity;

/**
 * Created by orikam on 02/07/2017.
 */

public class BannerEventsListener implements BannerAdListener
{
    private MainScreenActivity mMainScreenActivity;
    public BannerEventsListener(MainScreenActivity mainScreenActivity)
    {
        mMainScreenActivity = mainScreenActivity;
    }

    @Override
    public void onBannerLoaded(MoPubView banner)
    {
        mMainScreenActivity.showFeedBackText("onBannerLoaded");
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode)
    {
        mMainScreenActivity.showFeedBackText("onBannerFailed | errorCode = " + errorCode.toString());
    }

    @Override
    public void onBannerClicked(MoPubView banner)
    {
        mMainScreenActivity.showFeedBackText("onBannerClicked");
    }

    @Override
    public void onBannerExpanded(MoPubView banner)
    {
        mMainScreenActivity.showFeedBackText("onBannerExpanded");
    }

    @Override
    public void onBannerCollapsed(MoPubView banner)
    {
        mMainScreenActivity.showFeedBackText("onBannerCollapsed");
    }
}
