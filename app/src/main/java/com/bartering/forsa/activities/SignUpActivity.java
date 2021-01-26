package com.bartering.forsa.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements ClickListener {

    TextView textView;
    SpannableString content;
    ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        activitySignUpBinding.setClickListener(this::onClick);
        underLine();
    }

    private void underLine() {
        textView = findViewById(R.id.signInTextViewId);
        content = new SpannableString("Sign In");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            SignUpActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {

        }
        if (callerIdentity.equals("event3")) {

        }
        if (callerIdentity.equals("event4")) {
            ((AcraSlackSample) getApplication()).switcher(SignUpActivity.this, SignUpWithPhoneActivity.class, 0);
        }
        if (callerIdentity.equals("event5")) {
            ((AcraSlackSample) getApplication()).switcher(SignUpActivity.this, SignUpWithEmailActivity.class, 0);
        }
        if (callerIdentity.equals("event6")) {
            SignUpActivity.this.finish();
        }
    }
}