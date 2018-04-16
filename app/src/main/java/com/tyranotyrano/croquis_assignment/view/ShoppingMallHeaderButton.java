package com.tyranotyrano.croquis_assignment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyranotyrano.croquis_assignment.R;

public class ShoppingMallHeaderButton extends LinearLayout {
    Context context;
    TextView textviewButtonName;
    ImageView imageviewButtonIcon;

    public ShoppingMallHeaderButton(Context context) {
        super(context);

        this.context = context;
        initView();
    }

    public ShoppingMallHeaderButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView();
        getAttrs(attrs);
    }

    public ShoppingMallHeaderButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.button_shopping_mall_ranking_header, this, false);

        addView(v);

        imageviewButtonIcon = (ImageView) findViewById(R.id.imageview_button_icon);
        textviewButtonName = (TextView) findViewById(R.id.textview_button_name);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShoppingMallHeaderButton);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShoppingMallHeaderButton, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        int iconResID = typedArray.getResourceId(R.styleable.ShoppingMallHeaderButton_buttonIcon, R.drawable.ic_filter_list);
        imageviewButtonIcon.setImageResource(iconResID);

        int textColor = typedArray.getColor(R.styleable.ShoppingMallHeaderButton_textColor, 0);
        textviewButtonName.setTextColor(textColor);

        String textString = typedArray.getString(R.styleable.ShoppingMallHeaderButton_text);
        textviewButtonName.setText(textString);

        typedArray.recycle();
    }

    void setButtonIcon(int iconResID) {
        imageviewButtonIcon.setImageResource(iconResID);
    }

    void setTextColor(String color) {
        textviewButtonName.setTextColor(Color.parseColor(color));
    }

    void setTextColor(int colorResID) {
        textviewButtonName.setTextColor(ContextCompat.getColor(context, colorResID));
    }

    void setText(String buttonName) {
        textviewButtonName.setText(buttonName);
    }

    void setText(int text_resID) {
        textviewButtonName.setText(getResources().getString(text_resID));
    }
}
