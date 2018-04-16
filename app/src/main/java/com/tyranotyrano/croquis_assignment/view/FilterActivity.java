package com.tyranotyrano.croquis_assignment.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tyranotyrano.croquis_assignment.R;
import com.tyranotyrano.croquis_assignment.databinding.ActivityFilterBinding;
import com.tyranotyrano.croquis_assignment.viewmodel.FilterViewModel;

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding binding;
    FilterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initDataBinding();
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(FilterActivity.this, R.layout.activity_filter);
        viewModel = new FilterViewModel(this);

        binding.setViewModel(viewModel);
        binding.setAgeList(viewModel.getAgeList());
        binding.setStyleList(viewModel.getStyleList());
    }
}
