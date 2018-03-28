package com.android.limkokwingapp.ui.main;

import com.android.limkokwingapp.ui.main.view.MainContract;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
@Module
public abstract class MainViewModule {

    @Binds
    abstract MainContract.View provideMainView(MainActivity mainActivity);
}
