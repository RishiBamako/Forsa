package com.bartering.forsa.retrofit;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.bartering.forsa.R;
import com.bartering.forsa.retrofit.service_model.ResultsData_ServiceModel;
import com.google.gson.Gson;

import javax.inject.Inject;




public class BookFragment extends BaseFragment implements Observer<Object> {

    ViewDataBinding binding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewClass;

    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_remove, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observePosts();
        observeLoadStatus();
        observerErrorStatus();
        //setRecyclerView();
    }

    private void observePosts() {
        /*viewClass = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewClass.loadData("BOOKS");
        viewClass.getBookList().observe(this, this::onChanged);*/
    }

    @Override
    protected void observerErrorStatus() {
        viewClass.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error.getMessage());
                        showProgressBar(false);
                    }
                });
    }

    @Override
    protected void observeLoadStatus() {
        viewClass.getLoadingStatus().observe(this, isLoading -> showProgressBar(isLoading));
    }

    private void showProgressBar(boolean isVisible) {
        /*if (isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);*/
    }


    @Override
    public void onChanged(Object o) {
        ResultData resultsData = (ResultData) o;
        if (resultsData.getTag().equals("BOOKS")) {
            ResultsData_ServiceModel resultsDataServiceModel = (ResultsData_ServiceModel) resultsData.getRootData().getValue();
            Log.e("RESULT", new Gson().toJson(resultsDataServiceModel));

        }
    }
}
