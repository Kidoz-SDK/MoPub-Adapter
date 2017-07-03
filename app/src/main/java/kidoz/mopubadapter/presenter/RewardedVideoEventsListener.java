package kidoz.mopubadapter.presenter;

import android.support.annotation.NonNull;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;

import java.util.Set;

import kidoz.mopubadapter.view.MainScreenActivity;

/**
 * Created by orikam on 03/07/2017.
 */

public class RewardedVideoEventsListener implements MoPubRewardedVideoListener
{
    private MainScreenActivity mMainScreenActivity;

    public RewardedVideoEventsListener(MainScreenActivity mainScreenActivity)
    {
        mMainScreenActivity = mainScreenActivity;
    }

    @Override
    public void onRewardedVideoLoadSuccess(@NonNull String adUnitId)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoLoadSuccess");
    }

    @Override
    public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoLoadFailure | errorCode = " + errorCode);
    }

    @Override
    public void onRewardedVideoStarted(@NonNull String adUnitId)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoPlaybackError | errorCode = " + errorCode.toString());
    }

    @Override
    public void onRewardedVideoClosed(@NonNull String adUnitId)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoClosed");
    }

    @Override
    public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward)
    {
        mMainScreenActivity.showFeedBackText("onRewardedVideoCompleted | reward = " + reward.getAmount());
    }
}
