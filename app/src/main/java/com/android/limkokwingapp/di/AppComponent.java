package com.android.limkokwingapp.di;

import android.app.Application;

import com.android.limkokwingapp.App;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by gautam on 25/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
@AppScope
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        RoomModule.class,
        AppModule.class,
        SharedPreferenceModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);

}
