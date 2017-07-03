package kidoz.mopubadapter.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kidoz.mopubadapter.R;
import kidoz.mopubadapter.presenter.MainScreenPresenter;
import kidoz.mopubadapter.presenter.MainScreenPresenterImpl;

public class MainScreenActivityImpl extends Activity implements MainScreenActivity
{
    private MainScreenPresenter mMainPresenter;

    private TextView mFeedbackTV;
    private Button mInterstitialLoad;
    private Button mInterstitialShow;
    private Button mRewardedLoad;
    private Button mRewardedShow;
    private Button mBannerLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViews();
        setButtons();

        mMainPresenter = new MainScreenPresenterImpl(this);
        mMainPresenter.onCreate();
    }

    private void findViews()
    {
        mFeedbackTV = (TextView) findViewById(R.id.feedback_tv);
        mInterstitialLoad = (Button) findViewById(R.id.interstitial_load);
        mInterstitialShow = (Button) findViewById(R.id.interstitial_show);
        mRewardedLoad = (Button) findViewById(R.id.rewarded_load);
        mRewardedShow = (Button) findViewById(R.id.rewarded_show);
        mBannerLoad = (Button) findViewById(R.id.banner_load);
    }

    private void setButtons()
    {
        mInterstitialLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMainPresenter.onClick_LoadInterstitial();
            }
        });

        mInterstitialShow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMainPresenter.onClick_ShowInterstitial();
            }
        });

        mRewardedLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMainPresenter.onClick_LoadRewarded();
            }
        });

        mRewardedShow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMainPresenter.onClick_ShowRewarded();
            }
        });

        mBannerLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMainPresenter.onClick_LoadBanner();
            }
        });
    }

    @Override
    public void showFeedBackText(String text)
    {
        if (!TextUtils.isEmpty(text))
        {
            mFeedbackTV.setText(text);
        }
    }

    @Override
    public Activity getActivity()
    {
        return MainScreenActivityImpl.this;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }
}