package com.tyranotyrano.croquis_assignment.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tyranotyrano.croquis_assignment.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBindings {

    @BindingAdapter("adapter")
    public static void setRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if ( adapter != null ) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("imageUrl")
    public static void loadImageUrl(CircleImageView circleImageView, String imageUrl) {
        Glide.with(circleImageView.getContext())
                .load(imageUrl)
                .error(R.drawable.ic_profile_default)
                .into(circleImageView);
    }
}
