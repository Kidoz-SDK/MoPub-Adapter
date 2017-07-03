package kidoz.mopubadapter.view;

import android.app.Activity;

/**
 * Created by orikam on 02/07/2017.
 */

public interface MainScreenActivity
{
    void showFeedBackText(String text);
    Activity getActivity(); //Specifically, since our mainView here is meant to lead to Activity based SDK actions we require our MainViewImpl to be able to return an Activity
}
