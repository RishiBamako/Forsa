package com.bartering.forsa.retrofit;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AppModule {
    @Provides
    @Singleton
    static APIService provideApi(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


}
