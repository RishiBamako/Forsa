package com.bartering.forsa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityMainTwoBinding;
import com.bartering.forsa.retrofit.BookFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_two);
        setFragment(R.id.container, new BookFragment());
    }

    public void setFragment(int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment)
                .commit();
    }

}
