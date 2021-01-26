package com.bartering.forsa.tradeProcess;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.ActivityTotalCartBinding;
import com.bartering.forsa.recyclerViewAdapter.Cart_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.CartData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class CartTotalActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityTotalCartBinding activityTotalCartBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Cart_RecyclerViewAdapter cart_recyclerViewAdapter;
    boolean isInEditMode = false;

    CartData_ServiceModel cartData_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    Product_Add_WiLit product_add_wiLit;


    int clickCartPosition;
    int clickedWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTotalCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_total_cart);
        activityTotalCartBinding.setIsNoRecord(false);
        activityTotalCartBinding.setButtonName(getString(R.string.edibtn));
        activityTotalCartBinding.setClickListener(this::onClick);
        activityTotalCartBinding.setCheckoutButtonShouldHide(false);
        activityTotalCartBinding.setShouldHideTopInformation(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartData();
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("MY_CART_DATA")) {
            cartData_serviceModel = (CartData_ServiceModel) resultData.getRootData().getValue();
            if (cartData_serviceModel.getData().getPrdpecord().size() > 0) {
                activityTotalCartBinding.setShouldHideTopInformation(false);

                activityTotalCartBinding.setIsNoRecord(false);
                activityTotalCartBinding.setTotalPrice(cartData_serviceModel.getData().getProduct_total_price());
                activityTotalCartBinding.setCartProfileSection(cartData_serviceModel.getData().getUserprofile().get(0)); //send profile data

                if (cartData_serviceModel.getData().getPrdpecord().size() == 1)
                    activityTotalCartBinding.setCartSize(cartData_serviceModel.getData().getPrdpecord().size() + " " + getResources().getString(R.string.item));
                else
                    activityTotalCartBinding.setCartSize(cartData_serviceModel.getData().getPrdpecord().size() + " " + getResources().getString(R.string.items));

                ((SimpleItemAnimator) activityTotalCartBinding.cartRecyclerViewId.getItemAnimator()).setSupportsChangeAnimations(false);
                cart_recyclerViewAdapter = new Cart_RecyclerViewAdapter(CartTotalActivity.this, cartData_serviceModel.getData().getPrdpecord(), this::onClick, isInEditMode);
                activityTotalCartBinding.cartRecyclerViewId.setLayoutManager(new GridLayoutManager(CartTotalActivity.this, 2));
                activityTotalCartBinding.cartRecyclerViewId.setAdapter(cart_recyclerViewAdapter);


            } else {
                activityTotalCartBinding.setShouldHideTopInformation(true);
                activityTotalCartBinding.setIsNoRecord(true);
            }
        }
        if (resultData.getTag().equals("CHECKOUT_DATA")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.customToast(CartTotalActivity.this, comman_serviceModel.getMessage());
                CartTotalActivity.this.finish();
                ((AcraSlackSample) getApplication()).switcher(CartTotalActivity.this, CheckoutActivity.class, 0);
            } else {
                AlphaHolder.customToast(CartTotalActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("CHECKOUT_PREVIEW_DATA")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {

            } else {
                AlphaHolder.customToast(CartTotalActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("DELETE_CART_ITEM")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                if (cartData_serviceModel.getData().getPrdpecord() != null && cartData_serviceModel.getData().getPrdpecord().size() > 0) {
                    cartData_serviceModel.getData().getPrdpecord().remove(clickCartPosition);
                    cart_recyclerViewAdapter.notifyItemRemoved(clickCartPosition);
                    cart_recyclerViewAdapter.notifyItemRangeChanged(clickCartPosition, cartData_serviceModel.getData().getPrdpecord().size());

                    if (cartData_serviceModel.getData().getPrdpecord().size() != 0) {
                        activityTotalCartBinding.setIsNoRecord(false);
                    } else {
                        activityTotalCartBinding.setIsNoRecord(true);
                    }
                }
            } else {
                AlphaHolder.customToast(CartTotalActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (cartData_serviceModel.isStatus().equals("true")) {
                if (cartData_serviceModel.getData().getPrdpecord().get(clickedWish).getWishstatus().equals("Wished")) {
                    cartData_serviceModel.getData().getPrdpecord().get(clickedWish).setWishstatus("Not Wished");
                } else {
                    cartData_serviceModel.getData().getPrdpecord().get(clickedWish).setWishstatus("Wished");
                }
            } else {
                AlphaHolder.customToast(CartTotalActivity.this, product_add_wiLit.getMessage());
            }
            cart_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {/// checkout//
            checkout();
        }
        if (callerIdentity.equals("event2")) {
            CartTotalActivity.this.finish();
        }
        if (callerIdentity.equals("event3")) {
            String name = activityTotalCartBinding.editBtnId.getText().toString();
            if (name.equals("Edit")) {
                activityTotalCartBinding.setCheckoutButtonShouldHide(true);
                isInEditMode = true;
                activityTotalCartBinding.setButtonName(getString(R.string.save));

                cart_recyclerViewAdapter = new Cart_RecyclerViewAdapter(CartTotalActivity.this, cartData_serviceModel.getData().getPrdpecord(), this::onClick, isInEditMode);
                activityTotalCartBinding.cartRecyclerViewId.setLayoutManager(new GridLayoutManager(CartTotalActivity.this, 2));
                activityTotalCartBinding.cartRecyclerViewId.setAdapter(cart_recyclerViewAdapter);

            } else {
                activityTotalCartBinding.setCheckoutButtonShouldHide(false);

                isInEditMode = false;
                activityTotalCartBinding.setButtonName(getString(R.string.edibtn));

                cart_recyclerViewAdapter = new Cart_RecyclerViewAdapter(CartTotalActivity.this, cartData_serviceModel.getData().getPrdpecord(), this::onClick, isInEditMode);
                activityTotalCartBinding.cartRecyclerViewId.setLayoutManager(new GridLayoutManager(CartTotalActivity.this, 2));
                activityTotalCartBinding.cartRecyclerViewId.setAdapter(cart_recyclerViewAdapter);

            }
        }
        if (callerIdentity.equals("event4")) {
            clickCartPosition = position;
            CartData_ServiceModel.DataBean.PrdpecordBean prdpecordBean = (CartData_ServiceModel.DataBean.PrdpecordBean) object;
            deleteCartProduct(prdpecordBean);
        }
        if (callerIdentity.equals("event8")) { ///delete cart item
            clickedWish = position;
            CartData_ServiceModel.DataBean.PrdpecordBean dataBean = (CartData_ServiceModel.DataBean.PrdpecordBean) object;
            addProductToWishlist(dataBean.getPrd_id());

        }
        if (callerIdentity.equals("event10")) { ////on product Click
            CartData_ServiceModel.DataBean.PrdpecordBean dataBean = (CartData_ServiceModel.DataBean.PrdpecordBean) object;
            if (dataBean != null) {
                Intent intent = new Intent(CartTotalActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", dataBean.getPrd_id());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

        }

    }

    public void addProductToWishlist(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(CartTotalActivity.this));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_ADD_WISHLIST", paramMap, false, CartTotalActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getCartData() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(CartTotalActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("MY_CART_DATA", paramMap, true, CartTotalActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void checkout() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(CartTotalActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHECKOUT_DATA", paramMap, true, CartTotalActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void checkout_preview() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(CartTotalActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHECKOUT_PREVIEW_DATA", paramMap, true, CartTotalActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void deleteCartProduct(CartData_ServiceModel.DataBean.PrdpecordBean prdpecordBean) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(CartTotalActivity.this));
        paramMap.put("prd_id", prdpecordBean.getPrd_id());

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("DELETE_CART_ITEM", paramMap, true, CartTotalActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}