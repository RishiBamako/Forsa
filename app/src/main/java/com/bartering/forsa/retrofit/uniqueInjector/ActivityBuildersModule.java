package com.bartering.forsa.retrofit.uniqueInjector;

import com.bartering.forsa.activities.ChangePasswordActivity;
import com.bartering.forsa.activities.ChooseCategoryActivity;
import com.bartering.forsa.activities.ChooseLanguageActivity;
import com.bartering.forsa.activities.EditProfileActivity;
import com.bartering.forsa.activities.FollowerUserActivity;
import com.bartering.forsa.activities.FollowingUserActivity;
import com.bartering.forsa.activities.ForgotPasswordLinkActivity;
import com.bartering.forsa.activities.HelpAndSupportActivity;
import com.bartering.forsa.activities.MobileNoVerificationActivity;
import com.bartering.forsa.activities.MyTransactionActivity;
import com.bartering.forsa.activities.ProductReviewActivity;
import com.bartering.forsa.activities.ProfileActivity;
import com.bartering.forsa.activities.SettingsEditProfileActivity;
import com.bartering.forsa.activities.SignInActivity;
import com.bartering.forsa.activities.SignInWithEmailActivity;
import com.bartering.forsa.activities.SignInWithPhoneActivity;
import com.bartering.forsa.activities.SignUpWithEmailActivity;
import com.bartering.forsa.activities.SignUpWithPhoneActivity;
import com.bartering.forsa.activities.TransactionDetailActivity;
import com.bartering.forsa.activities.WishListProfileActivity;
import com.bartering.forsa.activities.bartering_detail.BarteringSuggestionActivity;
import com.bartering.forsa.activities.bartering_detail.BarteringSuggestionChatActivity;
import com.bartering.forsa.activities.bartering_detail.BarteringTransactionDetailActivity;
import com.bartering.forsa.activities.bartering_detail.ChooseBarteringItemCameraActivity;
import com.bartering.forsa.activities.bartering_detail.ChooseBarteringItemSuggestionActivity;
import com.bartering.forsa.activities.bartering_detail.EditProductActivity;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.activities.bartering_detail.ProductDetail_UploadActivity;
import com.bartering.forsa.activities.bartering_detail.ProductOverViewActivity;
import com.bartering.forsa.activities.bartering_detail.ProductsCategoriesActivity;
import com.bartering.forsa.activities.bartering_detail.ProductsSubCategoriesActivity;
import com.bartering.forsa.activities.bartering_detail.ViewBarteringImageVideoActivity;
import com.bartering.forsa.activities.boots_Section.BoostPlansActivity;
import com.bartering.forsa.activities.boots_Section.MyAdsActivity;
import com.bartering.forsa.activities.boots_Section.Order_detailActivity;
import com.bartering.forsa.activities.seller.SellerProfileActivity;
import com.bartering.forsa.buySubscriptionPlanProcess.OrderDetailActivity;
import com.bartering.forsa.buySubscriptionPlanProcess.SubscriptionActivity;
import com.bartering.forsa.buySubscriptionPlanProcess.SubscriptionPlansActivity;
import com.bartering.forsa.chat_module.ChatActivity;
import com.bartering.forsa.retrofit.PostModule;
import com.bartering.forsa.retrofit.ViewModelModule;
import com.bartering.forsa.tradeProcess.AddAddressActivity;
import com.bartering.forsa.tradeProcess.AddressActivity;
import com.bartering.forsa.tradeProcess.CartTotalActivity;
import com.bartering.forsa.tradeProcess.CheckoutActivity;
import com.bartering.forsa.tradeProcess.EditAddressActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {PostModule.class})
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SubscriptionActivity subscriptionActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductOverViewActivity productOverViewActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract Order_detailActivity order_detailActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract BoostPlansActivity boostPlansActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract OrderDetailActivity orderDetailActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SubscriptionPlansActivity subscriptionPlansActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SignInWithEmailActivity contributeNoteFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ForgotPasswordLinkActivity forgotPasswordLinkActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SignUpWithEmailActivity signUpWithEmailActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MyAdsActivity adsActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddAddressActivity addAddressActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddressActivity addressActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract CartTotalActivity cartTotalActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SignInWithPhoneActivity signInWithPhoneActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChooseLanguageActivity chooseLanguageActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChooseCategoryActivity chooseCategoryActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SignUpWithPhoneActivity signUpWithPhoneActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductsCategoriesActivity productsCategoriesActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductsSubCategoriesActivity productsSubCategoriesActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FollowerUserActivity followerUserActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FollowingUserActivity followingUserActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract EditProfileActivity editProfileActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChangePasswordActivity changePasswordActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MyTransactionActivity myTransactionActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract HelpAndSupportActivity helpAndSupportActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract TransactionDetailActivity transactionDetailActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SettingsEditProfileActivity settingsEditProfileActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductReviewActivity productReviewActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChooseBarteringItemCameraActivity chooseBarteringItemCameraActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductDetail_UploadActivity productDetail_uploadActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProductDetailActivity productDetailActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChatActivity chatActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SellerProfileActivity sellerProfileActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract CheckoutActivity checkoutActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract WishListProfileActivity wishListProfileActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract EditAddressActivity editAddressActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ChooseBarteringItemSuggestionActivity chooseBarteringItemSuggestionActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract BarteringSuggestionActivity barteringSuggestionActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract BarteringSuggestionChatActivity barteringSuggestionChatActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ViewBarteringImageVideoActivity viewBarteringImageVideoActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProfileActivity profileActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SignInActivity signInActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract EditProductActivity editProductActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MobileNoVerificationActivity mobileNoVerificationActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract BarteringTransactionDetailActivity barteringTransactionDetailActivity();


}
