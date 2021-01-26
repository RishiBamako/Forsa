package com.bartering.forsa.retrofit.uniqueInjector;

import com.bartering.forsa.Fragment.Home_BM_Fragment;
import com.bartering.forsa.Fragment.Profile_BM_Fragment;
import com.bartering.forsa.Fragment.SimilarProducts_TabFragment;
import com.bartering.forsa.Fragment.WishList_Products_TabFragment;
import com.bartering.forsa.retrofit.BookFragment;
import com.bartering.forsa.retrofit.PostModule;
import com.bartering.forsa.retrofit.ViewModelModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {PostModule.class})
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract BookFragment contributeNoteFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract Profile_BM_Fragment profile_bm_fragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract Home_BM_Fragment homeBmFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SimilarProducts_TabFragment similarProducts_tabFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract WishList_Products_TabFragment wishListProductsTabFragment();

}
