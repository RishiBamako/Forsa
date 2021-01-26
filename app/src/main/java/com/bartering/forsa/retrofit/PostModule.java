package com.bartering.forsa.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PostModule {
    @Singleton
    @Provides
    static BookRepo provideNoteRepo(APIService noteAPI){
        return new BookRepo(noteAPI);
    }
}
