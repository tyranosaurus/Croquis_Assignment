package com.tyranotyrano.croquis_assignment.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.LinearLayout;

import com.tyranotyrano.croquis_assignment.R;
import com.tyranotyrano.croquis_assignment.databinding.ActivityShoppingMallRankingBinding;
import com.tyranotyrano.croquis_assignment.viewmodel.FilterViewModel;
import com.tyranotyrano.croquis_assignment.viewmodel.ShoppingMallRankingViewModel;

import java.util.ArrayList;

public class ShoppingMallRankingActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SHOPPING_MALL_RANKING = 100;

    ActivityShoppingMallRankingBinding binding;
    ShoppingMallRankingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK && requestCode == REQUEST_CODE_SHOPPING_MALL_RANKING) {
            Bundle bundle = data.getExtras();

            ArrayList<Integer> selectedAgeIndexList = bundle.getIntegerArrayList(FilterViewModel.BUNDLE_KEY_SELECTED_AGE_INDEX_LIST);
            ArrayList<String> selectedStyleIndexList = bundle.getStringArrayList(FilterViewModel.BUNDLE_KEY_SELECTED_STYLE_INDEX_LIST);

            viewModel.setSelectedListAtAdapter(selectedAgeIndexList, selectedStyleIndexList);
        }
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(ShoppingMallRankingActivity.this, R.layout.activity_shopping_mall_ranking);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ShoppingMallRankingActivity.this, LinearLayout.VERTICAL);
        binding.recyclerviewShoppingMallRanking.addItemDecoration(dividerItemDecoration);

        viewModel = new ShoppingMallRankingViewModel(this);
        binding.setViewModel(viewModel);
    }
}
