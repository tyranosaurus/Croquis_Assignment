package com.tyranotyrano.croquis_assignment.view;

import com.tyranotyrano.croquis_assignment.model.ShoppingMall;

import java.util.Comparator;

public class ShoppingMallComparator implements Comparator<ShoppingMall> {
    @Override
    public int compare(ShoppingMall o1, ShoppingMall o2) {
        if ( o1.score.get() > o2.score.get() ) {
            return -1;
        } else if ( o1.score.get() < o2.score.get() ) {
            return 1;
        } else {
            return 0;
        }
    }
}
