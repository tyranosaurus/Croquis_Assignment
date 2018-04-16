package com.tyranotyrano.croquis_assignment.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyranotyrano.croquis_assignment.databinding.HeaderShoppingMallRankingBinding;
import com.tyranotyrano.croquis_assignment.databinding.ItemShoppingMallRankingBinding;
import com.tyranotyrano.croquis_assignment.model.ShoppingMall;
import com.tyranotyrano.croquis_assignment.util.ShoppingMallStyle;
import com.tyranotyrano.croquis_assignment.view.ShoppingMallComparator;
import com.tyranotyrano.croquis_assignment.viewmodel.HeaderViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShoppingMallRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_HEADER = 0;
    final int VIEW_TYPE_ITEM = 1;

    private String updateDate;
    private List<ShoppingMall> shoppingMallList;
    private List<ShoppingMall> totalShoppingMallList;
    private List<ShoppingMall> filteredShoppingMallList;

     public ShoppingMallRankingAdapter() {
         this.totalShoppingMallList = new LinkedList<>();
         this.filteredShoppingMallList = new LinkedList<>();
     }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( viewType == VIEW_TYPE_HEADER ) {
            HeaderShoppingMallRankingBinding binding
                    = HeaderShoppingMallRankingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new HeaderViewHolder(binding);
        } else if ( viewType == VIEW_TYPE_ITEM ) {
            ItemShoppingMallRankingBinding binding
                    = ItemShoppingMallRankingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new ItemViewHolder(binding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( holder instanceof HeaderViewHolder ) {
            ((HeaderViewHolder)holder).bindUpdateDate(updateDate);
        } else if ( holder instanceof ItemViewHolder ) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ShoppingMall shoppingMall = shoppingMallList.get(position - 1);

            itemViewHolder.bindShoppingMall(shoppingMall);
            itemViewHolder.binding.textviewRanking.setText(String.valueOf(position));

            if ( shoppingMall.isStyleFirstMatched.get() ) {
                itemViewHolder.setStyleColor(itemViewHolder.binding.textviewStyleFirst, shoppingMall.styleFirst.get());
            } else {
                itemViewHolder.setStyleColor(itemViewHolder.binding.textviewStyleFirst, ShoppingMallStyle.DEFAULT);
            }

            if ( shoppingMall.isStyleSecondMatched.get() ) {
                itemViewHolder.setStyleColor(itemViewHolder.binding.textviewStyleSecond, shoppingMall.styleSecond.get());
            } else {
                itemViewHolder.setStyleColor(itemViewHolder.binding.textviewStyleSecond, ShoppingMallStyle.DEFAULT);
            }
        }
    }

    @Override
    public int getItemCount() {
        return shoppingMallList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ( position == 0 ) {
            return VIEW_TYPE_HEADER;
        }

        return VIEW_TYPE_ITEM;
    }

    public void showTotalShoppingMall() {
        for ( int i = 0; i < totalShoppingMallList.size(); i++ ) {
            ShoppingMall shoppingMall = totalShoppingMallList.get(i);

            shoppingMall.isStyleFirstMatched.set(true);
            shoppingMall.isStyleSecondMatched.set(true);
        }

        shoppingMallList = totalShoppingMallList;
        notifyDataSetChanged();
    }

    public void showFilteredShoppingMall() {
        shoppingMallList = filteredShoppingMallList;
        notifyDataSetChanged();
    }

    public void addShoppingMall(ShoppingMall shoppingMall) {
        totalShoppingMallList.add(shoppingMall);
        Collections.sort(totalShoppingMallList, new ShoppingMallComparator());
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void filterShoppingMall(ArrayList<Integer> selectedAgeIndexList, ArrayList<String> selectedStyleIndexList) {
        List<ShoppingMall> twoStyleMatchedShoppingMallList = new ArrayList<>();
        List<ShoppingMall> oneStyleMatchedShoppingMallList = new ArrayList<>();

        filteredShoppingMallList.clear();

        /**
         * In case user doesn't select any ages and styles.
         */
        if ( selectedAgeIndexList.size() < 1 && selectedStyleIndexList.size() < 1) {
            showTotalShoppingMall();

            return;
        }

        for (int i = 0; i < totalShoppingMallList.size(); i++ ) {
            ShoppingMall shoppingMall = totalShoppingMallList.get(i);
            List<Integer> ageList = shoppingMall.ageList;
            String styleFirst = shoppingMall.styleFirst.get();
            String styleSecond = shoppingMall.styleSecond.get();

            if ( styleSecond == null ) {
                styleSecond = "";
            }

            /**
             * Check whether shopping mall has selected ages(filter).
             */
            if ( selectedAgeIndexList.size() > 0 && !containSelectedAge(ageList ,selectedAgeIndexList) ) {
                continue;
            }

            /**
             * In case user select only ages.
             */
            if ( selectedAgeIndexList.size() > 0 && selectedStyleIndexList.size() < 1 ) {
                filteredShoppingMallList.add(shoppingMall);

                continue;
            }

            /**
             * Check whether shopping mall has selected two style.
             */
            if ( containStyle(styleFirst, selectedStyleIndexList) && containStyle(styleSecond, selectedStyleIndexList) ) {
                shoppingMall.isStyleFirstMatched.set(true);
                shoppingMall.isStyleSecondMatched.set(true);

                twoStyleMatchedShoppingMallList.add(shoppingMall);

                continue;
            }

            /**
             * Check whether shopping mall has selected one style.
             */
            if ( containStyle(styleFirst, selectedStyleIndexList) || containStyle(styleSecond, selectedStyleIndexList) ) {
                if ( containStyle(styleFirst, selectedStyleIndexList) ) {
                    shoppingMall.isStyleFirstMatched.set(true);
                } else {
                    shoppingMall.isStyleFirstMatched.set(false);
                }

                if ( containStyle(styleSecond, selectedStyleIndexList) ) {
                    shoppingMall.isStyleSecondMatched.set(true);
                } else {
                    shoppingMall.isStyleSecondMatched.set(false);
                }

                oneStyleMatchedShoppingMallList.add(shoppingMall);
            }
        }

        filteredShoppingMallList.addAll(twoStyleMatchedShoppingMallList);
        filteredShoppingMallList.addAll(oneStyleMatchedShoppingMallList);

        showFilteredShoppingMall();
    }

    private boolean containSelectedAge(List<Integer> shoppingMallAgeList, List<Integer> selectedAgeIndexList) {
        for ( int i = 0; i < selectedAgeIndexList.size(); i++ ) {
            if ( shoppingMallAgeList.get(selectedAgeIndexList.get(i)) == 1 ) {
                return true;
            }
        }

        return false;
    }

    private boolean containStyle(String style, List<String> selectedStyleIndexList) {
        for ( int i = 0; i < selectedStyleIndexList.size(); i++ ) {
            if ( style.equals(selectedStyleIndexList.get(i)) ) {
                return true;
            }
        }

        return false;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderShoppingMallRankingBinding binding;

        public HeaderViewHolder(HeaderShoppingMallRankingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindUpdateDate(String updateDate) {
            binding.setViewModel(new HeaderViewModel(updateDate));
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemShoppingMallRankingBinding binding;

        public ItemViewHolder(ItemShoppingMallRankingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindShoppingMall(ShoppingMall shoppingMall) {
            binding.setShoppingMall(shoppingMall);
        }

        public void setStyleColor(TextView textview, String style) {
            if ( style == null || style.length() < 1 ) {
                return;
            }

            /**
             * [switch - case] can not resolve static final variable which is the resource in R.
             * so, [if - else if] is used instead of [switch - case].
             */
            if (style.equals(ShoppingMallStyle.FEMININE)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_FEMININE, ShoppingMallStyle.COLOR_BACKGROUND_FEMININE);

            } else if (style.equals(ShoppingMallStyle.MODERN_CHIC)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_MODERN_CHIC, ShoppingMallStyle.COLOR_BACKGROUND_MODERN_CHIC);

            } else if (style.equals(ShoppingMallStyle.SIMPLE_BASIC)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_SIMPLE_BASIC, ShoppingMallStyle.COLOR_BACKGROUND_SIMPLE_BASIC);

            } else if (style.equals(ShoppingMallStyle.LOVELY)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_LOVELY, ShoppingMallStyle.COLOR_BACKGROUND_LOVELY);

            } else if (style.equals(ShoppingMallStyle.UNIQUE)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_UNIQUE, ShoppingMallStyle.COLOR_BACKGROUND_UNIQUE);

            } else if (style.equals(ShoppingMallStyle.MISSY_STYLE)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_MISSY_STYLE, ShoppingMallStyle.COLOR_BACKGROUND_MISSY_STYLE);

            } else if (style.equals(ShoppingMallStyle.CAMPUS_LOOK)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_CAMPUS_LOOK, ShoppingMallStyle.COLOR_BACKGROUND_CAMPUS_LOOK);

            } else if (style.equals(ShoppingMallStyle.VINTAGE)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_VINTAGE, ShoppingMallStyle.COLOR_BACKGROUND_VINTAGE);

            } else if (style.equals(ShoppingMallStyle.SEXY_GLAM)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_SEXY_GLAM, ShoppingMallStyle.COLOR_BACKGROUND_SEXY_GLAM);

            } else if (style.equals(ShoppingMallStyle.SCHOOL_LOOK)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_SCHOOL_LOOK, ShoppingMallStyle.COLOR_BACKGROUND_SCHOOL_LOOK);

            } else if (style.equals(ShoppingMallStyle.ROMANTIC)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_ROMANTIC, ShoppingMallStyle.COLOR_BACKGROUND_ROMANTIC);

            } else if (style.equals(ShoppingMallStyle.OFFICE_LOOK)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_OFFICE_LOOK, ShoppingMallStyle.COLOR_BACKGROUND_OFFICE_LOOK);

            } else if (style.equals(ShoppingMallStyle.LUXURY)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_LUXURY, ShoppingMallStyle.COLOR_BACKGROUND_LUXURY);

            } else if (style.equals(ShoppingMallStyle.HOLLYWOOD_STYLE)) {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_HOLLYWOOD_STYLE, ShoppingMallStyle.COLOR_BACKGROUND_HOLLYWOOD_STYLE);

            } else {
                setStrokeBackgroundColor(textview, ShoppingMallStyle.COLOR_STROKE_DEFAULT, ShoppingMallStyle.COLOR_BACKGROUND_DEFAULT);

            }
        }

        private void setStrokeBackgroundColor(TextView textview, int colorStroke, int colorBackground) {
            GradientDrawable drawable = (GradientDrawable) textview.getBackground();
            drawable.setStroke(2, colorStroke);
            drawable.setColor(colorBackground);
        }
    }
}
