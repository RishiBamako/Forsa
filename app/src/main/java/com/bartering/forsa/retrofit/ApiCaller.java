package com.bartering.forsa.retrofit;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bartering.forsa.GoogleLoader;
import com.bartering.forsa.R;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import retrofit2.http.Part;

public class ApiCaller extends BaseViewModel {

    private static final String TAG = "ApiCaller";
    ResultData resultData = null;
    GoogleLoader googleLoader = null;
    private BookRepo bookRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<ResultData> bookList;
    private MutableLiveData<Object> objectMutableLiveData;

    public ApiCaller() {

    }

    @Inject
    public ApiCaller(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public LiveData<ResultData> getRootData() {
        bookList = new MutableLiveData<ResultData>();
        return bookList;
    }

    public void loadData(String callerSpecificSignature, Map<String, String> paramMap, boolean showLoader, Activity activity) {
        try {
            Log.e("param", String.valueOf(new JSONObject(paramMap)));
            Log.e("param", SharedPreferences_Util.getUser_Id(activity));


        } catch (Exception EE) {

        }
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
        disposable = new CompositeDisposable();
        if (showLoader) {
            googleLoader = new GoogleLoader(activity);
            googleLoader.showLoader();
        }
        disposable.add(bookRepo.getAppSpecificUnit(callerSpecificSignature, paramMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<Object>() {
                    @Override
                    public void onSuccess(Object resultsResponse) {
                        if (googleLoader != null) {
                            googleLoader.dismiss();
                        }
                        objectMutableLiveData = new MutableLiveData<>();
                        objectMutableLiveData.setValue(resultsResponse);

                        resultData = new ResultData(objectMutableLiveData, callerSpecificSignature);
                        bookList.setValue(resultData);


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //   throwable.printStackTrace();
                        if (googleLoader != null) {
                            googleLoader.dismiss();
                        }

                        try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }
                        //HandleError(activity, throwable);

                        /*try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }*/
                        onError.setValue(throwable);

                    }

                }));
    }

    public void loadDataRequestBody(String callerSpecificSignature, Map<String, RequestBody> paramMap, @Part List<MultipartBody.Part> image, @Part List<MultipartBody.Part> video, boolean showLoader, Activity activity) {
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
        disposable = new CompositeDisposable();
        if (showLoader) {
            googleLoader = new GoogleLoader(activity);
            googleLoader.showLoader();
        }
        // Log.e("param", String.valueOf(new JSONObject(paramMap)));
        //Log.e("param", String.valueOf(new JSONObject(paramMap)));
        disposable.add(bookRepo.getAppSpecificUnitRequestBody(callerSpecificSignature, paramMap, image, video)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<Object>() {
                    @Override
                    public void onSuccess(Object resultsResponse) {
                        if (googleLoader != null)
                            googleLoader.dismiss();
                        objectMutableLiveData = new MutableLiveData<>();
                        objectMutableLiveData.setValue(resultsResponse);
                        resultData = new ResultData(objectMutableLiveData, callerSpecificSignature);
                        bookList.setValue(resultData);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //  throwable.printStackTrace();
                        if (googleLoader != null)
                            googleLoader.dismiss();

                        try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }
                        //HandleError(activity,throwable);

                       /* try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }*/
                        onError.setValue(throwable);

                    }
                }));
    }

    public void editProduct(String callerSpecificSignature, Map<String, RequestBody> paramMap, Map<String, RequestBody> imagesIdParam, Map<String, RequestBody> videoIdParam, @Part List<MultipartBody.Part> image, @Part List<MultipartBody.Part> video, boolean showLoader, Activity activity) {
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
        disposable = new CompositeDisposable();
        if (showLoader) {
            googleLoader = new GoogleLoader(activity);
            googleLoader.showLoader();
        }
        // Log.e("param", String.valueOf(new JSONObject(paramMap)));
        //Log.e("param", String.valueOf(new JSONObject(paramMap)));
        disposable.add(bookRepo.editProduct(callerSpecificSignature, paramMap, imagesIdParam, videoIdParam, image, video)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<Object>() {
                    @Override
                    public void onSuccess(Object resultsResponse) {
                        if (googleLoader != null)
                            googleLoader.dismiss();
                        objectMutableLiveData = new MutableLiveData<>();
                        objectMutableLiveData.setValue(resultsResponse);
                        resultData = new ResultData(objectMutableLiveData, callerSpecificSignature);
                        bookList.setValue(resultData);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //      throwable.printStackTrace();
                        if (googleLoader != null)
                            googleLoader.dismiss();

                        try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }
                      /*  try {
                            HttpException error = (HttpException) throwable;
                            String errorBody = error.response().errorBody().string();
                            if (!TextUtils.isEmpty(errorBody)) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    AlphaHolder.customToast(activity, message);
                                }
                            } else {
                                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                            }
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }*/
                        //HandleError(activity, throwable);
                        onError.setValue(throwable);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

    void HandleError(Activity activity, Throwable throwable) {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof IOException) || (e instanceof SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            try {
                HttpException error = (HttpException) throwable;
                String errorBody = error.response().errorBody().string();
                if (!TextUtils.isEmpty(errorBody)) {
                    JSONObject jsonObject = new JSONObject(errorBody);
                    if (jsonObject.has("message")) {
                        String message = jsonObject.getString("message");
                        AlphaHolder.customToast(activity, message);
                    }
                } else {
                    AlphaHolder.customToast(activity, activity.getResources().getString(R.string.unknown_error));
                }
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }

        });
    }
}
