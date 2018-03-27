package com.android.limkokwingapp.di;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.signup.SignUpContract;
import com.android.limkokwingapp.ui.signup.SignUpPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */
@Module
public class SignUpModule {

    @Provides
    SignUpPresenter provideSignUpPresenter(SignUpContract.View singUpView,
                                           UserDataSource userDataSource,
                                           AppSchedulerProvider appSchedulerProvider) {
        return new SignUpPresenter(singUpView, userDataSource, appSchedulerProvider);
    }
}
