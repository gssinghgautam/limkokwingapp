package com.android.limkokwingapp.di;


import com.android.limkokwingapp.ui.images.ImageActivity;
import com.android.limkokwingapp.ui.images.ImageViewModule;
import com.android.limkokwingapp.ui.login.LoginActivity;
import com.android.limkokwingapp.ui.login.LoginViewModule;
import com.android.limkokwingapp.ui.main.MainActivity;
import com.android.limkokwingapp.ui.main.MainViewModule;
import com.android.limkokwingapp.ui.signup.SignUpActivity;
import com.android.limkokwingapp.ui.signup.SignUpViewModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {LoginModule.class, LoginViewModule.class})
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector(modules = {SignUpModule.class, SignUpViewModule.class})
    abstract SignUpActivity signUpActivity();

    @ContributesAndroidInjector(modules = {MainModule.class, MainViewModule.class})
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = {ImageModule.class, ImageViewModule.class})
    abstract ImageActivity imageActivity();

}