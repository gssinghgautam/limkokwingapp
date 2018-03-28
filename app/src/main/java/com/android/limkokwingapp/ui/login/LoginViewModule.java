package com.android.limkokwingapp.ui.login;

import com.android.limkokwingapp.ui.login.view.LoginContract;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
@Module
public abstract class LoginViewModule {

    @Binds
    abstract LoginContract.LoginView provideLoginView(LoginActivity loginActivity);
}
