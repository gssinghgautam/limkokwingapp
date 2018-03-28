package com.android.limkokwingapp.ui.images;

import com.android.limkokwingapp.ui.images.view.ImageContract;

import dagger.Binds;
import dagger.Module;

/**
 * Created by gautam on 27/03/18.
 */

@Module
public abstract class ImageViewModule {

    @Binds
    abstract ImageContract.View provideView(ImageActivity imageActivity);

}
