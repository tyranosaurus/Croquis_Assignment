package com.tyranotyrano.croquis_assignment.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import com.tyranotyrano.croquis_assignment.R;
import com.tyranotyrano.croquis_assignment.util.CroquisApplication;
import com.tyranotyrano.croquis_assignment.view.FilterActivity;
import com.tyranotyrano.croquis_assignment.view.ShoppingMallRankingActivity;

public class HeaderViewModel {
    public ObservableField<String> updateDate;

    public HeaderViewModel(String updateDate) {
        this.updateDate = new ObservableField<>(updateDate);
    }

    public void onFilterClick(View view) {
        Intent intent = new Intent(view.getContext(), FilterActivity.class);
        ((ShoppingMallRankingActivity)view.getContext()).startActivityForResult(intent, ShoppingMallRankingActivity.REQUEST_CODE_SHOPPING_MALL_RANKING);
    }

    public void onSearchClick(View view) {
        String message = CroquisApplication.getAppContext().getString(R.string.shopping_mall_header_button_search);

        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
