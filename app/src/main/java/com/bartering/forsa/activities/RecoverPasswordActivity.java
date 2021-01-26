package com.bartering.forsa.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bartering.forsa.R;

public class RecoverPasswordActivity extends AppCompatActivity {

    TextView textView;
    SpannableString content;
    TextView termAndCoCheckBoxId, termsTextViewId, conditionAndPolicyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        //underLine();
    }

    private void underLine() {
        textView = findViewById(R.id.signInTextViewId);
        termAndCoCheckBoxId = findViewById(R.id.termAndCoCheckBoxId);

        content = new SpannableString("Sign In");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        termsTextViewId = findViewById(R.id.termsTextViewId);

        content = new SpannableString(getResources().getString(R.string.terms));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        termsTextViewId.setText(content);

        conditionAndPolicyTextView = findViewById(R.id.conditionAndPolicyTextViewId);

        content = new SpannableString(getResources().getString(R.string.conditionandpolicy));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        conditionAndPolicyTextView.setText(content);
    }

    public void otpVerification(View view) {
        Intent intent = new Intent(RecoverPasswordActivity.this, MobileNoVerificationActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void signInWithPhone(View view) {
        Intent intent = new Intent(RecoverPasswordActivity.this, SignInWithPhoneActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void backPressed(View view) {
        this.finish();
    }
}