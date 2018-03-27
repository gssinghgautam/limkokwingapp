package com.android.limkokwingapp.di;

import com.android.limkokwingapp.data.repository.remote.FlickerDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.images.ImageContract;
import com.android.limkokwingapp.ui.images.ImagePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 27/03/18.
 */

@Module
public class ImageModule {

    @Provides
    ImagePresenter provideImagePresenter(ImageContract.View imageView,
                                         FlickerDataSource flickerDataSource,
                                         AppSchedulerProvider appSchedulerProvider) {
        return new ImagePresenter(imageView, flickerDataSource, appSchedulerProvider);
    }
}
