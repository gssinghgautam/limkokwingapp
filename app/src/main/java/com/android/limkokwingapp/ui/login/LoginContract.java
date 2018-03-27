package com.android.limkokwingapp.ui.login;

import com.android.limkokwingapp.BaseView;
import com.android.limkokwingapp.data.entity.User;

/**
 * Created by gautam on 26/03/18.
 */

public interface LoginContract {

    interface LoginView extends BaseView {

        void attach();

        void detach();

        void onValidationSuccess(User user);

        void onEmailEmpty();

        void onPasswordEmpty();

        void onEmailInvalid();

        void onPasswordInvalid();

        void onValidationFailed();

        String getEmail();

        String getPassword();
    }

    interface Presenter {

        void loginUser();

        void fetchUser(String emailId);

    }
}
