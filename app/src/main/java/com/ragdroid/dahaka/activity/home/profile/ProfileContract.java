package com.ragdroid.dahaka.activity.home.profile;

import com.ragdroid.dahaka.mvp.BasePresenter;
import com.ragdroid.dahaka.mvp.BaseView;

/**
 * Created by garimajain on 13/08/17.
 */

public interface ProfileContract {

    interface View extends BaseView {

        void showModel(ProfileModel model);
    }

    interface Presenter extends BasePresenter<View> {

    }

}
