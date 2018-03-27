package com.android.limkokwingapp.ui.login;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */

@Module
public abstract class LoginViewModule {

    @Binds
    abstract LoginContract.LoginView provideLoginView(LoginActivity loginActivity);
}
