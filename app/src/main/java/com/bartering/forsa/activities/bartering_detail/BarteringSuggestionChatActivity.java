package com.bartering.forsa.activities.bartering_detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.databinding.ActivityBarteringSuggestionChatBinding;
import com.bartering.forsa.databinding.CongratualtionLayoutBinding;
import com.bartering.forsa.interfaces.OnKeyboardVisibilityListener;
import com.bartering.forsa.recyclerViewAdapter.Chat_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Accept_Bartering_ServiceModel;
import com.bartering.forsa.retrofit.service_model.BarteringProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ChatData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.OfferBartering_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class BarteringSuggestionChatActivity extends AppCompactActivity implements ClickListener, Observer<Object>, TextWatcher, Chat_RecyclerViewAdapter.OnItemEventListener, PopupMenu.OnMenuItemClickListener, OnKeyboardVisibilityListener {

    final int delay = 4000;
    ActivityBarteringSuggestionChatBinding activityBarteringSuggestionChatBinding;
    Dialog dialogBottom;
    CongratualtionLayoutBinding congratualtionLayoutBinding;
    BarteringProducts_ServiceModel.DataBean selectedProductData;
    ProductDetails_ServiceModel myProductData;
    SignIn_ServiceModel signIn_serviceModel = null;
    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Accept_Bartering_ServiceModel accept_bartering_serviceModel;
    OfferBartering_ServiceModel offerBartering_serviceModel;
    Chat_RecyclerViewAdapter chat_recyclerViewAdapter;
    ChatData_ServiceModel chatData_serviceModel = null;
    List<ChatData_ServiceModel.DataBean> chatData_ArrayList;
    ////Message Receiver
    Handler handler;
    Runnable runnable;
    boolean isThisFirstTime = true;

    int selectedPosition;
    String operation_Status;
    ChatData_ServiceModel.DataBean operation_Data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBarteringSuggestionChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_bartering_suggestion_chat);
        activityBarteringSuggestionChatBinding.setClickListener(this::onClick);
        activityBarteringSuggestionChatBinding.setLoggedInUserName(SharedPreferences_Util.getLoggedInUserName(this));
        activityBarteringSuggestionChatBinding.setShowDialog(true);
        activityBarteringSuggestionChatBinding.typeMessageEditTextId.addTextChangedListener(this);
        keyboardStateNotifier(this::onVisibilityChanged);

        isThisFirstTime = true;

        getProductsData();

    }

    private void getProductsData() {
        if (getIntent().getExtras() != null) {
            selectedProductData = (BarteringProducts_ServiceModel.DataBean) getIntent().getSerializableExtra("SELECTED_PRODUCT");
            myProductData = (ProductDetails_ServiceModel) getIntent().getSerializableExtra("MY_PRODUCT");
            offerBartering_serviceModel = (OfferBartering_ServiceModel) getIntent().getSerializableExtra("BARTERING_ID");

            if (myProductData.getData().getMediarec().size() > 0)
                activityBarteringSuggestionChatBinding.setMyProductImage(myProductData.getData().getMediarec().get(0).getProduct_image());

            activityBarteringSuggestionChatBinding.setSelectedProductImage(selectedProductData.getPrd_img());
            activityBarteringSuggestionChatBinding.setEndUserImage(selectedProductData.getUser_img());

            activityBarteringSuggestionChatBinding.setMyImage(SharedPreferences_Util.getUser_Image(BarteringSuggestionChatActivity.this));
        }
    }

    /*private void listener() {
        activityBarteringSuggestionChatBinding.menuLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.show();
            }
        });
    }*/

    private void congratulationsDialog() {
        dialogBottom = new Dialog(BarteringSuggestionChatActivity.this, R.style.DialogAnimation);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        congratualtionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(BarteringSuggestionChatActivity.this), R.layout.congratualtion_layout, null, false);
        dialogBottom.setContentView(congratualtionLayoutBinding.getRoot());

        dialogBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBottom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimationtwo;
        dialogBottom.getWindow().setAttributes(lp);

        congratualtionLayoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();

                Intent intent = new Intent(BarteringSuggestionChatActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        congratualtionLayoutBinding.okTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();

                Intent intent = new Intent(BarteringSuggestionChatActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        dialogBottom.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  getConversationData();
        //setUpIntevalExecutionSystem();
    }

    private void setUpIntevalExecutionSystem() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                runnable = this::run;
                getConversationData();
            }
        }, delay);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) { ///back pressed
            BarteringSuggestionChatActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) { //// do agree for bartering
            doAgreeForBartering();
        }
        if (callerIdentity.equals("event3")) { //// menu button pressed
            if (activityBarteringSuggestionChatBinding.getShowDialog())
                activityBarteringSuggestionChatBinding.setShowDialog(false);
            else
                activityBarteringSuggestionChatBinding.setShowDialog(false);
        }
        if (callerIdentity.equals("event4")) { //// send button pressed
            if (TextUtils.isEmpty(operation_Status)) {
                if (handler != null)
                    handler.removeCallbacks(runnable, delay);
                addMessage(activityBarteringSuggestionChatBinding.typeMessageEditTextId.getText().toString(), "2");
            } else if (operation_Status.equals("EDIT")) {
                if (handler != null)
                    handler.removeCallbacks(runnable, delay);
                editMessage(operation_Data.getId(), activityBarteringSuggestionChatBinding.typeMessageEditTextId.getText().toString());
            }
        }
        if (callerIdentity.equals("event5")) { //// camera button pressed

        }
    }

    private void doAgreeForBartering() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("bartering_id", offerBartering_serviceModel.getData().getBartering_id());
        paramMap.put("bartering_status", "Accept");
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionChatActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("DO_AGREE_BARTERING", paramMap, true, BarteringSuggestionChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getConversationData() {
        boolean showLoader = false;
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionChatActivity.this));
        paramMap.put("conversation_id", offerBartering_serviceModel.getData().getConversation_id());
        if (chatData_serviceModel == null) {
            showLoader = true;
            paramMap.put("number", "" + 2);
        }
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHAT_DATA", paramMap, showLoader, BarteringSuggestionChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void addMessage(String message, String receiver_Id) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionChatActivity.this));
        paramMap.put("conversation_id", offerBartering_serviceModel.getData().getConversation_id());
        paramMap.put("receiver_id", receiver_Id);
        paramMap.put("msg_img", "1");
        paramMap.put("enter_message", message);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SEND_MESSAGE", paramMap, true, BarteringSuggestionChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void editMessage(String message_id, String messageAfterEdit) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionChatActivity.this));
        paramMap.put("msg_id", message_id);
        paramMap.put("msg_img", "1");
        paramMap.put("enter_message", messageAfterEdit);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("EDIT_MESSAGE", paramMap, true, BarteringSuggestionChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void deleteMessage(String message_id) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionChatActivity.this));
        paramMap.put("msg_id", message_id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("DELETE_MESSAGE", paramMap, false, BarteringSuggestionChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData != null) {
            if (resultData.getTag().equals("DO_AGREE_BARTERING")) {
                accept_bartering_serviceModel = (Accept_Bartering_ServiceModel) resultData.getRootData().getValue();
                if (accept_bartering_serviceModel.isStatus().equals("true")) {
                    congratulationsDialog();
                } else {
                    AlphaHolder.customToast(BarteringSuggestionChatActivity.this, accept_bartering_serviceModel.getMessage());
                }
            }
            if (resultData.getTag().equals("CHAT_DATA")) {
                if (chatData_serviceModel == null) {
                    chatData_serviceModel = (ChatData_ServiceModel) resultData.getRootData().getValue();
                    if (chatData_serviceModel != null && chatData_serviceModel.getData().size() > 0) {
                        activityBarteringSuggestionChatBinding.setIsRecordsNotFound(false);
                        if (chatData_serviceModel.getData().size() > 0) {
                            activityBarteringSuggestionChatBinding.setShowDialog(false);
                            if (chatData_serviceModel.getData().size() > 0) {
                                AlphaHolder.saveRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");
                                ((SimpleItemAnimator) activityBarteringSuggestionChatBinding.chatRecyclerViewId.getItemAnimator()).setSupportsChangeAnimations(false);
                                chat_recyclerViewAdapter = new Chat_RecyclerViewAdapter(BarteringSuggestionChatActivity.this, chatData_serviceModel.getData(), this::onClick, this::onMoreClicked);
                                activityBarteringSuggestionChatBinding.setChatAdapter(chat_recyclerViewAdapter);
                                isThisFirstTime = false;
                                AlphaHolder.manipulateRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");

                            }
                        }
                        if (handler != null)
                            handler.postDelayed(runnable, delay);

                    } else {
                        activityBarteringSuggestionChatBinding.setIsRecordsNotFound(true);
                    }
                } else {
                    chatData_serviceModel = (ChatData_ServiceModel) resultData.getRootData().getValue();
                    if (chatData_serviceModel.getData().size() > 0) {
                        List<ChatData_ServiceModel.DataBean> list = chat_recyclerViewAdapter.getChatData();
                        for (int chat = 0; chat < chatData_serviceModel.getData().size(); chat++) {
                            if (!list.contains(chatData_serviceModel.getData().get(chat))) {
                                chat_recyclerViewAdapter.addMessages(chatData_serviceModel.getData().get(chat));
                            }
                        }
                    }
                }

            }
            if (resultData.getTag().equals("SEND_MESSAGE")) {
                Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
                if (comman_serviceModel != null) {
                    if (comman_serviceModel.isStatus().equals("true")) {
                        ChatData_ServiceModel.DataBean dataBean = new ChatData_ServiceModel.DataBean();
                        dataBean.setFinal_message(activityBarteringSuggestionChatBinding.typeMessageEditTextId.getText().toString());
                        dataBean.setReceiver_id("2");
                        dataBean.setMsg_rprt("loginuser");

                        chatData_serviceModel.getData().add(dataBean);
                        chat_recyclerViewAdapter.notifyItemRangeInserted(chatData_serviceModel.getData().size() - 1, chatData_serviceModel.getData().size());
                        activityBarteringSuggestionChatBinding.chatRecyclerViewId.scrollToPosition(chatData_serviceModel.getData().size() - 1);
                        if (handler != null)
                            handler.postDelayed(runnable, delay);

                    }
                } else {
                    activityBarteringSuggestionChatBinding.setIsRecordsNotFound(true);
                }
            }
            if (resultData.getTag().equals("EDIT_MESSAGE")) {
                Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
                if (comman_serviceModel != null) {
                    if (comman_serviceModel.isStatus().equals("true")) {

                        AlphaHolder.saveRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");
                        chatData_serviceModel.getData().get(selectedPosition).setFinal_message(activityBarteringSuggestionChatBinding.typeMessageEditTextId.getText().toString());
                        chat_recyclerViewAdapter.notifyItemChanged(selectedPosition);
                        chat_recyclerViewAdapter.notifyItemRangeChanged(selectedPosition, chatData_serviceModel.getData().size());
                        operation_Status = "";
                        activityBarteringSuggestionChatBinding.typeMessageEditTextId.getText().clear();
                        AlphaHolder.manipulateRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");

                        if (handler != null)
                            handler.postDelayed(runnable, delay);

                    } else {
                        AlphaHolder.customToast(BarteringSuggestionChatActivity.this, comman_serviceModel.getMessage());
                    }
                } else {
                    activityBarteringSuggestionChatBinding.setIsRecordsNotFound(true);
                }
            }
            if (resultData.getTag().equals("DELETE_MESSAGE")) {
                Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
                if (comman_serviceModel != null) {
                    if (comman_serviceModel.isStatus().equals("true")) {
                        chatData_serviceModel.getData().remove(selectedPosition);
                        chat_recyclerViewAdapter.notifyItemRemoved(selectedPosition);
                        chat_recyclerViewAdapter.notifyItemRangeRemoved(selectedPosition, chatData_serviceModel.getData().size());
                        AlphaHolder.manipulateRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");
                        if (handler != null)
                            handler.postDelayed(runnable, delay);

                        operation_Status = "";


                    } else {
                        AlphaHolder.customToast(BarteringSuggestionChatActivity.this, comman_serviceModel.getMessage());
                    }
                } else {
                    activityBarteringSuggestionChatBinding.setIsRecordsNotFound(true);
                }
            }
        }
    }

    private boolean isExistInParentList(ChatData_ServiceModel.DataBean dataBean) {
        for (int p = 0; p < chatData_ArrayList.size(); p++) {
            if (chatData_ArrayList.get(p).getId().equals(dataBean.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (chatData_serviceModel.getData().size() == 0 && !TextUtils.isEmpty(s.toString())) {
            activityBarteringSuggestionChatBinding.setShowDialog(true);
        } else {
            activityBarteringSuggestionChatBinding.setShowDialog(false);
        }
        if (TextUtils.isEmpty(s.toString())) {
            activityBarteringSuggestionChatBinding.setShouldShowSendMessage(false);
        } else {
            activityBarteringSuggestionChatBinding.setShouldShowSendMessage(true);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onMoreClicked(View v, int position, ChatData_ServiceModel.DataBean dataBean) {
        selectedPosition = position;
        operation_Data = dataBean;

        PopupMenu popup = new PopupMenu(BarteringSuggestionChatActivity.this, v);
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.inflate(R.menu.chat_option_menu);
        popup.setGravity(Gravity.END);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        operation_Status = "EDIT";
                        activityBarteringSuggestionChatBinding.typeMessageEditTextId.setText(dataBean.getFinal_message());
                        activityBarteringSuggestionChatBinding.typeMessageEditTextId.requestFocus();
                        break;
                    case R.id.delete:
                        operation_Status = "DELETE";
                        if (handler != null)
                            handler.removeCallbacks(runnable, delay);

                        AlphaHolder.saveRecyclerviewState(activityBarteringSuggestionChatBinding.chatRecyclerViewId, "CHAT_DATA");
                        deleteMessage(dataBean.getId());
                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (chatData_serviceModel != null && chatData_serviceModel.getData().size() > 0)
            activityBarteringSuggestionChatBinding.chatRecyclerViewId.scrollToPosition(chatData_serviceModel.getData().size() - 1);
    }

    private void keyboardStateNotifier(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        final View parentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();
            private boolean alreadyOpen;

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
                parentView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    Log.i("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                alreadyOpen = isShown;
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }
}