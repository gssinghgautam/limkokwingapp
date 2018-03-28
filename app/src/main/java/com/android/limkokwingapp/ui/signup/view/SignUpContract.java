package com.android.limkokwingapp.ui.signup.view;

import com.android.limkokwingapp.BaseView;
import com.android.limkokwingapp.data.entity.User;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface SignUpContract {

    interface View extends BaseView {

        void attach();

        void detach();

        void onSignUpSuccess(User user);

        void onFieldEmpty();

        void onEmailInvalid();

        void onPasswordInvalid();

        void onMobileInvalid();

        void onGenderInvalid();

        void onSignUpFailed();

        String getFName();

        String getLName();

        String getEmail();

        String getPassword();

        String getMobile();

        String getGender();

    }

    interface Presenter {

        void doSignUp();
    }
}
