package com.android.limkokwingapp.data.repository.remote;

import com.android.limkokwingapp.data.remote.WebApiService;
import com.android.limkokwingapp.data.remote.model.FlickerPhotos;
import com.android.limkokwingapp.utility.ApiConstant;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class FlickerDataSource implements FlickerDataRepository {

    private final WebApiService webApiService;

    @Inject
    public FlickerDataSource(WebApiService webApiService){
        this.webApiService =webApiService;
    }
    @Override
    public Flowable<FlickerPhotos> searchFlickerPhotos(String text, int page) {
        return webApiService.searchPhotos(text, page,ApiConstant.PER_PAGE);
    }
}
