package com.android.limkokwingapp.data.remote;

import com.android.limkokwingapp.data.remote.model.FlickerPhotos;
import com.android.limkokwingapp.utility.ApiConstant;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gautam on 25/03/18.
 */

public interface WebApiService {

    @GET(ApiConstant.FLICKER_PHOTO_SEARCH + ApiConstant.FLICKER_API_KEY + "&format=json&nojsoncallback=1")
    Flowable<FlickerPhotos> searchPhotos(@Query("text") String text, @Query("page") int page, @Query("per_page") int perPage);

}
