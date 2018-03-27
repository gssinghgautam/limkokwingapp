package com.android.limkokwingapp.ui.main;

import com.android.limkokwingapp.ui.signup.SignUpActivity;
import com.android.limkokwingapp.ui.signup.SignUpContract;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 26/03/18.
 */

@Module
public abstract class MainViewModule {

    @Binds
    abstract MainContract.View provideMainView(MainActivity mainActivity);
}
