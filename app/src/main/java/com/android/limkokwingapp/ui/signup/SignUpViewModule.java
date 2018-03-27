package com.android.limkokwingapp.ui.signup;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 26/03/18.
 */
@Module
public abstract class SignUpViewModule {

    @Binds
    abstract SignUpContract.View provideSignUpView(SignUpActivity signUpActivity);
}
