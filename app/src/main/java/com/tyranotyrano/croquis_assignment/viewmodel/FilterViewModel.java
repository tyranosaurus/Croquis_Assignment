package com.tyranotyrano.croquis_assignment.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FilterViewModel {
    public  static final String BUNDLE_KEY_SELECTED_AGE_INDEX_LIST = "selectedAgeIndexList";
    public  static final String BUNDLE_KEY_SELECTED_STYLE_INDEX_LIST = "selectedStyleIndexList";

    private final int TOTAL_AGE_COUNT = 7;
    private final int TOTAL_STYLE_COUNT = 14;


    private Context context;

    ObservableArrayList<Integer> ageList;
    ObservableArrayList<Integer> styleList;

    ObservableArrayList<Integer> selectedAgeIndexList;
    ObservableArrayList<String> selectedStyleIndexList;

    public FilterViewModel(Context context) {
        init(context);
        setInitValueOfList();
    }

    public ObservableArrayList<Integer> getAgeList() {
        return ageList;
    }

    public ObservableArrayList<Integer> getStyleList() {
        return styleList;
    }

    private void init(Context context) {
        this.context = context;
        this.ageList = new ObservableArrayList<>();
        this.styleList = new ObservableArrayList<>();
        this.selectedAgeIndexList = new ObservableArrayList<>();
        this.selectedStyleIndexList = new ObservableArrayList<>();
    }

    private void setInitValueOfList() {
        for ( int i = 0; i < TOTAL_AGE_COUNT; i++ ) {
            ageList.add(0);
        }

        for ( int i = 0; i < TOTAL_STYLE_COUNT; i++ ) {
            styleList.add(0);
        }
    }

    public void onCloseClick(View view) {
        ((Activity)context).setResult(Activity.RESULT_CANCELED);
        ((Activity)context).finish();
    }

    public void onResetClick(View view) {
        for ( int i = 0; i < ageList.size(); i++ ) {
            ageList.set(i, 0);
        }

        for ( int i = 0; i < styleList.size(); i++ ) {
            styleList.set(i, 0);
        }

        selectedAgeIndexList.clear();
        selectedStyleIndexList.clear();
    }

    public void onAgeClick(View view) {
        int position = Integer.parseInt(view.getTag().toString());

        if ( ageList.get(position) == 0 ) {
            ageList.set(position, 1);
            selectedAgeIndexList.add(position);
        } else if ( ageList.get(position) == 1 ){
            ageList.set(position, 0);
            selectedAgeIndexList.remove((Integer)position);
        }
    }

    public void onStyleClick(View view) {
        int position = Integer.parseInt(view.getTag().toString());

        if ( styleList.get(position) == 0 ) {
            styleList.set(position, 1);
            selectedStyleIndexList.add(((TextView)view).getText().toString());
        } else if ( styleList.get(position) == 1 ){
            styleList.set(position, 0);
            selectedStyleIndexList.remove(((TextView)view).getText().toString());
        }
    }

    public void onSelectComplete(View view) {
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(BUNDLE_KEY_SELECTED_AGE_INDEX_LIST, selectedAgeIndexList);
        bundle.putStringArrayList(BUNDLE_KEY_SELECTED_STYLE_INDEX_LIST, selectedStyleIndexList);

        intent.putExtras(bundle);

        ((Activity)context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).finish();
    }
}
