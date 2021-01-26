package com.bartering.forsa.retrofit;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory modelProvider);


}
