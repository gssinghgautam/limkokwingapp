package com.android.limkokwingapp.di;

import android.app.Application;
import android.util.Log;

import com.android.limkokwingapp.data.remote.WebApiService;
import com.android.limkokwingapp.data.repository.remote.FlickerDataSource;
import com.android.limkokwingapp.utility.ApiConstant;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ActivityBuilderModule.class)
public class AppModule {

    private static final String TAG = "AppModule";

    @AppScope
    @Provides
    HttpLoggingInterceptor logger() {
        return new HttpLoggingInterceptor(message -> Log.d(TAG,message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @AppScope
    @Provides
    OkHttpClient okHttpClient(HttpLoggingInterceptor logger) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ApiConstant.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        builder.readTimeout(ApiConstant.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        builder.writeTimeout(ApiConstant.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        builder.addInterceptor(logger);
        return builder.build();
    }

    @AppScope
    @Provides
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConstant.RELEASE_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    WebApiService webApiService(Retrofit retrofit) {
        return retrofit.create(WebApiService.class);
    }

    @AppScope
    @Provides
    OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }

    @AppScope
    @Provides
    Picasso picasso(Application context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context).downloader(okHttp3Downloader).build();
    }

    @AppScope
    @Provides
    FlickerDataSource flickerDataSource(WebApiService webApiService) {
        return new FlickerDataSource(webApiService);
    }

}
