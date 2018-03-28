package com.android.limkokwingapp.ui.main.view;

import com.android.limkokwingapp.BaseView;
import com.android.limkokwingapp.data.entity.User;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface MainContract {

    interface View extends BaseView {

        void toEmptyMobile();

        void toInvalidMobile();

        void onUpdateSuccess(User user);

        void onUpdateFailed();

        void onLogout();
    }

    interface Presenter {

        void updateUserInfo(User user);

        void logout();

    }
}
