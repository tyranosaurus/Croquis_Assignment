package com.tyranotyrano.croquis_assignment.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.tyranotyrano.croquis_assignment.R;
import com.tyranotyrano.croquis_assignment.util.CroquisApplication;

public class ShoppingMall {
    final String URL_PARSING_DIVIDER = ".";
    final String SHOPPING_MALL_IMAGE_PREFIX = "https://cf.shop.s.zigzag.kr/images/";
    final String SHOPPING_MALL_IMAGE_SUFFIX = ".jpg";

    public ObservableInt score;
    public ObservableField<String> name;
    public ObservableField<String> url;
    public ObservableField<String> imageUrl;
    public ObservableField<String> styleFirst;
    public ObservableField<String> styleSecond;
    public ObservableField<Boolean> isStyleFirstMatched;
    public ObservableField<Boolean> isStyleSecondMatched;
    public ObservableField<String> age;
    public ObservableArrayList<Integer> ageList;

    public ShoppingMall(int score, String name, String url, String[] styleArr, int[] ageArr) {
        init();

        this.score.set(score);
        this.name.set(name);
        this.url.set(url);
        this.imageUrl.set(SHOPPING_MALL_IMAGE_PREFIX + getShoppingMallName(url) + SHOPPING_MALL_IMAGE_SUFFIX);

        if ( styleArr.length == 1 ) {
            this.styleFirst.set(styleArr[0]);
            this.styleSecond.set(null);
        } else {
            this.styleFirst.set(styleArr[0]);
            this.styleSecond.set(styleArr[1]);
        }

        this.isStyleFirstMatched.set(true);
        this.isStyleSecondMatched.set(true);

        this.age.set(getAge(ageArr));

        for ( int i = 0; i < ageArr.length; i++ ) {
            ageList.add(ageArr[i]);
        }
    }

    private void init() {
        this.score = new ObservableInt();
        this.name = new ObservableField<>();
        this.url = new ObservableField<>();
        this.imageUrl = new ObservableField<>();
        this.styleFirst = new ObservableField<>();
        this.styleSecond = new ObservableField<>();
        this.isStyleFirstMatched = new ObservableField<>();
        this.isStyleSecondMatched = new ObservableField<>();
        this.age = new ObservableField<>();
        this.ageList = new ObservableArrayList<>();
    }

    private String getShoppingMallName(String url) {
        String basicName = url.replaceAll("^(http(s)?):\\/\\/(www+\\.)?", "");
        int dotIndex = basicName.indexOf(URL_PARSING_DIVIDER);
        String shoppingMallName = basicName.substring(0, dotIndex);

        return shoppingMallName;
    }

    private String getAge(int[] ageArr) {
        StringBuilder sb = new StringBuilder();

        if ( ageArr[0] == 1 ) {
            sb.append(CroquisApplication.getAppContext().getString(R.string.shopping_mall_age_10));
        }

        if ( ageArr[1] == 1 || ageArr[2] == 1 || ageArr[3] == 1) {
            sb.append(CroquisApplication.getAppContext().getString(R.string.shopping_mall_age_20));
        }

        if ( ageArr[4] == 1 || ageArr[5] == 1 || ageArr[6] == 1) {
            sb.append(CroquisApplication.getAppContext().getString(R.string.shopping_mall_age_30));
        }

        return sb.toString().trim();
    }
}
