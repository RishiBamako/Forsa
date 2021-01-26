package com.bartering.forsa.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bartering.forsa.BottomMenuFragment;
import com.bartering.forsa.Fragment.Profile_BM_Fragment;
import com.bartering.forsa.NavigationDrawer.FragmentDrawer;
import com.bartering.forsa.R;
import com.bartering.forsa.utils.NetworkCheck;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public static boolean categoryChecker = false;
    private static String TAG = MainActivity.class.getSimpleName();
    boolean check = true;
    NetworkCheck networkCheck;
    TextView titleTextView;
    View view9;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateDrawer();
    }

    private void initiateDrawer() {
        networkCheck = new NetworkCheck(getApplicationContext());
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawerFragment.setDrawerListener(this);
        if (getIntent().hasExtra("PROFILE")) {
            callFragement(new Profile_BM_Fragment());

        } else {
            callFragement(new BottomMenuFragment());
        }
    }

    private void callFragement(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void displayView(int position) {
        switch (position) {
            case 1:
                Toast.makeText(MainActivity.this, "ONE", Toast.LENGTH_LONG).show();
                getSupportActionBar().setDisplayShowCustomEnabled(true);
                break;


        }
    }
}