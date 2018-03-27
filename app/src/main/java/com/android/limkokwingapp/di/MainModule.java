package com.android.limkokwingapp.di;

import android.content.SharedPreferences;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.main.MainContract;
import com.android.limkokwingapp.ui.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */

@Module
public class MainModule {

    @Provides
    MainPresenter provideMainPresenter(MainContract.View mainView,
                                       UserDataSource userDataSource,
                                       AppSchedulerProvider appSchedulerProvider, SharedPreferences sharedPreferences) {
        return new MainPresenter(mainView, userDataSource, appSchedulerProvider, sharedPreferences);
    }
}
