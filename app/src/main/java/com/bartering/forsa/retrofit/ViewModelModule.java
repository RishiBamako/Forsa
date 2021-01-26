package com.bartering.forsa.retrofit;

import androidx.lifecycle.ViewModel;


import com.bartering.forsa.retrofit.keys.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ApiCaller.class)
    public abstract ViewModel bindPostViewModel(ApiCaller postViewModel);

}
