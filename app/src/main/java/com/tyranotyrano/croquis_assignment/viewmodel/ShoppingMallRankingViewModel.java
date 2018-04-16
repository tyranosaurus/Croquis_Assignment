package com.tyranotyrano.croquis_assignment.viewmodel;

import android.content.Context;

import com.tyranotyrano.croquis_assignment.R;
import com.tyranotyrano.croquis_assignment.adapter.ShoppingMallRankingAdapter;
import com.tyranotyrano.croquis_assignment.model.ShoppingMall;
import com.tyranotyrano.croquis_assignment.model.ShoppingMallRankingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShoppingMallRankingViewModel {
    final String JSON_KEY_SHOPPING_MALL_UPDATE_DATE = "week";
    final String JSON_KEY_SHOPPING_MALL_LIST = "list";
    final String JSON_KEY_SHOPPING_MALL_SCORE = "0";
    final String JSON_KEY_SHOPPING_MALL_NAME = "n";
    final String JSON_KEY_SHOPPING_MALL_URL = "u";
    final String JSON_KEY_SHOPPING_MALL_STYLE = "S";
    final String JSON_KEY_SHOPPING_MALL_AGE = "A";
    final String STYLE_PARSING_DIVIDER = ",";

    private Context context;
    private ShoppingMallRankingModel model;

    ShoppingMallRankingAdapter adapter;

    public ShoppingMallRankingViewModel(Context context) {
        this.context = context;
        this.model = new ShoppingMallRankingModel(context);
        this.adapter = new ShoppingMallRankingAdapter();

        setUpShoppingMallRankingInfo();
    }

    public ShoppingMallRankingAdapter getAdapter() {
        return adapter;
    }

    public void setUpShoppingMallRankingInfo() {
        try {
            JSONObject jsonObject = new JSONObject(getShoppingMallRankingInfo());

            String updateDate = jsonObject.getString(JSON_KEY_SHOPPING_MALL_UPDATE_DATE) + context.getResources().getString(R.string.shopping_mall_header_ranking_update_date);
            adapter.setUpdateDate(updateDate);

            JSONArray jsonArrayShoppingMallList = jsonObject.getJSONArray(JSON_KEY_SHOPPING_MALL_LIST);

            for ( int i = 0; i < jsonArrayShoppingMallList.length(); i++ ) {
                ShoppingMall shoppingMall = parseShoppingMall(jsonArrayShoppingMallList.getJSONObject(i));
                adapter.addShoppingMall(shoppingMall);
            }

            adapter.showTotalShoppingMall();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getShoppingMallRankingInfo() {
        String shoppingMallRankingInfo = model.getJsonShoppingMallRankingInfo();

        return shoppingMallRankingInfo;
    }

    public ShoppingMall parseShoppingMall(JSONObject jsonObject) {
        ShoppingMall shoppingMall = null;

        try {
            int score = jsonObject.getInt(JSON_KEY_SHOPPING_MALL_SCORE);
            String name = jsonObject.getString(JSON_KEY_SHOPPING_MALL_NAME);
            String url = jsonObject.getString(JSON_KEY_SHOPPING_MALL_URL);
            String[] styleArr = jsonObject.getString(JSON_KEY_SHOPPING_MALL_STYLE).split(STYLE_PARSING_DIVIDER);

            JSONArray jsonArrayAge = jsonObject.getJSONArray(JSON_KEY_SHOPPING_MALL_AGE);
            int[] ageArr = new int[7];

            for ( int i = 0; i < jsonArrayAge.length(); i++ ) {
                ageArr[i] = jsonArrayAge.getInt(i);
            }

            shoppingMall = new ShoppingMall(score, name, url, styleArr, ageArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return shoppingMall;
    }

    public void setSelectedListAtAdapter(ArrayList<Integer> selectedAgeIndexList, ArrayList<String> selectedStyleIndexList) {
        adapter.filterShoppingMall(selectedAgeIndexList, selectedStyleIndexList);
    }
}
