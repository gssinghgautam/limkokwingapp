package com.android.limkokwingapp.ui.signup;

import com.android.limkokwingapp.ui.signup.view.SignUpContract;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 26/03/18.
 */
@SuppressWarnings("DefaultFileTemplate")
@Module
public abstract class SignUpViewModule {

    @Binds
    abstract SignUpContract.View provideSignUpView(SignUpActivity signUpActivity);
}
