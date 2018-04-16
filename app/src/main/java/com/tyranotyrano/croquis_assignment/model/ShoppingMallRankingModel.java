package com.tyranotyrano.croquis_assignment.model;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class ShoppingMallRankingModel {
    private final String DATA_FILE_SHOP_LIST = "shop_list.json";
    private final String CHARSET_NAME = "UTF-8";

    private Context context;
    private String jsonShoppingMallRankingInfo;

    public ShoppingMallRankingModel(Context context) {
        this.context = context;
        this.jsonShoppingMallRankingInfo = readJSONFromAsset();
    }

    private String readJSONFromAsset() {
        String json;

        try {
            InputStream is = context.getAssets().open(DATA_FILE_SHOP_LIST);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, CHARSET_NAME);
        } catch (IOException ex) {
            ex.printStackTrace();

            return null;
        }

        return json;
    }

    public String getJsonShoppingMallRankingInfo() {
        return jsonShoppingMallRankingInfo;
    }
}
