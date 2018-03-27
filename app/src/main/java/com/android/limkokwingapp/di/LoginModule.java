package com.android.limkokwingapp.di;

import android.content.SharedPreferences;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.login.LoginContract;
import com.android.limkokwingapp.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */

@Module
public class LoginModule {

    @Provides
    LoginPresenter provideLoginPresenter(LoginContract.LoginView loginView,
                                         UserDataSource userDataSource,
                                         AppSchedulerProvider appSchedulerProvider, SharedPreferences sharedPreferences) {
        return new LoginPresenter(loginView, userDataSource, appSchedulerProvider, sharedPreferences);
    }
}
