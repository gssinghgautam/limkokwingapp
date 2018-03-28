package com.android.limkokwingapp.di;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.signup.view.SignUpContract;
import com.android.limkokwingapp.ui.signup.presenter.SignUpPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */
@SuppressWarnings("DefaultFileTemplate")
@Module
public class SignUpModule {

    @Provides
    SignUpPresenter provideSignUpPresenter(SignUpContract.View singUpView,
                                           UserDataSource userDataSource,
                                           AppSchedulerProvider appSchedulerProvider) {
        return new SignUpPresenter(singUpView, userDataSource, appSchedulerProvider);
    }
}
