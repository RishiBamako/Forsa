package com.bartering.forsa.activities.seller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.ActivitySellerProfileBinding;
import com.bartering.forsa.recyclerViewAdapter.SellerProfileProducts_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.retrofit.service_model.SellerProfile_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SellerProfileActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivitySellerProfileBinding activitySellerProfileBinding;
    SellerProfileProducts_RecyclerViewAdapter sellerProfileProducts_recyclerViewAdapter;
    Dialog shareDialog;

    LinearLayout mainDialogLinLayoutId;
    TextView shareProfileLinLayoutId, reportUserLinLayoutId;

    int clickedLike, clickedWish;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    SellerProfile_ServiceModel sellerProfile_serviceModel;

    ProductLike_ServiceModel productLike_serviceModel;
    Product_Add_WiLit product_add_wiLit;
    Comman_ServiceModel comman_serviceModel;
    Intent intent;

    String seller_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySellerProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_seller_profile);
        activitySellerProfileBinding.setIsNoRecord(false);
        activitySellerProfileBinding.setClickListener(this::onClick);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        getSellerId();

        if (!TextUtils.isEmpty(seller_id)) {
            if (seller_id.equals(SharedPreferences_Util.getUser_Id(SellerProfileActivity.this))) {
                activitySellerProfileBinding.followTextViewId.setVisibility(View.GONE);
            } else {
                activitySellerProfileBinding.followTextViewId.setVisibility(View.VISIBLE);
            }
            getProfileData();
        } else {
            AlphaHolder.customToast(SellerProfileActivity.this, getResources().getString(R.string.makeacakktoprogrammer));
        }
    }

    private void getSellerId() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getData() != null) {
                Uri data = getIntent().getData();
                seller_id = data.toString().substring(data.toString().indexOf("=") + 1);
            } else {
                seller_id = getIntent().getExtras().getString("SELLER_ID");
            }
        }
    }

    public void showDialog(Context context, int x, int y) {

        shareDialog = new Dialog(context, R.style.seller_dialog);
        shareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        shareDialog.setContentView(R.layout.seller_profilepop_up);

        shareDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wmlp = shareDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wmlp.x = x;
        wmlp.y = y;

        mainDialogLinLayoutId = shareDialog.findViewById(R.id.mainDialogLinLayoutId);
        reportUserLinLayoutId = shareDialog.findViewById(R.id.reportUserLinLayoutId);
        shareProfileLinLayoutId = shareDialog.findViewById(R.id.shareProfileLinLayoutId);

        mainDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        reportUserLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportUser();
            }
        });
        shareProfileLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performShare();
                shareDialog.dismiss();
            }
        });

        shareDialog.show();
    }

    private void performShare() {
        shareDialog.dismiss();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://com.bartering.forsa?SELLER_ID=" + seller_id; //new add
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_with)));
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) { ///back
            SellerProfileActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) { ///back
            showDialog(SellerProfileActivity.this, 100, 60);
        }
        if (callerIdentity.equals("event3")) { ///WISH
            clickedWish = position;
            SellerProfile_ServiceModel.DataBean.PrdpecordBean prdpecordBean = (SellerProfile_ServiceModel.DataBean.PrdpecordBean) object;
            addProductToWishlist(prdpecordBean.getId());
        }
        if (callerIdentity.equals("event4")) { ////LIKE
            clickedLike = position;
            SellerProfile_ServiceModel.DataBean.PrdpecordBean prdpecordBean = (SellerProfile_ServiceModel.DataBean.PrdpecordBean) object;
            likeProduct(prdpecordBean.getId());
        }
        if (callerIdentity.equals("event5")) { ////Follow
            if (!TextUtils.isEmpty(sellerProfile_serviceModel.getData().getFollowerstatus()) && sellerProfile_serviceModel.getData().getFollowerstatus().equals("yes")) {
                unFollow();
            } else {
                startFollow();
            }
        }
        if (callerIdentity.equals("event6")) { ////ProductView
            SellerProfile_ServiceModel.DataBean.PrdpecordBean prdpecordBean = (SellerProfile_ServiceModel.DataBean.PrdpecordBean) object;
            intent = new Intent(SellerProfileActivity.this, ProductDetailActivity.class);
            intent.putExtra("product_id", prdpecordBean.getId());
            startActivity(intent);
            overridePendingTransition(0, 0);

        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SELLER_PROFILE")) {
            sellerProfile_serviceModel = (SellerProfile_ServiceModel) resultData.getRootData().getValue();
            if (sellerProfile_serviceModel.getData() != null) {

                //// is SelfProfile


                ///FollerOrNot
                activitySellerProfileBinding.setFollowerStatus(sellerProfile_serviceModel.getData().getFollowerstatus());

                ///ABOVE PROFILE SECTION
                if (sellerProfile_serviceModel.getData().getUserprofile().size() > 0) {
                    activitySellerProfileBinding.setUserProfileData(sellerProfile_serviceModel.getData().getUserprofile().get(0));
                }

                ///ABOVE PROFILE SECTION
                if (sellerProfile_serviceModel.getData().getPrdpecord().size() > 0) {
                    activitySellerProfileBinding.setIsNoRecord(false);
                    sellerProfileProducts_recyclerViewAdapter = new SellerProfileProducts_RecyclerViewAdapter(SellerProfileActivity.this, sellerProfile_serviceModel.getData().getPrdpecord(), this::onClick);
                    activitySellerProfileBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(SellerProfileActivity.this, 2));
                    activitySellerProfileBinding.productsRecyclerViewId.setAdapter(sellerProfileProducts_recyclerViewAdapter);

                } else {
                    activitySellerProfileBinding.setIsNoRecord(true);
                }
            }
        }
        if (resultData.getTag().equals("PRODUCT_LIKE")) {
            productLike_serviceModel = (ProductLike_ServiceModel) resultData.getRootData().getValue();
            if (productLike_serviceModel.isStatus().equals("true")) {
                if (sellerProfile_serviceModel.getData().getPrdpecord().get(clickedLike).getLike().equals("1")) {
                    sellerProfile_serviceModel.getData().getPrdpecord().get(clickedLike).setLike("0");
                    manipulaTotalLike(false);
                } else {
                    sellerProfile_serviceModel.getData().getPrdpecord().get(clickedLike).setLike("1");
                    manipulaTotalLike(true);
                }
            } else {
                AlphaHolder.customToast(SellerProfileActivity.this, productLike_serviceModel.getMessage());
            }
            sellerProfileProducts_recyclerViewAdapter.notifyItemChanged(clickedLike);

        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (product_add_wiLit.isStatus().equals("true")) {
                if (sellerProfile_serviceModel.getData().getPrdpecord().get(clickedWish).getWish().equals("1")) {
                    sellerProfile_serviceModel.getData().getPrdpecord().get(clickedWish).setWish("0");
                } else {
                    sellerProfile_serviceModel.getData().getPrdpecord().get(clickedWish).setWish("1");
                }
            } else {
                AlphaHolder.customToast(SellerProfileActivity.this, product_add_wiLit.getMessage());
            }
            sellerProfileProducts_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
        if (resultData.getTag().equals("FOLLOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                activitySellerProfileBinding.followTextViewId.setText(getResources().getString(R.string.unfollow));
                sellerProfile_serviceModel.getData().setFollowerstatus("yes");
            } else {
                AlphaHolder.customToast(SellerProfileActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("UN_FOLLOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                activitySellerProfileBinding.followTextViewId.setText(getResources().getString(R.string.follow));
                sellerProfile_serviceModel.getData().setFollowerstatus("no");
            } else {
                AlphaHolder.customToast(SellerProfileActivity.this, comman_serviceModel.getMessage());
            }

        }
        if (resultData.getTag().equals("REPORT_USER")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                shareDialog.dismiss();
                AlphaHolder.customToast(SellerProfileActivity.this, getResources().getString(R.string.reportedsuccessfully));
            } else {
                AlphaHolder.customToast(SellerProfileActivity.this, comman_serviceModel.getMessage());
            }

        }
    }

    public void addProductToWishlist(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_ADD_WISHLIST", paramMap, false, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void likeProduct(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_LIKE", paramMap, false, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void manipulaTotalLike(boolean isPlus) {
        try {
            String toLikeString = sellerProfile_serviceModel.getData().getPrdpecord().get(clickedLike).getTotal_like();
            if (toLikeString != null && !toLikeString.equals("")) {
                int totatLikeInt = Integer.parseInt(toLikeString);
                if (isPlus)
                    totatLikeInt++;
                else
                    totatLikeInt--;
                sellerProfile_serviceModel.getData().getPrdpecord().get(clickedLike).setTotal_like(String.valueOf(totatLikeInt));
            }
        } catch (Exception EE) {

        }
    }

    public void startFollow() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));
        paramMap.put("userid", seller_id);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FOLLOW", paramMap, true, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void unFollow() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));
        paramMap.put("userid", seller_id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("UN_FOLLOW", paramMap, true, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getProfileData() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("user_id", seller_id);
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SELLER_PROFILE", paramMap, true, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void reportUser() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("user_id", seller_id);
        paramMap.put("token", SharedPreferences_Util.getToken(SellerProfileActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("REPORT_USER", paramMap, true, SellerProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}