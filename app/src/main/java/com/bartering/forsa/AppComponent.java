package com.bartering.forsa;

import android.app.Application;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.retrofit.uniqueInjector.ActivityBuildersModule;
import com.bartering.forsa.retrofit.AppModule;
import com.bartering.forsa.retrofit.uniqueInjector.FragmentBuildersModule;
import com.bartering.forsa.retrofit.NetworkModule;
import com.bartering.forsa.retrofit.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, FragmentBuildersModule.class, ActivityBuildersModule.class
        , AppModule.class, ViewModelFactoryModule.class, NetworkModule.class})
public interface AppComponent extends AndroidInjector<AcraSlackSample> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
