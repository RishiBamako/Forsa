package com.bartering.forsa.NavigationDrawer;

/**
 * Created by vikram on 29/07/15.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.R;
import com.bartering.forsa.retrofit.service_model.SideMenu_ServiceModel;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();
    int checkValue = 0;
    public static RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    RelativeLayout container;
    List count;
    public static List<NavDrawerItem> data;

    List<NavDrawerItem> dataone;
    public static boolean bestcheck = false;
    public static String title = "";
    SideMenu_ServiceModel sideMenu_serviceModel;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    public List<NavDrawerItem> getData(SideMenu_ServiceModel sideMenu_serviceModel) {
        NavDrawerItem navItem = null;
        data = new ArrayList<NavDrawerItem>();

        for (int i = 0; i < sideMenu_serviceModel.getData().size(); i++) {
            navItem = new NavDrawerItem(false, sideMenu_serviceModel.getData().get(i).getTitle(), sideMenu_serviceModel.getData().get(i).getIcon());
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        dataone = new ArrayList<NavDrawerItem>();
        bestcheck = true;

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                checkValue++;
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListene() {
            @Override
            public void onClick(View view, int position) {
                closeDrawer();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSideMenuData();
        return layout;
    }

    private void getSideMenuData() {
        // Sending get request
        new loadMenuData().execute();

    }

    public class loadMenuData extends AsyncTask<Void, String, String> {
        StringBuffer response;
        String token;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            token = SharedPreferences_Util.getToken(getActivity());
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://dev.rglabs.net/forsa/api/v1/sidebar");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();

            } catch (Exception EE) {

            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (!TextUtils.isEmpty(response)) {
                sideMenu_serviceModel = new Gson().fromJson(response, SideMenu_ServiceModel.class);
                if (sideMenu_serviceModel.getData().size() > 0) {

                    adapter = new NavigationDrawerAdapter(getActivity(), getData(sideMenu_serviceModel));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;


        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };

        // mDrawerToggle.setHomeAsUpIndicator(R.drawable.backarrow);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListene {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);

    }

    public static void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public static void closeDrawer() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListene clickListene;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListene clickListene) {
            this.clickListene = clickListene;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListene != null) {
                        clickListene.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListene != null && gestureDetector.onTouchEvent(e)) {

                clickListene.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
