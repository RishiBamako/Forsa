package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySignInMainBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class SignInActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    private static final int RC_SIGN_IN = 9001;
    TextView forgotPasswordTextViewId, loginUsingMoTextViewId;
    SpannableString content;
    ActivitySignInMainBinding parentBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    String socialLoginType;
    SignIn_ServiceModel signIn_serviceModel;


    @Inject
    SharedPreferences_Util sharedPreferences_util;
    String social_userName;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_main);
        parentBinding.setClickListener(this::onClick);
        sharedPreferences_util = new SharedPreferences_Util(SignInActivity.this);
        AlphaHolder.stackList.add(this);

        underLine();
        facebookInitiation();
        googleSignInInitiation();

     /*   PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.bartering.forsa", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hashkey", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }*/
    }

    private void googleSignInInitiation() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        parentBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Log.e("USERNAME", account.getDisplayName());
            } else {

            }
        } catch (ApiException e) {
            Log.e("USERNAME", e.getMessage());
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInIntent.addFlags(signInIntent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void facebookInitiation() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(getApplicationContext(),loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                Log.e("VALUEEEE", loginResult.getAccessToken().getToken());
                GraphApiIntegration(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "CANCEL", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GraphApiIntegration(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            social_userName = object.getString("name");
                            String id = object.getString("id");
                            // Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();
                            loginWithSocialMedia(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();


    }

    private void underLine() {
        content = new SpannableString(getString(R.string.signup));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        parentBinding.signUpTextViewId.setText(content);

      /*  content = new SpannableString(getString(R.string.forget_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordTextViewId.setText(content);

        content = new SpannableString(getString(R.string.logininusingmobileno));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        loginUsingMoTextViewId.setText(content);*/
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            ((AcraSlackSample) getApplication()).switcher(SignInActivity.this, ChooseCategoryActivity.class, 0);
        }
        if (callerIdentity.equals("event5")) {///FACEBOOK
            socialLoginType = "facebook";
            loginButton.performClick();
        }
        if (callerIdentity.equals("event6")) {//GOOGLE
            socialLoginType = "gmail";
            signIn();
        }
        if (callerIdentity.equals("event7")) {//SIGN IN WITH PHONE
            ((AcraSlackSample) getApplication()).switcher(SignInActivity.this, SignInWithPhoneActivity.class, 0);
        }
        if (callerIdentity.equals("event8")) {//SIGN IN WITH EMAIL
            ((AcraSlackSample) getApplication()).switcher(SignInActivity.this, SignInWithEmailActivity.class, 0);
        }
        if (callerIdentity.equals("event9")) {//MOVE TO SIGN UP BASE SCREEN
            ((AcraSlackSample) getApplication()).switcher(SignInActivity.this, SignUpActivity.class, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == -1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            social_userName = task.getResult().getDisplayName();
            loginWithSocialMedia(task.getResult().getId());
        } else if (socialLoginType.equals("facebook")) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else {
            AlphaHolder.customToast(SignInActivity.this, getResources().getString(R.string.request_cancelled));
        }
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public void loginWithSocialMedia(String unique_social_identity) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("socialLoginType", socialLoginType);
        paramMap.put("social_id", unique_social_identity);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("LOGIN_WITH_SOCIAL_MEDIA", paramMap, true, SignInActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);

    }
    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData != null) {
            if (resultData.getTag().equals("LOGIN_WITH_SOCIAL_MEDIA")) {
                signIn_serviceModel = (SignIn_ServiceModel) resultData.getRootData().getValue();
                if (signIn_serviceModel.isStatus().equals("true")) {
                    sharedPreferences_util.saveLoginModel(signIn_serviceModel);
                    Intent intent = new Intent(SignInActivity.this, ChooseLanguageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                if (socialLoginType.equals("facebook")) {
                    LoginManager.getInstance().logOut();
                } else {
                    signOut();
                }
            }
        }
    }
}