package com.bartering.forsa.retrofit;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;

public class BookRepo {

    public final APIService api;
    Object freeBas;

    @Inject
    public BookRepo(APIService api) {
        this.api = api;
    }

    public Single<Object> getAppSpecificUnit(String callerSpecificSignature, Map<String, String> paramMap) {
        if (callerSpecificSignature.equals("SIGNIN_EMAIL"))
            freeBas = api.loginWithEmail(paramMap);
        else if (callerSpecificSignature.equals("LANGUAGES_CALLER"))
            freeBas = api.getLanguages();
        else if (callerSpecificSignature.equals("COUNTRIES_CALLER"))
            freeBas = api.getCountries();
        else if (callerSpecificSignature.equals("ADS_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getMyAds(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("UPDATE_COUNTRY_LANGUAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.updateCountry(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SIGNIN_MOBILE")) {
            freeBas = api.signInWithPhone(paramMap);
        } else if (callerSpecificSignature.equals("SIGN_UP_MOBILE")) {
            freeBas = api.signUpWithPhone(paramMap); ///API NOT PUTTED YET
        } else if (callerSpecificSignature.equals("CATEGORIES")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getCategories("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("OTP_VERIFICATION")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.otpVerification(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("LOGOUT")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.logout("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("HOME_FILTER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.homeFilter(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("CHECKOUT_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.checkout("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("CHECKOUT_PREVIEW_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.checkout_preview("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("HOME_FILTER_NOT_LOGGEDIN")) {
            freeBas = api.homeFilterNL(paramMap);
        } else if (callerSpecificSignature.equals("PRODUCT_CATEGORY")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.productCategories("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_SUB_CATEGORY")) {
            String tokenIs = paramMap.get("token");
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(paramMap)).toString());
            freeBas = api.productSubCategories(body, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("HOME_PRODUCTS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.homeProducts(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("GUEST_HOME_PRODUCTS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.homeProductsGuest(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("FOLLOWER_USER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getFollowers("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("UN_FOLLOW")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.unFollow_User(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("FOLLOW")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.follow_User(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("FOLLOWING_USER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getFollowing("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("GET_PROFILE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getProfile("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("CHANGE_PASSWORD")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.changePassword(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("MY_TRANSACTION")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getTransactions("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SEND_SUPPORT")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.sendRequest(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("TRANSACTION_DETAIL")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.transactionDetails(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SETTING")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.settingUpdates(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_RATING")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.productRating(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_LIKE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.productLike(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_ADD_WISHLIST")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.addProductWilt(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("ADD_CATEGORY")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.addCategory(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("ADD_SUB_CATEGORY")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.addSubCategory(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_DETAILS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.productDetails(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("REPORT_AD")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.reportAd(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("IMPROVEMENT_LIST")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.improvementList(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SIMILAR_PRODUCTS_LIST")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.similarProductsList(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SIGN_UP_WITH_EMAIL")) {
            freeBas = api.registerWithEmail(paramMap);
        } else if (callerSpecificSignature.equals("SIGN_UP_WITH_MOBILE")) {
            freeBas = api.registerWithPhone(paramMap);
        } else if (callerSpecificSignature.equals("FORGOT_PASSWORD")) {
            freeBas = api.forgotPassword(paramMap);
        } else if (callerSpecificSignature.equals("SUBSCRIPTION_PACKAGE")) {
            freeBas = api.subscriptionPackage(paramMap);
        } else if (callerSpecificSignature.equals("SUBSCRIPTION_PACKAGES")) {
            freeBas = api.subscriptionPackages(paramMap);
        } else if (callerSpecificSignature.equals("PLACE_ORDER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.orderSubscription(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("BOOST_PLANS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.boostPlans(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("ORDER_PACKAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.orderBoostPackage(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("CHAT_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getChatData(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("SELLER_PROFILE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.sellerProfile(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("WISHLIST_PRODUCTS_LIST")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.wishListProductsList(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("PRODUCT_REVIEWS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.productsReviewsList(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("REPORT_USER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.reportUser_SellerProfile(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("ADD_ADDRESS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.addAddress(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("ADDRESS_LIST")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getAllAddress("Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SELECT_ADDRESS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.selectAddress(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("MY_CART_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getMyCart(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("DELETE_CART_ITEM")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.deleteCartItem(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("CHANGE_STATUS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.changeAdsStatus(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("UPDATE_LANGUAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.updateLanguage(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("EDIT_ADDRESS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.updateAddress(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("DELETE_ADDRESS")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.deleteAddress(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SUBSCRIBED_PLAN")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.subscribedPlan(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("BARTERING_SUGGESTION_DATA")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.list_bartering(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PROCEED_BARTERING")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.proceedBartering(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("DO_AGREE_BARTERING")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.doAgreeForBartering(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("LOGIN_WITH_SOCIAL_MEDIA")) {
            freeBas = api.socialLoginBoth(paramMap);
        } else if (callerSpecificSignature.equals("BUY_NOW")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.buyNow(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("TEXT_CONVERSION")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.buyNow(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("SEND_MESSAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.sendMessage(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("EDIT_MESSAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.editMessage(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("DELETE_MESSAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.deleteMessage(paramMap, "Bearer " + tokenIs);
        } else if (callerSpecificSignature.equals("PRIVACY_POLICY")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getPrivacyPolicy(tokenIs);
        } else if (callerSpecificSignature.equals("TERM_CONDITION")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.getTermAndCondition(tokenIs);
        } else if (callerSpecificSignature.equals("REMOVE_IMAGE")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.removeImage(paramMap, tokenIs);
        } else if (callerSpecificSignature.equals("TOP_FILTER")) {
            String tokenIs = paramMap.get("token");
            freeBas = api.topFilter(tokenIs);
        }
        return (Single<Object>) freeBas;
    }

    public Single<Object> getAppSpecificUnitRequestBody(String callerSpecificSignature, Map<String, RequestBody> paramMap, List<MultipartBody.Part> image, List<MultipartBody.Part> video) {
        if (callerSpecificSignature.equals("EDIT_PROFILE")) {
            RequestBody requestBody = paramMap.get("token");
            String token = bodyToString(requestBody);
            if (image.get(0) == null)
                freeBas = api.editProfile_NullImage(paramMap, token);
            else
                freeBas = api.editProfile(paramMap, image, token);

        }
        if (callerSpecificSignature.equals("UPLOAD_BARTERING_MEDIA")) {
            RequestBody requestBody = paramMap.get("token");
            String token = bodyToString(requestBody);
            freeBas = api.uploadProductInfo(paramMap, image, video, token);
        }
        if (callerSpecificSignature.equals("UPLAOD_PRODUCT_DATA")) {
            RequestBody requestBody = paramMap.get("token");
            String token = bodyToString(requestBody);
            freeBas = api.product_review_update(paramMap, image, video, token);
        }
        if (callerSpecificSignature.equals("ADD_PROFILE")) {
            RequestBody requestBody = paramMap.get("token");
            String token = bodyToString(requestBody);
            freeBas = api.addProfile(paramMap, image, token);
        }


        return (Single<Object>) freeBas;
    }

    public Single<Object> editProduct(String callerSpecificSignature, Map<String, RequestBody> paramMap, Map<String, RequestBody> imagesIdParam, Map<String, RequestBody> videoIdsParam, List<MultipartBody.Part> image, List<MultipartBody.Part> video) {
        if (callerSpecificSignature.equals("EDIT_PRODUCT_DATA")) {
            RequestBody requestBody = paramMap.get("token");
            String token = bodyToString(requestBody);
            freeBas = api.edit_product_update(paramMap, imagesIdParam, videoIdsParam, image, video, token);
        }
        return (Single<Object>) freeBas;
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
