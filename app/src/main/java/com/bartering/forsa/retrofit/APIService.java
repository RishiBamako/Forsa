package com.bartering.forsa.retrofit;


import com.bartering.forsa.retrofit.service_model.Accept_Bartering_ServiceModel;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.BarteringProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.BoostPlans_ServiceModel;
import com.bartering.forsa.retrofit.service_model.CartData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.CategoriesData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ChatData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Countries_ServiceModel;
import com.bartering.forsa.retrofit.service_model.EditAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.FollowerData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.FollowingUser_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeFilter_NL_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeFilter_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ImproveList_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Languages_ServiceModel;
import com.bartering.forsa.retrofit.service_model.MyAds_ServiceModel;
import com.bartering.forsa.retrofit.service_model.OfferBartering_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Privacy_Policy_Terms_DataModel;
import com.bartering.forsa.retrofit.service_model.ProductCategory_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductReviewsList_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.retrofit.service_model.ProfileData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SellerProfile_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SettingProfile_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignUpEmail_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignUpMobile_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubCategoriesData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubscribedPlan_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackage_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackages_ServiceModel;
import com.bartering.forsa.retrofit.service_model.TopFilter_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Tran_Detail_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;
import com.bartering.forsa.retrofit.service_model.UploadProduct_ServiceModel;
import com.bartering.forsa.retrofit.service_model.WishlistData_ServiceModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("languages")
    Single<Languages_ServiceModel> getLanguages();

    @GET("logout")
    Single<Comman_ServiceModel> logout(@Header("Authorization") String authHeader);

    @GET("category")
    Single<CategoriesData_ServiceModel> getCategories(@Header("Authorization") String authHeader);

    @GET("countries")
    Single<Countries_ServiceModel> getCountries();

    @GET("my-ads")
    Single<MyAds_ServiceModel> getMyAds(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("checkout")
    Single<Comman_ServiceModel> checkout(@Header("Authorization") String authHeader);

    @GET("checkout-view")
    Single<Comman_ServiceModel> checkout_preview(@Header("Authorization") String authHeader);

    @GET("choose-bartering-product")
    Single<BarteringProducts_ServiceModel> list_bartering(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("login")
    Single<SignIn_ServiceModel> loginWithEmail(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("offer-bartering")
    Single<OfferBartering_ServiceModel> proceedBartering(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("respond-bartering")
    Single<Accept_Bartering_ServiceModel> doAgreeForBartering(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("social-login")
    Single<SignIn_ServiceModel> socialLoginBoth(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("buy-now")
    Single<Comman_ServiceModel> buyNow(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("add-message")
    Single<Comman_ServiceModel> sendMessage(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("edit-message")
    Single<Comman_ServiceModel> editMessage(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("delete-message")
    Single<Comman_ServiceModel> deleteMessage(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("privacy-policy")
    Single<Privacy_Policy_Terms_DataModel> getPrivacyPolicy(@Header("Authorization") String authHeader);

    @GET("terms-condition")
    Single<Privacy_Policy_Terms_DataModel> getTermAndCondition(@Header("Authorization") String authHeader);

    @Multipart
    @POST("update-profile")
    Single<Comman_ServiceModel> editProfile(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> image, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("delete-media")
    Single<Comman_ServiceModel> removeImage(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);


    @GET("top-filter")
    Single<TopFilter_ServiceModel> topFilter(@Header("Authorization") String authHeader);

    @Multipart
    @POST("add-product-detail")
    Single<UploadProduct_ServiceModel> uploadProductInfo(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> image, @Part List<MultipartBody.Part> video, @Header("Authorization") String authHeader);

    @Multipart
    @POST("product-preview-update")
    Single<ProductDetails_ServiceModel> product_review_update(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> image, @Part List<MultipartBody.Part> video, @Header("Authorization") String authHeader);

    @Multipart
    @POST("prd-edit")
    Single<Comman_ServiceModel> edit_product_update(@PartMap Map<String, RequestBody> param, @PartMap Map<String, RequestBody> images_IdParam, @PartMap Map<String, RequestBody> videoIdsParam, @Part List<MultipartBody.Part> image, @Part List<MultipartBody.Part> video, @Header("Authorization") String authHeader);

    @Multipart
    @POST("complete-profile")
    Single<Comman_ServiceModel> addProfile(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> image, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("address/edit")
    Single<EditAddress_ServiceModel> updateAddress(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("address/delete")
    Single<Comman_ServiceModel> deleteAddress(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("address/delete")
    Single<SubscribedPlan_ServiceModel> subscribedPlan(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("update-language-country")
    Single<Comman_ServiceModel> updateCountry(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("mobile-login")
    Single<SignIn_ServiceModel> signInWithPhone(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("mobile-login")
    Single<SignIn_ServiceModel> signUpWithPhone(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("register-otp")
    Single<Comman_ServiceModel> otpVerification(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("home-filter-user")
    Single<HomeFilter_ServiceModel> homeFilter(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("home-filter")
    Single<HomeFilter_NL_ServiceModel> homeFilterNL(@FieldMap Map<String, String> param);

    @GET("product-cat")
    Single<ProductCategory_ServiceModel> productCategories(@Header("Authorization") String authHeader);

    @POST("product-subcat")
    Single<SubCategoriesData_ServiceModel> productSubCategories(@Body RequestBody param, @Header("Authorization") String authHeader);

    @GET("home-prd")
    Single<HomeProducts_ServiceModel> homeProducts(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("home-prd-public")
    Single<HomeProducts_ServiceModel> homeProductsGuest(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("follower")
    Single<FollowerData_ServiceModel> getFollowers(@Header("Authorization") String authHeader);

    @GET("prd-similar")
    Single<SimilarProducts_ServiceModel> similarProductsList(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("my-wishlist-prd")
    Single<WishlistData_ServiceModel> wishListProductsList(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("prd-review-rating")
    Single<ProductReviewsList_ServiceModel> productsReviewsList(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("improvement-list")
    Single<ImproveList_ServiceModel> improvementList(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("following")
    Single<FollowingUser_ServiceModel> getFollowing(@Header("Authorization") String authHeader);

    @GET("get-profile")
    Single<ProfileData_ServiceModel> getProfile(@Header("Authorization") String authHeader);

    @GET("address/list")
    Single<AllAddress_ServiceModel> getAllAddress(@Header("Authorization") String authHeader);

    @GET("prd-detail")
    Single<ProductDetails_ServiceModel> productDetails(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @GET("my-cart")
    Single<CartData_ServiceModel> getMyCart(@QueryMap Map<String, String> stringStringMap, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("start-unfollow")
    Single<Comman_ServiceModel> unFollow_User(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("address/change")
    Single<Comman_ServiceModel> selectAddress(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("report-ad")
    Single<Comman_ServiceModel> reportAd(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("change-password")
    Single<Comman_ServiceModel> changePassword(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("report-user")
    Single<Comman_ServiceModel> reportUser_SellerProfile(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("address/add")
    Single<Comman_ServiceModel> addAddress(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("start-follow")
    Single<Comman_ServiceModel> follow_User(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("delete-cart-prd")
    Single<Comman_ServiceModel> deleteCartItem(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("supportform")
    Single<Comman_ServiceModel> sendRequest(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("prd-status")
    Single<Comman_ServiceModel> changeAdsStatus(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("update-language")
    Single<Comman_ServiceModel> updateLanguage(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("submit-rating")
    Single<Comman_ServiceModel> productRating(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("prd-like")
    Single<ProductLike_ServiceModel> productLike(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("prd-wishlist")
    Single<Product_Add_WiLit> addProductWilt(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("add-product-cat")
    Single<Comman_ServiceModel> addCategory(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("register-new")
    Single<SignUpEmail_ServiceModel> registerWithEmail(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("register-new")
    Single<SignUpMobile_ServiceModel> registerWithPhone(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("forget-password")
    Single<Comman_ServiceModel> forgotPassword(@FieldMap Map<String, String> param);

    @GET("subscription-package")
    Single<SubscriptionPackage_ServiceModel> subscriptionPackage(@QueryMap Map<String, String> param);

    @GET("subscription-plans")
    Single<SubscriptionPackages_ServiceModel> subscriptionPackages(@QueryMap Map<String, String> param);

    @FormUrlEncoded
    @POST("add-product-subcat")
    Single<Comman_ServiceModel> addSubCategory(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("subscription-place-order")
    Single<Comman_ServiceModel> orderSubscription(@FieldMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("boost-packages")
    Single<BoostPlans_ServiceModel> boostPlans(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("boost-place-order")
    Single<Comman_ServiceModel> orderBoostPackage(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("transaction-detail")
    Single<Tran_Detail_ServiceModel> transactionDetails(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("settings")
    Single<SettingProfile_ServiceModel> settingUpdates(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("message-detail")
    Single<ChatData_ServiceModel> getChatData(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("user-profile")
    Single<SellerProfile_ServiceModel> sellerProfile(@QueryMap Map<String, String> param, @Header("Authorization") String authHeader);

    @GET("transaction-record")
    Single<Transaction_ServiceModel> getTransactions(@Header("Authorization") String authHeader);

}
