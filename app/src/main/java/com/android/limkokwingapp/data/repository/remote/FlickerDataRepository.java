package com.android.limkokwingapp.data.repository.remote;

import com.android.limkokwingapp.data.remote.model.FlickerPhotos;

import io.reactivex.Flowable;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface FlickerDataRepository {

    Flowable<FlickerPhotos> searchFlickerPhotos(String text, int page);
}
